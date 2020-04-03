package com.noenv.wiremongo.mapping;

import com.mongodb.*;
import com.noenv.wiremongo.Stub;
import io.vertx.core.json.JsonObject;
import org.bson.BsonDocument;

import java.net.ConnectException;

public interface Mapping<T> extends Command {

  boolean matches(Command c);

  int priority();

  Mapping<T> priority(int priority);

  Stub<T> stub();

  Mapping<T> stub(Stub<T> stub);

  default Mapping<T> returns(T response) {
    return stub(() -> response);
  }

  default Mapping<T> returnsError(Throwable error) {
    return stub(() -> { throw error; });
  }

  default Mapping<T> returnsDuplicateKeyError() {
    return returnsError(new MongoWriteException(
      new WriteError(11000, "E11000 duplicate key error", new BsonDocument()), new ServerAddress()));
  }

  default Mapping<T> returnsTimeoutException() {
    return returnsError(new MongoTimeoutException("Timed out after 30000 ms while waiting for a server that matches " +
      "ReadPreferenceServerSelector{readPreference=primary}. Client view of cluster state is {type=UNKNOWN, servers=[" +
      "{address=127.0.0.1:27017, type=UNKNOWN, state=CONNECTING, exception={com.mongodb.MongoSocketOpenException: Exc" +
      "eption opening socket}, caused by {java.net.ConnectException: Connection refused}}]"));
  }

  default Mapping<T> returnsConnectionException() {
    return returnsError(new MongoSocketOpenException("Exception opening socket",
      new ServerAddress(), new ConnectException("Connection refused")));
  }

  static Mapping create(JsonObject json) {
    try {
      switch (json.getString("method")) {
        case "insert": return new Insert(json);
        case "insertWithOptions": return new InsertWithOptions(json);
        case "save": return new Save(json);
        case "saveWithOptions": return new SaveWithOptions(json);
        case "updateCollection": return new UpdateCollection(json);
        case "updateCollectionWithOptions": return new UpdateCollectionWithOptions(json);
        case "find": return new Find(json);
        case "findWithOptions": return new FindWithOptions(json);
        case "findBatch": return new FindBatch(json);
        case "findBatchWithOptions": return new FindBatchWithOptions(json);
        case "findOne": return new FindOne(json);
        case "findOneAndUpdate": return new FindOneAndUpdate(json);
        case "findOneAndReplace": return new FindOneAndReplace(json);
        case "findOneAndReplaceWithOptions": return new FindOneAndReplaceWithOptions(json);
        case "findOneAndDelete": return new FindOneAndDelete(json);
        case "findOneAndDeleteWithOptions": return new FindOneAndDeleteWithOptions(json);
        case "findOneAndUpdateWithOptions": return new FindOneAndUpdateWithOptions(json);
        case "createCollection": return new CreateCollection(json);
        case "dropCollection": return new DropCollection(json);
        case "listIndexes": return new ListIndexes(json);
        case "createIndex": return new CreateIndex(json);
        case "createIndexWithOptions": return new CreateIndexWithOptions(json);
        case "runCommand": return new RunCommand(json);
        case "count": return new Count(json);
        case "bulkWrite": return new BulkWrite(json);
        case "bulkWriteWithOptions": return new BulkWriteWithOptions(json);
        case "getCollections": return new GetCollections(json);
        case "removeDocument": return new RemoveDocument(json);
        case "removeDocumentWithOptions": return new RemoveDocumentWithOptions(json);
        case "removeDocuments": return new RemoveDocuments(json);
        case "removeDocumentsWithOptions": return new RemoveDocumentsWithOptions(json);
        case "replaceDocuments": return new ReplaceDocuments(json);
        case "replaceDocumentsWithOptions": return new ReplaceDocumentsWithOptions(json);
        case "dropIndex": return new DropIndex(json);
        case "distinct": return new Distinct(json);
        case "distinctBatch": return new DistinctBatch(json);
        case "distinctBatchWithQuery": return new DistinctBatchWithQuery(json);
        case "distinctWithQuery": return new DistinctWithQuery(json);
        case "aggregate": return new Aggregate(json);
        case "aggregateWithOptions": return new AggregateWithOptions(json);
        default:
          throw new IllegalArgumentException("couldn't parse mapping, " + json.encode());
      }
    } catch(Exception ex) {
      throw new IllegalArgumentException("error parsing mapping " + json.encode());
    }
  }
}
