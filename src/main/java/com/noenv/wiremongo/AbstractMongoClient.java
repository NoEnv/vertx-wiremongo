package com.noenv.wiremongo;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.streams.ReadStream;
import io.vertx.ext.mongo.*;
import org.bson.types.ObjectId;

import java.util.List;

public class AbstractMongoClient implements MongoClient {
  @Override
  public MongoClient save(String collection, JsonObject document, Handler<AsyncResult<String>> resultHandler) {
    resultHandler.handle(Future.succeededFuture(ObjectId.get().toHexString()));
    return this;
  }

  @Override
  public MongoClient saveWithOptions(String collection, JsonObject document, @Nullable WriteOption writeOption, Handler<AsyncResult<String>> resultHandler) {
    resultHandler.handle(Future.succeededFuture(ObjectId.get().toHexString()));
    return this;
  }

  @Override
  public MongoClient insert(String collection, JsonObject document, Handler<AsyncResult<String>> resultHandler) {
    return save(collection,document,resultHandler);
  }

  @Override
  public MongoClient insertWithOptions(String collection, JsonObject document, @Nullable WriteOption writeOption, Handler<AsyncResult<String>> resultHandler) {
    return save(collection,document,resultHandler);
  }

  @Override
  public MongoClient update(String collection, JsonObject query, JsonObject update, Handler<AsyncResult<Void>> resultHandler) {
    resultHandler.handle(Future.succeededFuture());
    return this;
  }

  @Override
  public MongoClient updateCollection(String collection, JsonObject query, JsonObject update, Handler<AsyncResult<MongoClientUpdateResult>> resultHandler) {
    resultHandler.handle(Future.succeededFuture(new MongoClientUpdateResult()));
    return this;
  }

  @Override
  public MongoClient updateWithOptions(String collection, JsonObject query, JsonObject update, UpdateOptions options, Handler<AsyncResult<Void>> resultHandler) {
    resultHandler.handle(Future.succeededFuture());
    return this;
  }

  @Override
  public MongoClient updateCollectionWithOptions(String collection, JsonObject query, JsonObject update, UpdateOptions options, Handler<AsyncResult<MongoClientUpdateResult>> resultHandler) {
    resultHandler.handle(Future.succeededFuture(new MongoClientUpdateResult()));
    return this;
  }

  @Override
  public MongoClient replace(String collection, JsonObject query, JsonObject replace, Handler<AsyncResult<Void>> resultHandler) {
    resultHandler.handle(Future.succeededFuture());
    return this;
  }

  @Override
  public MongoClient replaceDocuments(String collection, JsonObject query, JsonObject replace, Handler<AsyncResult<MongoClientUpdateResult>> resultHandler) {
    resultHandler.handle(Future.succeededFuture(new MongoClientUpdateResult()));
    return this;
  }

  @Override
  public MongoClient replaceWithOptions(String collection, JsonObject query, JsonObject replace, UpdateOptions options, Handler<AsyncResult<Void>> resultHandler) {
    resultHandler.handle(Future.succeededFuture());
    return this;
  }

  @Override
  public MongoClient replaceDocumentsWithOptions(String collection, JsonObject query, JsonObject replace, UpdateOptions options, Handler<AsyncResult<MongoClientUpdateResult>> resultHandler) {
    resultHandler.handle(Future.succeededFuture(new MongoClientUpdateResult()));
    return this;
  }

  @Override
  public MongoClient bulkWrite(String collection, List<BulkOperation> operations, Handler<AsyncResult<MongoClientBulkWriteResult>> resultHandler) {
    resultHandler.handle(Future.succeededFuture(new MongoClientBulkWriteResult()));
    return this;
  }

  @Override
  public MongoClient bulkWriteWithOptions(String collection, List<BulkOperation> operations, BulkWriteOptions bulkWriteOptions, Handler<AsyncResult<MongoClientBulkWriteResult>> resultHandler) {
    resultHandler.handle(Future.succeededFuture(new MongoClientBulkWriteResult()));
    return this;
  }

