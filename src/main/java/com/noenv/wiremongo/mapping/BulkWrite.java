package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.BulkOperation;
import io.vertx.ext.mongo.MongoClientBulkWriteResult;

import java.util.List;
import java.util.stream.Collectors;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

public class BulkWrite extends WithCollection<MongoClientBulkWriteResult, BulkWrite> {

  public static class BulkWriteCommand extends WithCollectionCommand {
    private final List<BulkOperation> operations;

    public BulkWriteCommand(String collection, List<BulkOperation> operations) {
      this("bulkWrite", collection, operations);
    }

    public BulkWriteCommand(String method, String collection, List<BulkOperation> operations) {
      super(method, collection);
      this.operations = operations;
    }

    @Override
    public String toString() {
      return super.toString() + ", operations: " + (operations == null ? "null" : operations.stream()
        .map(o -> o.toJson().encode()).collect(Collectors.joining(",")));
    }
  }

  private Matcher<List<BulkOperation>> operations;

  public BulkWrite() {
    this("bulkWrite");
  }

  public BulkWrite(String method) {
    super(method);
  }

  public BulkWrite(JsonObject json) {
    super(json);
    operations = Matcher.create(json.getJsonObject("operations"),
      j -> ((JsonArray) j).stream().map(v -> new BulkOperation((JsonObject) v)).collect(Collectors.toList()),
      l -> new JsonArray(l.stream().map(BulkOperation::toJson).collect(Collectors.toList())));
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
    if (!(cmd instanceof BulkWriteCommand)) {
      return false;
    }
    BulkWriteCommand c = (BulkWriteCommand) cmd;
    return operations == null || operations.matches(c.operations);
  }

  public BulkWrite withOperations(List<BulkOperation> operations) {
    return withOperations(equalTo(operations));
  }

  public BulkWrite withOperations(Matcher<List<BulkOperation>> operations) {
    this.operations = operations;
    return this;
  }
}
