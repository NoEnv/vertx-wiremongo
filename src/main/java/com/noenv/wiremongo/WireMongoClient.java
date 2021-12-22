package com.noenv.wiremongo;

import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.CountCommand;
import com.noenv.wiremongo.command.CountWithOptionsCommand;
import com.noenv.wiremongo.command.RunCommandCommand;
import com.noenv.wiremongo.command.aggregate.AggregateBaseCommand;
import com.noenv.wiremongo.command.aggregate.AggregateWithOptionsCommand;
import com.noenv.wiremongo.command.bulkwrite.BulkWriteBaseCommand;
import com.noenv.wiremongo.command.bulkwrite.BulkWriteWithOptionsCommand;
import com.noenv.wiremongo.command.collection.CreateCollectionCommand;
import com.noenv.wiremongo.command.collection.CreateCollectionWithOptionsCommand;
import com.noenv.wiremongo.command.collection.DropCollectionCommand;
import com.noenv.wiremongo.command.collection.GetCollectionsCommand;
import com.noenv.wiremongo.command.distinct.*;
import com.noenv.wiremongo.command.find.*;
import com.noenv.wiremongo.command.index.*;
import com.noenv.wiremongo.command.insert.InsertBaseCommand;
import com.noenv.wiremongo.command.insert.InsertWithOptionsCommand;
import com.noenv.wiremongo.command.remove.RemoveDocumentBaseCommand;
import com.noenv.wiremongo.command.remove.RemoveDocumentWithOptionsCommand;
import com.noenv.wiremongo.command.remove.RemoveDocumentsBaseCommand;
import com.noenv.wiremongo.command.remove.RemoveDocumentsWithOptionsCommand;
import com.noenv.wiremongo.command.replace.ReplaceDocumentsBaseCommand;
import com.noenv.wiremongo.command.replace.ReplaceDocumentsWithOptionsCommand;
import com.noenv.wiremongo.command.save.SaveBaseCommand;
import com.noenv.wiremongo.command.save.SaveWithOptionsCommand;
import com.noenv.wiremongo.command.update.UpdateCollectionBaseCommand;
import com.noenv.wiremongo.command.update.UpdateCollectionWithOptionsCommand;
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

  private <T, U extends Command> Future<T> call(U request) {
    return wireMongo.call(request);
  }

  private ReadStream<JsonObject> callStream(Command request) {
    return wireMongo.callStream(request);
  }

  @Override
  public MongoClient save(String collection, JsonObject document, Handler<AsyncResult<String>> handler) {
    save(collection, document).onComplete(handler);
    return this;
  }

  @Override
  public Future<String> save(String collection, JsonObject document) {
    return call(new SaveBaseCommand(collection, document));
  }

  @Override
  public MongoClient saveWithOptions(String collection, JsonObject document, @Nullable WriteOption writeOption, Handler<AsyncResult<String>> handler) {
    saveWithOptions(collection, document, writeOption).onComplete(handler);
    return this;
  }

  @Override
  public Future<String> saveWithOptions(String collection, JsonObject document, @Nullable WriteOption writeOption) {
    return call(new SaveWithOptionsCommand(collection, document, writeOption));
  }

  @Override
  public MongoClient insert(String collection, JsonObject document, Handler<AsyncResult<String>> handler) {
    insert(collection, document).onComplete(handler);
    return this;
  }

  @Override
  public Future<String> insert(String collection, JsonObject document) {
    return call(new InsertBaseCommand(collection, document));
  }

  @Override
  public MongoClient insertWithOptions(String collection, JsonObject document, @Nullable WriteOption writeOption, Handler<AsyncResult<String>> handler) {
    insertWithOptions(collection, document, writeOption).onComplete(handler);
    return this;
  }

  @Override
  public Future<String> insertWithOptions(String collection, JsonObject document, @Nullable WriteOption writeOption) {
    return call(new InsertWithOptionsCommand(collection, document, writeOption));
  }

  @Override
  public MongoClient updateCollection(String collection, JsonObject query, JsonObject update, Handler<AsyncResult<MongoClientUpdateResult>> handler) {
    updateCollection(collection, query, update).onComplete(handler);
    return this;
  }

  @Override
  public Future<MongoClientUpdateResult> updateCollection(String collection, JsonObject query, JsonObject update) {
    return call(new UpdateCollectionBaseCommand<>(collection, query, update));
  }

  @Override
  public MongoClient updateCollection(String collection, JsonObject query, JsonArray update, Handler<AsyncResult<MongoClientUpdateResult>> handler) {
    updateCollection(collection, query, update).onComplete(handler);
    return this;
  }

  @Override
  public Future<MongoClientUpdateResult> updateCollection(String collection, JsonObject query, JsonArray update) {
    return call(new UpdateCollectionBaseCommand<>(collection, query, update));
  }

  @Override
  public MongoClient updateCollectionWithOptions(String collection, JsonObject query, JsonObject update, UpdateOptions options, Handler<AsyncResult<MongoClientUpdateResult>> handler) {
    updateCollectionWithOptions(collection, query, update, options).onComplete(handler);
    return this;
  }

  @Override
  public Future<MongoClientUpdateResult> updateCollectionWithOptions(String collection, JsonObject query, JsonObject update, UpdateOptions options) {
    return call(new UpdateCollectionWithOptionsCommand<>(collection, query, update, options));
  }

  @Override
  public MongoClient updateCollectionWithOptions(String collection, JsonObject query, JsonArray update, UpdateOptions updateOptions, Handler<AsyncResult<MongoClientUpdateResult>> handler) {
    updateCollectionWithOptions(collection, query, update, updateOptions).onComplete(handler);
    return this;
  }

  @Override
  public Future<MongoClientUpdateResult> updateCollectionWithOptions(String collection, JsonObject query, JsonArray update, UpdateOptions updateOptions) {
    return call(new UpdateCollectionWithOptionsCommand<>(collection, query, update, updateOptions));
  }

  @Override
  public MongoClient replaceDocuments(String collection, JsonObject query, JsonObject replace, Handler<AsyncResult<MongoClientUpdateResult>> handler) {
    replaceDocuments(collection, query, replace).onComplete(handler);
    return this;
  }

  @Override
  public Future<MongoClientUpdateResult> replaceDocuments(String collection, JsonObject query, JsonObject replace) {
    return call(new ReplaceDocumentsBaseCommand(collection, query, replace));
  }

  @Override
  public MongoClient replaceDocumentsWithOptions(String collection, JsonObject query, JsonObject replace, UpdateOptions options, Handler<AsyncResult<MongoClientUpdateResult>> handler) {
    replaceDocumentsWithOptions(collection, query, replace, options).onComplete(handler);
    return this;
  }

  @Override
  public Future<MongoClientUpdateResult> replaceDocumentsWithOptions(String collection, JsonObject query, JsonObject replace, UpdateOptions options) {
    return call(new ReplaceDocumentsWithOptionsCommand(collection, query, replace, options));
  }

  @Override
  public MongoClient bulkWrite(String collection, List<BulkOperation> operations, Handler<AsyncResult<MongoClientBulkWriteResult>> handler) {
    bulkWrite(collection, operations).onComplete(handler);
    return this;
  }

  @Override
  public Future<MongoClientBulkWriteResult> bulkWrite(String collection, List<BulkOperation> operations) {
    return call(new BulkWriteBaseCommand(collection, operations));
  }

  @Override
  public MongoClient bulkWriteWithOptions(String collection, List<BulkOperation> operations, BulkWriteOptions bulkWriteOptions, Handler<AsyncResult<MongoClientBulkWriteResult>> handler) {
    bulkWriteWithOptions(collection, operations, bulkWriteOptions).onComplete(handler);
    return this;
  }

  @Override
  public Future<MongoClientBulkWriteResult> bulkWriteWithOptions(String collection, List<BulkOperation> operations, BulkWriteOptions bulkWriteOptions) {
    return call(new BulkWriteWithOptionsCommand(collection, operations, bulkWriteOptions));
  }

  @Override
  public MongoClient find(String collection, JsonObject query, Handler<AsyncResult<List<JsonObject>>> handler) {
    find(collection, query).onComplete(handler);
    return this;
  }

  @Override
  public Future<List<JsonObject>> find(String collection, JsonObject query) {
    return call(new FindBaseCommand(collection, query));
  }

  @Override
  public ReadStream<JsonObject> findBatch(String collection, JsonObject query) {
    return callStream(new FindBatchBaseCommand(collection, query));
  }

  @Override
  public MongoClient findWithOptions(String collection, JsonObject query, FindOptions options, Handler<AsyncResult<List<JsonObject>>> handler) {
    findWithOptions(collection, query, options).onComplete(handler);
    return this;
  }

  @Override
  public Future<List<JsonObject>> findWithOptions(String collection, JsonObject query, FindOptions options) {
    return call(new FindWithOptionsCommand(collection, query, options));
  }

  @Override
  public ReadStream<JsonObject> findBatchWithOptions(String collection, JsonObject query, FindOptions options) {
    return callStream(new FindBatchWithOptionsCommand(collection, query, options));
  }

  @Override
  public MongoClient findOne(String collection, JsonObject query, @Nullable JsonObject fields, Handler<AsyncResult<JsonObject>> handler) {
    findOne(collection, query, fields).onComplete(handler);
    return this;
  }

  @Override
  public Future<JsonObject> findOne(String collection, JsonObject query, @Nullable JsonObject fields) {
    return call(new FindOneCommand(collection, query, fields));
  }

  @Override
  public MongoClient findOneAndUpdate(String collection, JsonObject query, JsonObject update, Handler<AsyncResult<JsonObject>> handler) {
    findOneAndUpdate(collection, query, update).onComplete(handler);
    return this;
  }

  @Override
  public Future<JsonObject> findOneAndUpdate(String collection, JsonObject query, JsonObject update) {
    return call(new FindOneAndUpdateBaseCommand(collection, query, update));
  }

  @Override
  public MongoClient findOneAndUpdateWithOptions(String collection, JsonObject query, JsonObject update, FindOptions findOptions, UpdateOptions updateOptions, Handler<AsyncResult<JsonObject>> handler) {
    findOneAndUpdateWithOptions(collection, query, update, findOptions, updateOptions).onComplete(handler);
    return this;
  }

  @Override
  public Future<JsonObject> findOneAndUpdateWithOptions(String collection, JsonObject query, JsonObject update, FindOptions findOptions, UpdateOptions updateOptions) {
    return call(new FindOneAndUpdateWithOptionsCommand(collection, query, update, findOptions, updateOptions));
  }

  @Override
  public MongoClient findOneAndReplace(String collection, JsonObject query, JsonObject replace, Handler<AsyncResult<JsonObject>> handler) {
    findOneAndReplace(collection, query, replace).onComplete(handler);
    return this;
  }

  @Override
  public Future<JsonObject> findOneAndReplace(String collection, JsonObject query, JsonObject replace) {
    return call(new FindOneAndReplaceBaseCommand(collection, query, replace));
  }

  @Override
  public MongoClient findOneAndReplaceWithOptions(String collection, JsonObject query, JsonObject replace, FindOptions findOptions, UpdateOptions updateOptions, Handler<AsyncResult<JsonObject>> handler) {
    findOneAndReplaceWithOptions(collection, query, replace, findOptions, updateOptions).onComplete(handler);
    return this;
  }

  @Override
  public Future<JsonObject> findOneAndReplaceWithOptions(String collection, JsonObject query, JsonObject replace, FindOptions findOptions, UpdateOptions updateOptions) {
    return call(new FindOneAndReplaceWithOptionsCommand(collection, query, replace, findOptions, updateOptions));
  }

  @Override
  public MongoClient findOneAndDelete(String collection, JsonObject query, Handler<AsyncResult<JsonObject>> resultHandler) {
    findOneAndDelete(collection, query).onComplete(resultHandler);
    return this;
  }

  @Override
  public Future<JsonObject> findOneAndDelete(String collection, JsonObject query) {
    return call(new FindOneAndDeleteBaseCommand(collection, query));
  }

  @Override
  public MongoClient findOneAndDeleteWithOptions(String collection, JsonObject query, FindOptions findOptions, Handler<AsyncResult<JsonObject>> handler) {
    findOneAndDeleteWithOptions(collection, query, findOptions).onComplete(handler);
    return this;
  }

  @Override
  public Future<JsonObject> findOneAndDeleteWithOptions(String collection, JsonObject query, FindOptions findOptions) {
    return call(new FindOneAndDeleteWithOptionsCommand(collection, query, findOptions));
  }

  @Override
  public MongoClient count(String collection, JsonObject query, Handler<AsyncResult<Long>> handler) {
    count(collection, query).onComplete(handler);
    return this;
  }

  @Override
  public Future<Long> count(String collection, JsonObject query) {
    return call(new CountCommand(collection, query));
  }

  @Override
  public MongoClient countWithOptions(String collection, JsonObject query, CountOptions countOptions, Handler<AsyncResult<Long>> handler) {
    countWithOptions(collection, query, countOptions).onComplete(handler);
    return this;
  }

  @Override
  public Future<Long> countWithOptions(String collection, JsonObject query, CountOptions countOptions) {
    return call(new CountWithOptionsCommand(collection, query, countOptions));
  }

  @Override
  public MongoClient removeDocuments(String collection, JsonObject query, Handler<AsyncResult<MongoClientDeleteResult>> handler) {
    removeDocuments(collection, query).onComplete(handler);
    return this;
  }

  @Override
  public Future<MongoClientDeleteResult> removeDocuments(String collection, JsonObject query) {
    return call(new RemoveDocumentsBaseCommand(collection, query));
  }

  @Override
  public MongoClient removeDocumentsWithOptions(String collection, JsonObject query, @Nullable WriteOption writeOption, Handler<AsyncResult<MongoClientDeleteResult>> handler) {
    removeDocumentsWithOptions(collection, query, writeOption).onComplete(handler);
    return this;
  }

  @Override
  public Future<MongoClientDeleteResult> removeDocumentsWithOptions(String collection, JsonObject query, @Nullable WriteOption writeOption) {
    return call(new RemoveDocumentsWithOptionsCommand(collection, query, writeOption));
  }

  @Override
  public MongoClient removeDocument(String collection, JsonObject query, Handler<AsyncResult<MongoClientDeleteResult>> handler) {
    removeDocument(collection, query).onComplete(handler);
    return this;
  }

  @Override
  public Future<MongoClientDeleteResult> removeDocument(String collection, JsonObject query) {
    return call(new RemoveDocumentBaseCommand(collection, query));
  }

  @Override
  public MongoClient removeDocumentWithOptions(String collection, JsonObject query, @Nullable WriteOption writeOption, Handler<AsyncResult<MongoClientDeleteResult>> handler) {
    removeDocumentWithOptions(collection, query, writeOption).onComplete(handler);
    return this;
  }

  @Override
  public Future<MongoClientDeleteResult> removeDocumentWithOptions(String collection, JsonObject query, @Nullable WriteOption writeOption) {
    return call(new RemoveDocumentWithOptionsCommand(collection, query, writeOption));
  }

  @Override
  public MongoClient createCollection(String collection, Handler<AsyncResult<Void>> handler) {
    createCollection(collection).onComplete(handler);
    return this;
  }

  @Override
  public MongoClient createCollectionWithOptions(String collection, CreateCollectionOptions createCollectionOptions, Handler<AsyncResult<Void>> handler) {
    createCollectionWithOptions(collection, createCollectionOptions).onComplete(handler);
    return this;
  }

  @Override
  public Future<Void> createCollection(String collection) {
    return call(new CreateCollectionCommand(collection));
  }

  @Override
  public Future<Void> createCollectionWithOptions(String collection, CreateCollectionOptions createCollectionOptions) {
    return call(new CreateCollectionWithOptionsCommand(collection, createCollectionOptions));
  }

  @Override
  public MongoClient getCollections(Handler<AsyncResult<List<String>>> handler) {
    getCollections().onComplete(handler);
    return this;
  }

  @Override
  public Future<List<String>> getCollections() {
    return call(new GetCollectionsCommand());
  }

  @Override
  public MongoClient dropCollection(String collection, Handler<AsyncResult<Void>> handler) {
    dropCollection(collection).onComplete(handler);
    return this;
  }

  @Override
  public Future<Void> dropCollection(String collection) {
    return call(new DropCollectionCommand(collection));
  }

  @Override
  public MongoClient createIndex(String collection, JsonObject key, Handler<AsyncResult<Void>> handler) {
    createIndex(collection, key).onComplete(handler);
    return this;
  }

  @Override
  public Future<Void> createIndex(String collection, JsonObject key) {
    return call(new CreateIndexBaseCommand(collection, key));
  }

  @Override
  public MongoClient createIndexWithOptions(String collection, JsonObject key, IndexOptions options, Handler<AsyncResult<Void>> handler) {
    createIndexWithOptions(collection, key, options).onComplete(handler);
    return this;
  }

  @Override
  public Future<Void> createIndexWithOptions(String collection, JsonObject key, IndexOptions options) {
    return call(new CreateIndexWithOptionsCommand(collection, key, options));
  }

  @Override
  public MongoClient createIndexes(String s, List<IndexModel> list, Handler<AsyncResult<Void>> handler) {
    createIndexes(s, list).onComplete(handler);
    return this;
  }

  @Override
  public Future<Void> createIndexes(String collection, List<IndexModel> indexModels) {
    return call(new CreateIndexesCommand(collection, indexModels));
  }

  @Override
  public MongoClient listIndexes(String collection, Handler<AsyncResult<JsonArray>> handler) {
    listIndexes(collection).onComplete(handler);
    return this;
  }

  @Override
  public Future<JsonArray> listIndexes(String collection) {
    return call(new ListIndexesCommand(collection));
  }

  @Override
  public MongoClient dropIndex(String collection, String indexName, Handler<AsyncResult<Void>> handler) {
    dropIndex(collection, indexName).onComplete(handler);
    return this;
  }

  @Override
  public Future<Void> dropIndex(String collection, String indexName) {
    return call(new DropIndexCommand(collection, indexName));
  }

  @Override
  public MongoClient runCommand(String commandName, JsonObject command, Handler<AsyncResult<JsonObject>> handler) {
    runCommand(commandName, command).onComplete(handler);
    return this;
  }

  @Override
  public Future<JsonObject> runCommand(String commandName, JsonObject command) {
    return call(new RunCommandCommand(commandName, command));
  }

  @Override
  public MongoClient distinct(String collection, String fieldName, String resultClassname, Handler<AsyncResult<JsonArray>> handler) {
    distinct(collection, fieldName, resultClassname).onComplete(handler);
    return this;
  }

  @Override
  public MongoClient distinct(String collection, String fieldName, String resultClassname, Handler<AsyncResult<JsonArray>> handler, DistinctOptions distinctOptions) {
    distinct(collection, fieldName, resultClassname, distinctOptions).onComplete(handler);
    return this;
  }

  @Override
  public Future<JsonArray> distinct(String collection, String fieldName, String resultClassname) {
    return call(new DistinctCommand(collection, fieldName, resultClassname));
  }

  @Override
  public Future<JsonArray> distinct(String collection, String fieldName, String resultClassname, DistinctOptions distinctOptions) {
    return call(new DistinctCommand(collection, fieldName, resultClassname, distinctOptions));
  }

  @Override
  public MongoClient distinctWithQuery(String collection, String fieldName, String resultClassname, JsonObject query, Handler<AsyncResult<JsonArray>> handler) {
    distinctWithQuery(collection, fieldName, resultClassname, query).onComplete(handler);
    return this;
  }

  @Override
  public MongoClient distinctWithQuery(String collection, String fieldName, String resultClassname, JsonObject query, Handler<AsyncResult<JsonArray>> handler, DistinctOptions distinctOptions) {
    distinctWithQuery(collection, fieldName, resultClassname, query, distinctOptions).onComplete(handler);
    return this;
  }

  @Override
  public Future<JsonArray> distinctWithQuery(String collection, String fieldName, String resultClassname, JsonObject query) {
    return call(new DistinctWithQueryCommand(collection, fieldName, resultClassname, query));
  }

  @Override
  public Future<JsonArray> distinctWithQuery(String collection, String fieldName, String resultClassname, JsonObject query, DistinctOptions distinctOptions) {
    return call(new DistinctWithQueryCommand(collection, fieldName, resultClassname, query, distinctOptions));
  }

  @Override
  public ReadStream<JsonObject> distinctBatch(String collection, String fieldName, String resultClassname) {
    return callStream(new DistinctBatchBaseCommand(collection, fieldName, resultClassname));
  }

  @Override
  public ReadStream<JsonObject> distinctBatch(String collection, String fieldName, String resultClassname, DistinctOptions distinctOptions) {
    return callStream(new DistinctBatchBaseCommand(collection, fieldName, resultClassname, distinctOptions));
  }

  @Override
  public ReadStream<JsonObject> distinctBatchWithQuery(String collection, String fieldName, String resultClassname, JsonObject query) {
    return callStream(new DistinctBatchWithQueryCommand(collection, fieldName, resultClassname, query));
  }

  @Override
  public ReadStream<JsonObject> distinctBatchWithQuery(String collection, String fieldName, String resultClassname, JsonObject query, DistinctOptions options) {
    return callStream(new DistinctBatchWithQueryCommand(collection, fieldName, resultClassname, query, options));
  }

  @Override
  public ReadStream<JsonObject> distinctBatchWithQuery(String collection, String fieldName, String resultClassname, JsonObject query, int batchSize) {
    return callStream(new DistinctBatchWithQueryCommand(collection, fieldName, resultClassname, query, batchSize));
  }

  @Override
  public ReadStream<JsonObject> distinctBatchWithQuery(String collection, String fieldName, String resultClassname, JsonObject query, int batchSize, DistinctOptions options) {
    return callStream(new DistinctBatchWithQueryCommand(collection, fieldName, resultClassname, query, batchSize, options));
  }

  @Override
  public ReadStream<JsonObject> aggregate(String collection, JsonArray pipeline) {
    return callStream(new AggregateBaseCommand(collection, pipeline));
  }

  @Override
  public ReadStream<JsonObject> aggregateWithOptions(String collection, JsonArray pipeline, AggregateOptions options) {
    return callStream(new AggregateWithOptionsCommand(collection, pipeline, options));
  }

  @Override
  public MongoClient createDefaultGridFsBucketService(Handler<AsyncResult<MongoGridFsClient>> handler) {
    createDefaultGridFsBucketService().onComplete(handler);
    return this;
  }

  @Override
  public Future<MongoGridFsClient> createDefaultGridFsBucketService() {
    throw new UnsupportedOperationException("not implemented");
  }

  @Override
  public MongoClient createGridFsBucketService(String s, Handler<AsyncResult<MongoGridFsClient>> handler) {
    createGridFsBucketService(s).onComplete(handler);
    return this;
  }

  @Override
  public Future<MongoGridFsClient> createGridFsBucketService(String s) {
    throw new UnsupportedOperationException("not implemented");
  }

  @Override
  public ReadStream<ChangeStreamDocument<JsonObject>> watch(String s, JsonArray jsonArray, boolean b, int i) {
    throw new UnsupportedOperationException("not implemented");
  }

  @Override
  public void close(Handler<AsyncResult<Void>> handler) {
    close().onComplete(handler);
  }

  @Override
  public Future<Void> close() {
    return Future.succeededFuture();
  }

  public static synchronized WireMongoClient createShared(Vertx vertx, JsonObject config, String dataSource) {
    if (instance == null) {
      instance = new WireMongoClient();
    }
    return instance;
  }
}