  @Override
  public MongoClient find(String collection, JsonObject query, Handler<AsyncResult<List<JsonObject>>> resultHandler) {
    resultHandler.handle(Future.succeededFuture());
    return this;
  }

  @Override
  public ReadStream<JsonObject> findBatch(String collection, JsonObject query) {
    return null;
  }

  @Override
  public MongoClient findWithOptions(String collection, JsonObject query, FindOptions options, Handler<AsyncResult<List<JsonObject>>> resultHandler) {
    resultHandler.handle(Future.succeededFuture());
    return this;
  }

  @Override
  public ReadStream<JsonObject> findBatchWithOptions(String collection, JsonObject query, FindOptions options) {
    return null;
  }

  @Override
  public MongoClient findOne(String collection, JsonObject query, @Nullable JsonObject fields, Handler<AsyncResult<JsonObject>> resultHandler) {
    resultHandler.handle(Future.succeededFuture());
    return this;
  }

  @Override
  public MongoClient findOneAndUpdate(String collection, JsonObject query, JsonObject update, Handler<AsyncResult<JsonObject>> resultHandler) {
    resultHandler.handle(Future.succeededFuture(new JsonObject()));
    return this;
  }

  @Override
  public MongoClient findOneAndUpdateWithOptions(String collection, JsonObject query, JsonObject update, FindOptions findOptions, UpdateOptions updateOptions, Handler<AsyncResult<JsonObject>> resultHandler) {
    resultHandler.handle(Future.succeededFuture(new JsonObject()));
    return this;
  }

  @Override
  public MongoClient findOneAndReplace(String collection, JsonObject query, JsonObject replace, Handler<AsyncResult<JsonObject>> resultHandler) {
    resultHandler.handle(Future.succeededFuture(new JsonObject()));
    return this;
  }

  @Override
  public MongoClient findOneAndReplaceWithOptions(String collection, JsonObject query, JsonObject replace, FindOptions findOptions, UpdateOptions updateOptions, Handler<AsyncResult<JsonObject>> resultHandler) {
    resultHandler.handle(Future.succeededFuture(new JsonObject()));
    return this;
  }

  @Override
  public MongoClient findOneAndDelete(String collection, JsonObject query, Handler<AsyncResult<JsonObject>> resultHandler) {
    resultHandler.handle(Future.succeededFuture(new JsonObject()));
    return this;
  }

  @Override
  public MongoClient findOneAndDeleteWithOptions(String collection, JsonObject query, FindOptions findOptions, Handler<AsyncResult<JsonObject>> resultHandler) {
    resultHandler.handle(Future.succeededFuture(new JsonObject()));
    return this;
  }

  @Override
  public MongoClient count(String collection, JsonObject query, Handler<AsyncResult<Long>> resultHandler) {
    resultHandler.handle(Future.succeededFuture(0L));
    return this;
  }

  @Override
  public MongoClient remove(String collection, JsonObject query, Handler<AsyncResult<Void>> resultHandler) {
    resultHandler.handle(Future.succeededFuture());
    return this;
  }

  @Override
  public MongoClient removeDocuments(String collection, JsonObject query, Handler<AsyncResult<MongoClientDeleteResult>> resultHandler) {
    resultHandler.handle(Future.succeededFuture(new MongoClientDeleteResult()));
    return this;
  }

  @Override
  public MongoClient removeWithOptions(String collection, JsonObject query, WriteOption writeOption, Handler<AsyncResult<Void>> resultHandler) {
    resultHandler.handle(Future.succeededFuture());
    return this;
  }

  @Override
  public MongoClient removeDocumentsWithOptions(String collection, JsonObject query, @Nullable WriteOption writeOption, Handler<AsyncResult<MongoClientDeleteResult>> resultHandler) {
    return removeDocument(collection,query,resultHandler);
  }

