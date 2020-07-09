package com.noenv.wiremongo;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.RunCommandCommand;
import com.noenv.wiremongo.command.aggregate.AggregateBaseCommand;
import com.noenv.wiremongo.command.aggregate.AggregateWithOptionsCommand;
import com.noenv.wiremongo.command.bulkwrite.BulkWriteBaseCommand;
import com.noenv.wiremongo.command.bulkwrite.BulkWriteWithOptionsCommand;
import com.noenv.wiremongo.command.collection.CreateCollectionCommand;
import com.noenv.wiremongo.command.collection.DropCollectionCommand;
import com.noenv.wiremongo.command.collection.GetCollectionsCommand;
import com.noenv.wiremongo.command.CountCommand;
import com.noenv.wiremongo.command.distinct.DistinctBatchBaseCommand;
import com.noenv.wiremongo.command.distinct.DistinctBatchWithQueryCommand;
import com.noenv.wiremongo.command.distinct.DistinctCommand;
import com.noenv.wiremongo.command.distinct.DistinctWithQueryCommand;
import com.noenv.wiremongo.command.find.*;
import com.noenv.wiremongo.command.index.CreateIndexBaseCommand;
import com.noenv.wiremongo.command.index.CreateIndexWithOptionsCommand;
import com.noenv.wiremongo.command.index.DropIndexCommand;
import com.noenv.wiremongo.command.index.ListIndexesCommand;
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

  private <T, U extends Command> Future<T> call(U request) {
    return wireMongo.call(request);
  }

  private ReadStream<JsonObject> callStream(Command request) {
    return wireMongo.callStream(request);
  }

  @Override
  public MongoClient save(String collection, JsonObject document, Handler<AsyncResult<String>> resultHandler) {
    this.<String, SaveBaseCommand>call(new SaveBaseCommand(collection, document)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient saveWithOptions(String collection, JsonObject document, @Nullable WriteOption writeOption, Handler<AsyncResult<String>> resultHandler) {
    this.<String, SaveWithOptionsCommand>call(new SaveWithOptionsCommand(collection, document, writeOption)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient insert(String collection, JsonObject document, Handler<AsyncResult<String>> resultHandler) {
    this.<String, InsertBaseCommand>call(new InsertBaseCommand(collection, document)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient insertWithOptions(String collection, JsonObject document, @Nullable WriteOption writeOption, Handler<AsyncResult<String>> resultHandler) {
    this.<String, InsertWithOptionsCommand>call(new InsertWithOptionsCommand(collection, document, writeOption)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient update(String collection, JsonObject query, JsonObject update, Handler<AsyncResult<Void>> resultHandler) {
    throw new UnsupportedOperationException(DEPRECATED_MESSAGE);
  }

  @Override
  public MongoClient updateCollection(String collection, JsonObject query, JsonObject update, Handler<AsyncResult<MongoClientUpdateResult>> resultHandler) {
    this.<MongoClientUpdateResult, UpdateCollectionBaseCommand>call(new UpdateCollectionBaseCommand(collection, query, update)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient updateWithOptions(String collection, JsonObject query, JsonObject update, UpdateOptions options, Handler<AsyncResult<Void>> resultHandler) {
    throw new UnsupportedOperationException(DEPRECATED_MESSAGE);
  }

  @Override
  public MongoClient updateCollectionWithOptions(String collection, JsonObject query, JsonObject update, UpdateOptions options, Handler<AsyncResult<MongoClientUpdateResult>> resultHandler) {
    this.<MongoClientUpdateResult, UpdateCollectionWithOptionsCommand>call(new UpdateCollectionWithOptionsCommand(collection, query, update, options)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient replace(String collection, JsonObject query, JsonObject replace, Handler<AsyncResult<Void>> resultHandler) {
    throw new UnsupportedOperationException(DEPRECATED_MESSAGE);
  }

  @Override
  public MongoClient replaceDocuments(String collection, JsonObject query, JsonObject replace, Handler<AsyncResult<MongoClientUpdateResult>> resultHandler) {
    this.<MongoClientUpdateResult, ReplaceDocumentsBaseCommand>call(new ReplaceDocumentsBaseCommand(collection, query, replace)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient replaceWithOptions(String collection, JsonObject query, JsonObject replace, UpdateOptions options, Handler<AsyncResult<Void>> resultHandler) {
    throw new UnsupportedOperationException(DEPRECATED_MESSAGE);
  }

  @Override
  public MongoClient replaceDocumentsWithOptions(String collection, JsonObject query, JsonObject replace, UpdateOptions options, Handler<AsyncResult<MongoClientUpdateResult>> resultHandler) {
    this.<MongoClientUpdateResult, ReplaceDocumentsWithOptionsCommand>call(new ReplaceDocumentsWithOptionsCommand(collection, query, replace, options)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient bulkWrite(String collection, List<BulkOperation> operations, Handler<AsyncResult<MongoClientBulkWriteResult>> resultHandler) {
    this.<MongoClientBulkWriteResult, BulkWriteBaseCommand>call(new BulkWriteBaseCommand(collection, operations)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient bulkWriteWithOptions(String collection, List<BulkOperation> operations, BulkWriteOptions bulkWriteOptions, Handler<AsyncResult<MongoClientBulkWriteResult>> resultHandler) {
    this.<MongoClientBulkWriteResult, BulkWriteWithOptionsCommand>call(new BulkWriteWithOptionsCommand(collection, operations, bulkWriteOptions)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient find(String collection, JsonObject query, Handler<AsyncResult<List<JsonObject>>> resultHandler) {
    this.<List<JsonObject>, FindBaseCommand>call(new FindBaseCommand(collection, query)).onComplete(resultHandler);
    return this;
  }

  @Override
  public ReadStream<JsonObject> findBatch(String collection, JsonObject query) {
    return callStream(new FindBatchBaseCommand(collection, query));
  }

  @Override
  public MongoClient findWithOptions(String collection, JsonObject query, FindOptions options, Handler<AsyncResult<List<JsonObject>>> resultHandler) {
    this.<List<JsonObject>, FindWithOptionsCommand>call(new FindWithOptionsCommand(collection, query, options)).onComplete(resultHandler);
    return this;
  }

  @Override
  public ReadStream<JsonObject> findBatchWithOptions(String collection, JsonObject query, FindOptions options) {
    return callStream(new FindBatchWithOptionsCommand(collection, query, options));
  }

  @Override
  public MongoClient findOne(String collection, JsonObject query, @Nullable JsonObject fields, Handler<AsyncResult<JsonObject>> resultHandler) {
    this.<JsonObject, FindOneCommand>call(new FindOneCommand(collection, query, fields)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient findOneAndUpdate(String collection, JsonObject query, JsonObject update, Handler<AsyncResult<JsonObject>> resultHandler) {
    this.<JsonObject, FindOneAndUpdateBaseCommand>call(new FindOneAndUpdateBaseCommand(collection, query, update)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient findOneAndUpdateWithOptions(String collection, JsonObject query, JsonObject update, FindOptions findOptions, UpdateOptions updateOptions, Handler<AsyncResult<JsonObject>> resultHandler) {
    this.<JsonObject, FindOneAndUpdateWithOptionsCommand>call(new FindOneAndUpdateWithOptionsCommand(collection, query, update, findOptions, updateOptions)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient findOneAndReplace(String collection, JsonObject query, JsonObject replace, Handler<AsyncResult<JsonObject>> resultHandler) {
    this.<JsonObject, FindOneAndReplaceBaseCommand>call(new FindOneAndReplaceBaseCommand(collection, query, replace)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient findOneAndReplaceWithOptions(String collection, JsonObject query, JsonObject replace, FindOptions findOptions, UpdateOptions updateOptions, Handler<AsyncResult<JsonObject>> resultHandler) {
    this.<JsonObject, FindOneAndReplaceWithOptionsCommand>call(new FindOneAndReplaceWithOptionsCommand(collection, query, replace, findOptions, updateOptions)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient findOneAndDelete(String collection, JsonObject query, Handler<AsyncResult<JsonObject>> resultHandler) {
    this.<JsonObject, FindOneAndDeleteBaseCommand>call(new FindOneAndDeleteBaseCommand(collection, query)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient findOneAndDeleteWithOptions(String collection, JsonObject query, FindOptions findOptions, Handler<AsyncResult<JsonObject>> resultHandler) {
    this.<JsonObject, FindOneAndDeleteWithOptionsCommand>call(new FindOneAndDeleteWithOptionsCommand(collection, query, findOptions)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient count(String collection, JsonObject query, Handler<AsyncResult<Long>> resultHandler) {
    this.<Long, CountCommand>call(new CountCommand(collection, query)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient remove(String collection, JsonObject query, Handler<AsyncResult<Void>> resultHandler) {
    throw new UnsupportedOperationException(DEPRECATED_MESSAGE);
  }

  @Override
  public MongoClient removeDocuments(String collection, JsonObject query, Handler<AsyncResult<MongoClientDeleteResult>> resultHandler) {
    this.<MongoClientDeleteResult, RemoveDocumentsBaseCommand>call(new RemoveDocumentsBaseCommand(collection, query)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient removeWithOptions(String collection, JsonObject query, WriteOption writeOption, Handler<AsyncResult<Void>> resultHandler) {
    throw new UnsupportedOperationException(DEPRECATED_MESSAGE);
  }

  @Override
  public MongoClient removeDocumentsWithOptions(String collection, JsonObject query, @Nullable WriteOption writeOption, Handler<AsyncResult<MongoClientDeleteResult>> resultHandler) {
    this.<MongoClientDeleteResult, RemoveDocumentsWithOptionsCommand>call(new RemoveDocumentsWithOptionsCommand(collection, query, writeOption)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient removeOne(String collection, JsonObject query, Handler<AsyncResult<Void>> resultHandler) {
    throw new UnsupportedOperationException(DEPRECATED_MESSAGE);
  }

  @Override
  public MongoClient removeDocument(String collection, JsonObject query, Handler<AsyncResult<MongoClientDeleteResult>> resultHandler) {
    this.<MongoClientDeleteResult, RemoveDocumentBaseCommand>call(new RemoveDocumentBaseCommand(collection, query)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient removeOneWithOptions(String collection, JsonObject query, WriteOption writeOption, Handler<AsyncResult<Void>> resultHandler) {
    throw new UnsupportedOperationException(DEPRECATED_MESSAGE);
  }

  @Override
  public MongoClient removeDocumentWithOptions(String collection, JsonObject query, @Nullable WriteOption writeOption, Handler<AsyncResult<MongoClientDeleteResult>> resultHandler) {
    this.<MongoClientDeleteResult, RemoveDocumentWithOptionsCommand>call(new RemoveDocumentWithOptionsCommand(collection, query, writeOption)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient createCollection(String collection, Handler<AsyncResult<Void>> resultHandler) {
    this.<Void, CreateCollectionCommand>call(new CreateCollectionCommand(collection)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient getCollections(Handler<AsyncResult<List<String>>> resultHandler) {
    this.<List<String>, GetCollectionsCommand>call(new GetCollectionsCommand()).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient dropCollection(String collection, Handler<AsyncResult<Void>> resultHandler) {
    this.<Void, DropCollectionCommand>call(new DropCollectionCommand(collection)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient createIndex(String collection, JsonObject key, Handler<AsyncResult<Void>> resultHandler) {
    this.<Void, CreateIndexBaseCommand>call(new CreateIndexBaseCommand(collection, key)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient createIndexWithOptions(String collection, JsonObject key, IndexOptions options, Handler<AsyncResult<Void>> resultHandler) {
    this.<Void, CreateIndexWithOptionsCommand>call(new CreateIndexWithOptionsCommand(collection, key, options)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient listIndexes(String collection, Handler<AsyncResult<JsonArray>> resultHandler) {
    this.<JsonArray, ListIndexesCommand>call(new ListIndexesCommand(collection)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient dropIndex(String collection, String indexName, Handler<AsyncResult<Void>> resultHandler) {
    this.<Void, DropIndexCommand>call(new DropIndexCommand(collection, indexName)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient runCommand(String commandName, JsonObject command, Handler<AsyncResult<JsonObject>> resultHandler) {
    this.<JsonObject, RunCommandCommand>call(new RunCommandCommand(commandName, command)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient distinct(String collection, String fieldName, String resultClassname, Handler<AsyncResult<JsonArray>> resultHandler) {
    this.<JsonArray, DistinctCommand>call(new DistinctCommand(collection, fieldName, resultClassname)).onComplete(resultHandler);
    return this;
  }

  @Override
  public MongoClient distinctWithQuery(String collection, String fieldName, String resultClassname, JsonObject query, Handler<AsyncResult<JsonArray>> resultHandler) {
    this.<JsonArray, DistinctWithQueryCommand>call(new DistinctWithQueryCommand(collection, fieldName, resultClassname, query)).onComplete(resultHandler);
    return this;
  }

  @Override
  public ReadStream<JsonObject> distinctBatch(String collection, String fieldName, String resultClassname) {
    return callStream(new DistinctBatchBaseCommand(collection, fieldName, resultClassname));
  }

  @Override
  public ReadStream<JsonObject> distinctBatchWithQuery(String collection, String fieldName, String resultClassname, JsonObject query) {
    return callStream(new DistinctBatchWithQueryCommand(collection, fieldName, resultClassname, query));
  }

  @Override
  public ReadStream<JsonObject> distinctBatchWithQuery(String collection, String fieldName, String resultClassname, JsonObject query, int batchSize) {
    return callStream(new DistinctBatchWithQueryCommand(collection, fieldName, resultClassname, query, batchSize));
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
