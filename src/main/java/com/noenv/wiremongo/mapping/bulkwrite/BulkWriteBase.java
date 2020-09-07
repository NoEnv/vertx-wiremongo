package com.noenv.wiremongo.mapping.bulkwrite;

import com.mongodb.MongoBulkWriteException;
import com.mongodb.ServerAddress;
import com.mongodb.bulk.BulkWriteError;
import com.mongodb.bulk.BulkWriteResult;
import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.bulkwrite.BulkWriteBaseCommand;
import com.noenv.wiremongo.mapping.collection.WithCollection;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.BulkOperation;
import io.vertx.ext.mongo.MongoClientBulkWriteResult;
import org.bson.BsonDocument;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

public abstract class BulkWriteBase<U extends BulkWriteBaseCommand, C extends BulkWriteBase<U, C>> extends WithCollection<MongoClientBulkWriteResult, U, C> {

  private Matcher<List<BulkOperation>> operations;

  public BulkWriteBase() {
    this("bulkWrite");
  }

  public BulkWriteBase(String method) {
    super(method);
  }

  public BulkWriteBase(JsonObject json) {
    super(json);
    operations = Matcher.create(json.getJsonObject("operations"),
      j -> ((JsonArray) j).stream().map(v -> new BulkOperation((JsonObject) v)).collect(Collectors.toList()),
      l -> new JsonArray(l.stream().map(BulkOperation::toJson).collect(Collectors.toList())));
  }

  @Override
  public C returns(final MongoClientBulkWriteResult response) {
    return super.stub(c -> null == response ? null : new MongoClientBulkWriteResult(response.toJson().copy()));
  }

  @Override
  protected MongoClientBulkWriteResult parseResponse(Object jsonValue) {
    return new MongoClientBulkWriteResult((JsonObject) jsonValue);
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof BulkWriteBaseCommand)) {
      return false;
    }
    BulkWriteBaseCommand c = (BulkWriteBaseCommand) cmd;
    return operations == null || operations.matches(c.getOperations());
  }

  @Override
  public C returnsDuplicateKeyError() {
    return returnsError(new MongoBulkWriteException(
      BulkWriteResult.acknowledged(0, 0, 0, 0, Collections.emptyList()),
      Collections.singletonList(new BulkWriteError(11000, "error-detail", new BsonDocument(), 0)),
      null,
      new ServerAddress()));
  }

  public C returnsOtherBulkWriteError() {
    return returnsError(new MongoBulkWriteException(
      BulkWriteResult.acknowledged(0, 0, 0, 0, Collections.emptyList()),
      Collections.singletonList(new BulkWriteError(22000, "error-detail", new BsonDocument(), 0)),
      null,
      new ServerAddress()));
  }

  public C withOperations(List<BulkOperation> operations) {
    return withOperations(equalTo(operations));
  }

  public C withOperations(Matcher<List<BulkOperation>> operations) {
    this.operations = operations;
    return self();
  }
}
