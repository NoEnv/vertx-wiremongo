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
import com.noenv.wiremongo.command.distinct.DistinctBatchBaseCommand;
import com.noenv.wiremongo.command.distinct.DistinctBatchWithQueryCommand;
import com.noenv.wiremongo.command.distinct.DistinctCommand;
import com.noenv.wiremongo.command.distinct.DistinctWithQueryCommand;
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
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.streams.ReadStream;
import io.vertx.ext.mongo.*;

import java.util.List;

@SuppressWarnings("squid:S6548") // singleton
public class WireMongoClient implements MongoClient {

  public static final String NOT_IMPLEMENTED = "not implemented";
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
  public Future<String> save(String collection, JsonObject document) {
    return call(new SaveBaseCommand(collection, document));
  }

  @Override
  public Future<String> saveWithOptions(String collection, JsonObject document, @Nullable WriteOption writeOption) {
    return call(new SaveWithOptionsCommand(collection, document, writeOption));
  }

  @Override
  public Future<String> insert(String collection, JsonObject document) {
    return call(new InsertBaseCommand(collection, document));
  }

  @Override
  public Future<String> insertWithOptions(String collection, JsonObject document, @Nullable WriteOption writeOption) {
    return call(new InsertWithOptionsCommand(collection, document, writeOption));
  }

  @Override
  public Future<MongoClientUpdateResult> updateCollection(String collection, JsonObject query, JsonObject update) {
    return call(new UpdateCollectionBaseCommand<>(collection, query, update));
  }

  @Override
  public Future<MongoClientUpdateResult> updateCollection(String collection, JsonObject query, JsonArray update) {
    return call(new UpdateCollectionBaseCommand<>("updateCollectionAggregationPipeline", collection, query, update));
  }

  @Override
  public Future<MongoClientUpdateResult> updateCollectionWithOptions(String collection, JsonObject query, JsonObject update, UpdateOptions options) {
    return call(new UpdateCollectionWithOptionsCommand<>(collection, query, update, options));
  }

  @Override
  public Future<MongoClientUpdateResult> updateCollectionWithOptions(String collection, JsonObject query, JsonArray update, UpdateOptions options) {
    return call(new UpdateCollectionWithOptionsCommand<>("updateCollectionWithOptionsAggregationPipeline", collection, query, update, options));
  }

  @Override
  public Future<MongoClientUpdateResult> replaceDocuments(String collection, JsonObject query, JsonObject replace) {
    return call(new ReplaceDocumentsBaseCommand(collection, query, replace));
  }

  @Override
  public Future<MongoClientUpdateResult> replaceDocumentsWithOptions(String collection, JsonObject query, JsonObject replace, UpdateOptions options) {
    return call(new ReplaceDocumentsWithOptionsCommand(collection, query, replace, options));
  }

  @Override
  public Future<MongoClientBulkWriteResult> bulkWrite(String collection, List<BulkOperation> operations) {
    return call(new BulkWriteBaseCommand(collection, operations));
  }

