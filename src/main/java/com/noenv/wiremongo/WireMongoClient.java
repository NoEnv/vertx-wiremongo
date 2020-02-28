package com.noenv.wiremongo;

import com.noenv.wiremongo.mapping.*;
import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.streams.ReadStream;
import io.vertx.ext.mongo.*;

import java.util.List;

public class WireMongoClient implements MongoClient {

  public static final String DEPRECATED_MESSAGE = "deprecated";
  private static WireMongoClient instance;
  private WireMongo wireMongo;

  private WireMongoClient() {
  }

  public WireMongo getWireMongo() {
    return wireMongo;
  }

  WireMongoClient setWireMongo(WireMongo wireMongo) {
    this.wireMongo = wireMongo;
    return this;
  }

  private <T> Future<T> call(Command request) {
    return wireMongo.call(request);
  }

  private ReadStream<JsonObject> callStream(Command request) {
    return wireMongo.callStream(request);
  }

  @Override
  public MongoClient save(String collection, JsonObject document, Handler<AsyncResult<String>> resultHandler) {
    this.<String>call(new Save.SaveCommand(collection, document)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient saveWithOptions(String collection, JsonObject document, @Nullable WriteOption writeOption, Handler<AsyncResult<String>> resultHandler) {
    this.<String>call(new SaveWithOptions.SaveWithOptionsCommand(collection, document, writeOption)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient insert(String collection, JsonObject document, Handler<AsyncResult<String>> resultHandler) {
    this.<String>call(new Insert.InsertCommand(collection, document)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient insertWithOptions(String collection, JsonObject document, @Nullable WriteOption writeOption, Handler<AsyncResult<String>> resultHandler) {
    this.<String>call(new InsertWithOptions.InsertWithOptionsCommand(collection, document, writeOption)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient update(String collection, JsonObject query, JsonObject update, Handler<AsyncResult<Void>> resultHandler) {
    throw new UnsupportedOperationException(DEPRECATED_MESSAGE);
  }

  @Override
  public MongoClient updateCollection(String collection, JsonObject query, JsonObject update, Handler<AsyncResult<MongoClientUpdateResult>> resultHandler) {
    this.<MongoClientUpdateResult>call(new UpdateCollection.UpdateCollectionCommand(collection, query, update)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient updateWithOptions(String collection, JsonObject query, JsonObject update, UpdateOptions options, Handler<AsyncResult<Void>> resultHandler) {
    throw new UnsupportedOperationException(DEPRECATED_MESSAGE);
  }

  @Override
  public MongoClient updateCollectionWithOptions(String collection, JsonObject query, JsonObject update, UpdateOptions options, Handler<AsyncResult<MongoClientUpdateResult>> resultHandler) {
    this.<MongoClientUpdateResult>call(new UpdateCollectionWithOptions.UpdateCollectionWithOptionsCommand(collection, query, update, options)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient replace(String collection, JsonObject query, JsonObject replace, Handler<AsyncResult<Void>> resultHandler) {
    throw new UnsupportedOperationException(DEPRECATED_MESSAGE);
  }

  @Override
  public MongoClient replaceDocuments(String collection, JsonObject query, JsonObject replace, Handler<AsyncResult<MongoClientUpdateResult>> resultHandler) {
    this.<MongoClientUpdateResult>call(new ReplaceDocuments.ReplaceDocumentsCommand(collection, query, replace)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient replaceWithOptions(String collection, JsonObject query, JsonObject replace, UpdateOptions options, Handler<AsyncResult<Void>> resultHandler) {
    throw new UnsupportedOperationException(DEPRECATED_MESSAGE);
  }

  @Override
  public MongoClient replaceDocumentsWithOptions(String collection, JsonObject query, JsonObject replace, UpdateOptions options, Handler<AsyncResult<MongoClientUpdateResult>> resultHandler) {
    this.<MongoClientUpdateResult>call(new ReplaceDocumentsWithOptions.ReplaceDocumentsWithOptionsCommand(collection, query, replace, options)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient bulkWrite(String collection, List<BulkOperation> operations, Handler<AsyncResult<MongoClientBulkWriteResult>> resultHandler) {
    this.<MongoClientBulkWriteResult>call(new BulkWrite.BulkWriteCommand(collection, operations)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient bulkWriteWithOptions(String collection, List<BulkOperation> operations, BulkWriteOptions bulkWriteOptions, Handler<AsyncResult<MongoClientBulkWriteResult>> resultHandler) {
    this.<MongoClientBulkWriteResult>call(new BulkWriteWithOptions.BulkWriteWithOptionsCommand(collection, operations, bulkWriteOptions)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient find(String collection, JsonObject query, Handler<AsyncResult<List<JsonObject>>> resultHandler) {
    this.<List<JsonObject>>call(new Find.FindCommand(collection, query)).setHandler(resultHandler);
    return this;
  }

  @Override
  public ReadStream<JsonObject> findBatch(String collection, JsonObject query) {
    return callStream(new FindBatch.FindBatchCommand(collection, query));
  }

  @Override
  public MongoClient findWithOptions(String collection, JsonObject query, FindOptions options, Handler<AsyncResult<List<JsonObject>>> resultHandler) {
    this.<List<JsonObject>>call(new FindWithOptions.FindWithOptionsCommand(collection, query, options)).setHandler(resultHandler);
    return this;
  }

  @Override
  public ReadStream<JsonObject> findBatchWithOptions(String collection, JsonObject query, FindOptions options) {
    return callStream(new FindBatchWithOptions.FindBatchWithOptionsCommand(collection, query, options));
  }

  @Override
  public MongoClient findOne(String collection, JsonObject query, @Nullable JsonObject fields, Handler<AsyncResult<JsonObject>> resultHandler) {
    this.<JsonObject>call(new FindOne.FindOneCommand(collection, query, fields)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient findOneAndUpdate(String collection, JsonObject query, JsonObject update, Handler<AsyncResult<JsonObject>> resultHandler) {
    this.<JsonObject>call(new FindOneAndUpdate.FindOneAndUpdateCommand(collection, query, update)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient findOneAndUpdateWithOptions(String collection, JsonObject query, JsonObject update, FindOptions findOptions, UpdateOptions updateOptions, Handler<AsyncResult<JsonObject>> resultHandler) {
    this.<JsonObject>call(new FindOneAndUpdateWithOptions.FindOneAndUpdateWithOptionsCommand(collection, query, update, findOptions, updateOptions)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient findOneAndReplace(String collection, JsonObject query, JsonObject replace, Handler<AsyncResult<JsonObject>> resultHandler) {
    this.<JsonObject>call(new FindOneAndReplace.FindOneAndReplaceCommand(collection, query, replace)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient findOneAndReplaceWithOptions(String collection, JsonObject query, JsonObject replace, FindOptions findOptions, UpdateOptions updateOptions, Handler<AsyncResult<JsonObject>> resultHandler) {
    this.<JsonObject>call(new FindOneAndReplaceWithOptions.FindOneAndReplaceWithOptionsCommand(collection, query, replace, findOptions, updateOptions)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient findOneAndDelete(String collection, JsonObject query, Handler<AsyncResult<JsonObject>> resultHandler) {
    this.<JsonObject>call(new FindOneAndDelete.FindOneAndDeleteCommand(collection, query)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient findOneAndDeleteWithOptions(String collection, JsonObject query, FindOptions findOptions, Handler<AsyncResult<JsonObject>> resultHandler) {
    this.<JsonObject>call(new FindOneAndDeleteWithOptions.FindOneAndDeleteWithOptionsCommand(collection, query, findOptions)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient count(String collection, JsonObject query, Handler<AsyncResult<Long>> resultHandler) {
    this.<Long>call(new Count.CountCommand(collection, query)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient remove(String collection, JsonObject query, Handler<AsyncResult<Void>> resultHandler) {
    throw new UnsupportedOperationException(DEPRECATED_MESSAGE);
  }

  @Override
  public MongoClient removeDocuments(String collection, JsonObject query, Handler<AsyncResult<MongoClientDeleteResult>> resultHandler) {
    this.<MongoClientDeleteResult>call(new RemoveDocuments.RemoveDocumentsCommand(collection, query)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient removeWithOptions(String collection, JsonObject query, WriteOption writeOption, Handler<AsyncResult<Void>> resultHandler) {
    throw new UnsupportedOperationException(DEPRECATED_MESSAGE);
  }

  @Override
  public MongoClient removeDocumentsWithOptions(String collection, JsonObject query, @Nullable WriteOption writeOption, Handler<AsyncResult<MongoClientDeleteResult>> resultHandler) {
    this.<MongoClientDeleteResult>call(new RemoveDocumentsWithOptions.RemoveDocumentsWithOptionsCommand(collection, query, writeOption)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient removeOne(String collection, JsonObject query, Handler<AsyncResult<Void>> resultHandler) {
    throw new UnsupportedOperationException(DEPRECATED_MESSAGE);
  }

  @Override
  public MongoClient removeDocument(String collection, JsonObject query, Handler<AsyncResult<MongoClientDeleteResult>> resultHandler) {
    this.<MongoClientDeleteResult>call(new RemoveDocument.RemoveDocumentCommand(collection, query)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient removeOneWithOptions(String collection, JsonObject query, WriteOption writeOption, Handler<AsyncResult<Void>> resultHandler) {
    throw new UnsupportedOperationException(DEPRECATED_MESSAGE);
  }

  @Override
  public MongoClient removeDocumentWithOptions(String collection, JsonObject query, @Nullable WriteOption writeOption, Handler<AsyncResult<MongoClientDeleteResult>> resultHandler) {
    this.<MongoClientDeleteResult>call(new RemoveDocumentWithOptions.RemoveDocumentWithOptionsCommand(collection, query, writeOption)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient createCollection(String collection, Handler<AsyncResult<Void>> resultHandler) {
    this.<Void>call(new CreateCollection.CreateCollectionCommand(collection)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient getCollections(Handler<AsyncResult<List<String>>> resultHandler) {
    this.<List<String>>call(new GetCollections.GetCollectionsCommand()).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient dropCollection(String collection, Handler<AsyncResult<Void>> resultHandler) {
    this.<Void>call(new DropCollection.DropCollectionCommand(collection)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient createIndex(String collection, JsonObject key, Handler<AsyncResult<Void>> resultHandler) {
    this.<Void>call(new CreateIndex.CreateIndexCommand(collection, key)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient createIndexWithOptions(String collection, JsonObject key, IndexOptions options, Handler<AsyncResult<Void>> resultHandler) {
    this.<Void>call(new CreateIndexWithOptions.CreateIndexWithOptionsCommand(collection, key, options)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient listIndexes(String collection, Handler<AsyncResult<JsonArray>> resultHandler) {
    this.<JsonArray>call(new ListIndexes.ListIndexesCommand(collection)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient dropIndex(String collection, String indexName, Handler<AsyncResult<Void>> resultHandler) {
    this.<Void>call(new DropIndex.DropIndexCommand(collection, indexName)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient runCommand(String commandName, JsonObject command, Handler<AsyncResult<JsonObject>> resultHandler) {
    this.<JsonObject>call(new RunCommand.RunCommandCommand(commandName, command)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient distinct(String collection, String fieldName, String resultClassname, Handler<AsyncResult<JsonArray>> resultHandler) {
    this.<JsonArray>call(new Distinct.DistinctCommand(collection, fieldName, resultClassname)).setHandler(resultHandler);
    return this;
  }

  @Override
  public MongoClient distinctWithQuery(String collection, String fieldName, String resultClassname, JsonObject query, Handler<AsyncResult<JsonArray>> resultHandler) {
    this.<JsonArray>call(new DistinctWithQuery.DistinctWithQueryCommand(collection, fieldName, resultClassname, query)).setHandler(resultHandler);
    return this;
  }

  @Override
  public ReadStream<JsonObject> distinctBatch(String collection, String fieldName, String resultClassname) {
    return callStream(new DistinctBatch.DistinctBatchCommand(collection, fieldName, resultClassname));
  }

  @Override
  public ReadStream<JsonObject> distinctBatchWithQuery(String collection, String fieldName, String resultClassname, JsonObject query) {
    return callStream(new DistinctBatchWithQuery.DistinctBatchWithQueryCommand(collection, fieldName, resultClassname, query));
  }

  @Override
  public ReadStream<JsonObject> distinctBatchWithQuery(String collection, String fieldName, String resultClassname, JsonObject query, int batchSize) {
    return callStream(new DistinctBatchWithQuery.DistinctBatchWithQueryCommand(collection, fieldName, resultClassname, query, batchSize));
  }

  @Override
  public ReadStream<JsonObject> aggregate(String collection, JsonArray pipeline) {
    return callStream(new Aggregate.AggregateCommand(collection, pipeline));
  }

  @Override
  public ReadStream<JsonObject> aggregateWithOptions(String collection, JsonArray pipeline, AggregateOptions options) {
    return callStream(new AggregateWithOptions.AggregateWithOptionsCommand(collection, pipeline, options));
  }

  @Override
  public void close() {
    //empty close
  }

  public static synchronized WireMongoClient createShared(Vertx vertx, JsonObject config, String dataSource) {
    if (instance == null) {
      instance = new WireMongoClient();
    }
    return instance;
  }
}
