package com.noenv.wiremongo.mapping;

import com.mongodb.*;
import com.noenv.wiremongo.Stub;
import com.noenv.wiremongo.StubBase;
import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.mapping.aggregate.Aggregate;
import com.noenv.wiremongo.mapping.aggregate.AggregateWithOptions;
import com.noenv.wiremongo.mapping.bulkwrite.BulkWrite;
import com.noenv.wiremongo.mapping.bulkwrite.BulkWriteWithOptions;
import com.noenv.wiremongo.mapping.collection.CreateCollection;
import com.noenv.wiremongo.mapping.collection.DropCollection;
import com.noenv.wiremongo.mapping.collection.GetCollections;
import com.noenv.wiremongo.mapping.distinct.Distinct;
import com.noenv.wiremongo.mapping.distinct.DistinctBatch;
import com.noenv.wiremongo.mapping.distinct.DistinctBatchWithQuery;
import com.noenv.wiremongo.mapping.distinct.DistinctWithQuery;
import com.noenv.wiremongo.mapping.find.*;
import com.noenv.wiremongo.mapping.index.CreateIndex;
import com.noenv.wiremongo.mapping.index.CreateIndexWithOptions;
import com.noenv.wiremongo.mapping.index.DropIndex;
import com.noenv.wiremongo.mapping.index.ListIndexes;
import com.noenv.wiremongo.mapping.insert.Insert;
import com.noenv.wiremongo.mapping.insert.InsertWithOptions;
import com.noenv.wiremongo.mapping.remove.RemoveDocument;
import com.noenv.wiremongo.mapping.remove.RemoveDocumentWithOptions;
import com.noenv.wiremongo.mapping.remove.RemoveDocuments;
import com.noenv.wiremongo.mapping.remove.RemoveDocumentsWithOptions;
import com.noenv.wiremongo.mapping.replace.ReplaceDocuments;
import com.noenv.wiremongo.mapping.replace.ReplaceDocumentsWithOptions;
import com.noenv.wiremongo.mapping.save.Save;
import com.noenv.wiremongo.mapping.save.SaveWithOptions;
import com.noenv.wiremongo.mapping.update.UpdateCollection;
import com.noenv.wiremongo.mapping.update.UpdateCollectionWithOptions;
import com.noenv.wiremongo.verification.Verification;
import io.vertx.core.json.JsonObject;
import org.bson.BsonDocument;

import java.net.ConnectException;
import java.util.function.Function;

public interface Mapping<T, U extends Command, C extends Mapping<T, U, C>> {

  boolean matches(Command c);

  int priority();

  C priority(int priority);

  C validFor(int times);

  C stub(StubBase<T, U> stub);

  T invoke(U command) throws Throwable;

  C verify(Verification v);

  default C stub(Stub<T> stub) {
    return stub(c -> stub.invoke());
  }

  default C returns(T response) {
    return stub(x -> response);
  }

  default C returnsError(Throwable error) {
    return stub(x -> {
      throw error;
    });
  }

  default C returnsError(Function<U, Throwable> stub) {
    return stub(c -> {
      throw stub.apply(c);
    });
  }

  default C returnsDuplicateKeyError() {
    return returnsError(new MongoWriteException(
      new WriteError(11000, "E11000 duplicate key error", new BsonDocument()), new ServerAddress()));
  }

  default C returnsTimeoutException() {
    return returnsError(new MongoTimeoutException("Timed out after 30000 ms while waiting for a server that matches " +
      "ReadPreferenceServerSelector{readPreference=primary}. Client view of cluster state is {type=UNKNOWN, servers=[" +
      "{address=127.0.0.1:27017, type=UNKNOWN, state=CONNECTING, exception={com.mongodb.MongoSocketOpenException: Exc" +
      "eption opening socket}, caused by {java.net.ConnectException: Connection refused}}]"));
  }

  default C returnsConnectionException() {
    return returnsError(new MongoSocketOpenException("Exception opening socket",
      new ServerAddress(), new ConnectException("Connection refused")));
  }

  static Mapping create(JsonObject json) {
    try {
      switch (json.getString("method")) {
        case "matchAll":
          throw new UnsupportedOperationException("matchAll is not supported in file mappings");
        case "insert":
          return new Insert(json);
        case "insertWithOptions":
          return new InsertWithOptions(json);
        case "save":
          return new Save(json);
        case "saveWithOptions":
          return new SaveWithOptions(json);
        case "updateCollection":
          return new UpdateCollection(json);
        case "updateCollectionWithOptions":
          return new UpdateCollectionWithOptions(json);
        case "find":
          return new Find(json);
        case "findWithOptions":
          return new FindWithOptions(json);
        case "findBatch":
          return new FindBatch(json);
        case "findBatchWithOptions":
          return new FindBatchWithOptions(json);
        case "findOne":
          return new FindOne(json);
        case "findOneAndUpdate":
          return new FindOneAndUpdate(json);
        case "findOneAndReplace":
          return new FindOneAndReplace(json);
        case "findOneAndReplaceWithOptions":
          return new FindOneAndReplaceWithOptions(json);
        case "findOneAndDelete":
          return new FindOneAndDelete(json);
        case "findOneAndDeleteWithOptions":
          return new FindOneAndDeleteWithOptions(json);
        case "findOneAndUpdateWithOptions":
          return new FindOneAndUpdateWithOptions(json);
        case "createCollection":
          return new CreateCollection(json);
        case "dropCollection":
          return new DropCollection(json);
        case "listIndexes":
          return new ListIndexes(json);
        case "createIndex":
          return new CreateIndex(json);
        case "createIndexWithOptions":
          return new CreateIndexWithOptions(json);
        case "runCommand":
          return new RunCommand(json);
        case "count":
          return new Count(json);
        case "bulkWrite":
          return new BulkWrite(json);
        case "bulkWriteWithOptions":
          return new BulkWriteWithOptions(json);
        case "getCollections":
          return new GetCollections(json);
        case "removeDocument":
          return new RemoveDocument(json);
        case "removeDocumentWithOptions":
          return new RemoveDocumentWithOptions(json);
        case "removeDocuments":
          return new RemoveDocuments(json);
        case "removeDocumentsWithOptions":
          return new RemoveDocumentsWithOptions(json);
        case "replaceDocuments":
          return new ReplaceDocuments(json);
        case "replaceDocumentsWithOptions":
          return new ReplaceDocumentsWithOptions(json);
        case "dropIndex":
          return new DropIndex(json);
        case "distinct":
          return new Distinct(json);
        case "distinctBatch":
          return new DistinctBatch(json);
        case "distinctBatchWithQuery":
          return new DistinctBatchWithQuery(json);
        case "distinctWithQuery":
          return new DistinctWithQuery(json);
        case "aggregate":
          return new Aggregate(json);
        case "aggregateWithOptions":
          return new AggregateWithOptions(json);
        default:
          throw new IllegalArgumentException("couldn't parse mapping, " + json.encode());
      }
    } catch (Exception ex) {
      throw new IllegalArgumentException("error parsing mapping " + json.encode());
    }
  }
}