  @Override
  public MongoClient removeOne(String collection, JsonObject query, Handler<AsyncResult<Void>> resultHandler) {
    return remove(collection,query,resultHandler);
  }

  @Override
  public MongoClient removeDocument(String collection, JsonObject query, Handler<AsyncResult<MongoClientDeleteResult>> resultHandler) {
    return removeDocuments(collection,query,resultHandler);
  }

  @Override
  public MongoClient removeOneWithOptions(String collection, JsonObject query, WriteOption writeOption, Handler<AsyncResult<Void>> resultHandler) {
    return removeOne(collection,query,resultHandler);
  }

  @Override
  public MongoClient removeDocumentWithOptions(String collection, JsonObject query, @Nullable WriteOption writeOption, Handler<AsyncResult<MongoClientDeleteResult>> resultHandler) {
    return removeDocument(collection,query,resultHandler);
  }

  @Override
  public MongoClient createCollection(String collectionName, Handler<AsyncResult<Void>> resultHandler) {
    resultHandler.handle(Future.succeededFuture());
    return this;
  }

  @Override
  public MongoClient getCollections(Handler<AsyncResult<List<String>>> resultHandler) {
    resultHandler.handle(Future.succeededFuture());
    return this;
  }

  @Override
  public MongoClient dropCollection(String collection, Handler<AsyncResult<Void>> resultHandler) {
    resultHandler.handle(Future.succeededFuture());
    return this;
  }

  @Override
  public MongoClient createIndex(String collection, JsonObject key, Handler<AsyncResult<Void>> resultHandler) {
    resultHandler.handle(Future.succeededFuture());
    return this;
  }

  @Override
  public MongoClient createIndexWithOptions(String collection, JsonObject key, IndexOptions options, Handler<AsyncResult<Void>> resultHandler) {
    resultHandler.handle(Future.succeededFuture());
    return this;
  }

  @Override
  public MongoClient listIndexes(String collection, Handler<AsyncResult<JsonArray>> resultHandler) {
    resultHandler.handle(Future.succeededFuture(new JsonArray()));
    return this;
  }

  @Override
  public MongoClient dropIndex(String collection, String indexName, Handler<AsyncResult<Void>> resultHandler) {
    resultHandler.handle(Future.succeededFuture());
    return this;
  }

  @Override
  public MongoClient runCommand(String commandName, JsonObject command, Handler<AsyncResult<JsonObject>> resultHandler) {
    resultHandler.handle(Future.succeededFuture(new JsonObject()));
    return this;
  }

  @Override
  public MongoClient distinct(String collection, String fieldName, String resultClassname, Handler<AsyncResult<JsonArray>> resultHandler) {
    resultHandler.handle(Future.succeededFuture());
    return this;
  }

  @Override
  public MongoClient distinctWithQuery(String collection, String fieldName, String resultClassname, JsonObject query, Handler<AsyncResult<JsonArray>> resultHandler) {
    resultHandler.handle(Future.succeededFuture());
    return this;
  }

  @Override
  public ReadStream<JsonObject> distinctBatch(String collection, String fieldName, String resultClassname) {
    return null;
  }

  @Override
  public ReadStream<JsonObject> distinctBatchWithQuery(String collection, String fieldName, String resultClassname, JsonObject query) {
    return null;
  }

  @Override
  public ReadStream<JsonObject> distinctBatchWithQuery(String collection, String fieldName, String resultClassname, JsonObject query, int batchSize) {
    return null;
  }

  @Override
  public ReadStream<JsonObject> aggregate(String collection, JsonArray pipeline) {
    return null;
  }

  @Override
  public ReadStream<JsonObject> aggregateWithOptions(String collection, JsonArray pipeline, AggregateOptions options) {
    return null;
  }

  @Override
  public void close() {
    // intentional
  }
}