  @Override
  public Future<MongoClientBulkWriteResult> bulkWriteWithOptions(String collection, List<BulkOperation> operations, BulkWriteOptions bulkWriteOptions) {
    return call(new BulkWriteWithOptionsCommand(collection, operations, bulkWriteOptions));
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
  public Future<List<JsonObject>> findWithOptions(String collection, JsonObject query, FindOptions options) {
    return call(new FindWithOptionsCommand(collection, query, options));
  }

  @Override
  public ReadStream<JsonObject> findBatchWithOptions(String collection, JsonObject query, FindOptions options) {
    return callStream(new FindBatchWithOptionsCommand(collection, query, options));
  }

  @Override
  public Future<JsonObject> findOne(String collection, JsonObject query, @Nullable JsonObject fields) {
    return call(new FindOneCommand(collection, query, fields));
  }

  @Override
  public Future<JsonObject> findOneAndUpdate(String collection, JsonObject query, JsonObject update) {
    return call(new FindOneAndUpdateBaseCommand(collection, query, update));
  }

  @Override
  public Future<JsonObject> findOneAndUpdateWithOptions(String collection, JsonObject query, JsonObject update, FindOptions findOptions, UpdateOptions updateOptions) {
    return call(new FindOneAndUpdateWithOptionsCommand(collection, query, update, findOptions, updateOptions));
  }

  @Override
  public Future<JsonObject> findOneAndReplace(String collection, JsonObject query, JsonObject replace) {
    return call(new FindOneAndReplaceBaseCommand(collection, query, replace));
  }

  @Override
  public Future<JsonObject> findOneAndReplaceWithOptions(String collection, JsonObject query, JsonObject replace, FindOptions findOptions, UpdateOptions updateOptions) {
    return call(new FindOneAndReplaceWithOptionsCommand(collection, query, replace, findOptions, updateOptions));
  }

  @Override
  public Future<JsonObject> findOneAndDelete(String collection, JsonObject query) {
    return call(new FindOneAndDeleteBaseCommand(collection, query));
  }

  @Override
  public Future<JsonObject> findOneAndDeleteWithOptions(String collection, JsonObject query, FindOptions findOptions) {
    return call(new FindOneAndDeleteWithOptionsCommand(collection, query, findOptions));
  }

  @Override
  public Future<Long> count(String collection, JsonObject query) {
    return call(new CountCommand(collection, query));
  }

  @Override
  public Future<Long> countWithOptions(String collection, JsonObject query, CountOptions countOptions) {
    return call(new CountWithOptionsCommand(collection, query, countOptions));
  }

  @Override
  public Future<MongoClientDeleteResult> removeDocuments(String collection, JsonObject query) {
    return call(new RemoveDocumentsBaseCommand(collection, query));
  }

  @Override
  public Future<MongoClientDeleteResult> removeDocumentsWithOptions(String collection, JsonObject query, @Nullable WriteOption writeOption) {
    return call(new RemoveDocumentsWithOptionsCommand(collection, query, writeOption));
  }

  @Override
  public Future<MongoClientDeleteResult> removeDocument(String collection, JsonObject query) {
    return call(new RemoveDocumentBaseCommand(collection, query));
  }

  @Override
  public Future<MongoClientDeleteResult> removeDocumentWithOptions(String collection, JsonObject query, @Nullable WriteOption writeOption) {
    return call(new RemoveDocumentWithOptionsCommand(collection, query, writeOption));
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
  public Future<List<String>> getCollections() {
    return call(new GetCollectionsCommand());
  }

  @Override
  public Future<Void> dropCollection(String collection) {
    return call(new DropCollectionCommand(collection));
  }

  @Override
  public Future<Void> createIndex(String collection, JsonObject key) {
    return call(new CreateIndexBaseCommand(collection, key));
  }

  @Override
  public Future<Void> createIndexWithOptions(String collection, JsonObject key, IndexOptions options) {
    return call(new CreateIndexWithOptionsCommand(collection, key, options));
  }

  @Override
  public Future<Void> createIndexes(String collection, List<IndexModel> indexModels) {
    return call(new CreateIndexesCommand(collection, indexModels));
  }

  @Override
  public Future<JsonArray> listIndexes(String collection) {
    return call(new ListIndexesCommand(collection));
  }

  @Override
  public Future<Void> dropIndex(String collection, String indexName) {
    return call(new DropIndexCommand(collection, indexName));
  }

  @Override
  public Future<Void> dropIndex(String collection, JsonObject key) {
    throw new UnsupportedOperationException("not implemented");
  }

  @Override
  public Future<JsonObject> runCommand(String commandName, JsonObject command) {
    return call(new RunCommandCommand(commandName, command));
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
  public Future<MongoGridFsClient> createDefaultGridFsBucketService() {
    throw new UnsupportedOperationException(NOT_IMPLEMENTED);
  }

  @Override
  public Future<MongoGridFsClient> createGridFsBucketService(String s) {
    throw new UnsupportedOperationException(NOT_IMPLEMENTED);
  }

  @Override
  public ReadStream<ChangeStreamDocument<JsonObject>> watch(String s, JsonArray jsonArray, boolean b, int i) {
    throw new UnsupportedOperationException(NOT_IMPLEMENTED);
  }

  @Override
  public Future<Void> close() {
    return Future.succeededFuture();
  }

  @SuppressWarnings("squid:S1172") // unused parameters
  public static synchronized WireMongoClient createShared(Vertx vertx, JsonObject config, String dataSource) {
    if (instance == null) {
      instance = new WireMongoClient();
    }
    return instance;
  }
}
