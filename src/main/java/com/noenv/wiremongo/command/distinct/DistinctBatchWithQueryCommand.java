package com.noenv.wiremongo.command.distinct;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.DistinctOptions;

import static io.vertx.ext.mongo.FindOptions.DEFAULT_BATCH_SIZE;

public class DistinctBatchWithQueryCommand extends DistinctBatchBaseCommand {

  private final JsonObject query;
  private final int batchSize;
  private final DistinctOptions options;

  public DistinctBatchWithQueryCommand(String collection, String fieldName, String resultClassname, JsonObject query) {
    this(collection, fieldName, resultClassname, query, DEFAULT_BATCH_SIZE);
  }

  public DistinctBatchWithQueryCommand(String collection, String fieldName, String resultClassname, JsonObject query, int batchSize) {
    this(collection, fieldName, resultClassname, query, batchSize, null);
  }

  public DistinctBatchWithQueryCommand(String collection, String fieldName, String resultClassname, JsonObject query, DistinctOptions options) {
    this(collection, fieldName, resultClassname, query, DEFAULT_BATCH_SIZE, options);
  }

  public DistinctBatchWithQueryCommand(String collection, String fieldName, String resultClassname, JsonObject query, int batchSize, DistinctOptions options) {
    super(collection, "distinctBatchWithQuery", fieldName, resultClassname, options);
    this.query = query;
    this.batchSize = batchSize;
    this.options = options;
  }

  public JsonObject getQuery() {
    return query;
  }

  public int getBatchSize() {
    return batchSize;
  }

  @Override
  public String toString() {
    return super.toString() + ", query: " + (query != null ? query.encode() : "null") + ", option: " + (options != null ? options.toJson().encode() : "null");
  }
}
