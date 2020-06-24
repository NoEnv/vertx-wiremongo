package com.noenv.wiremongo.command.distinct;

import io.vertx.core.json.JsonObject;

public class DistinctBatchWithQueryCommand extends DistinctBatchBaseCommand {

  private final JsonObject query;
  private final int batchSize;

  public DistinctBatchWithQueryCommand(String collection, String fieldName, String resultClassname, JsonObject query) {
    this(collection, fieldName, resultClassname, query, 20);
  }

  public DistinctBatchWithQueryCommand(String collection, String fieldName, String resultClassname, JsonObject query, int batchSize) {
    super(collection, "distinctBatchWithQuery", fieldName, resultClassname);
    this.query = query;
    this.batchSize = batchSize;
  }

  public JsonObject getQuery() {
    return query;
  }

  public int getBatchSize() {
    return batchSize;
  }

  @Override
  public String toString() {
    return super.toString() + ", query: " + (query != null ? query.encode() : "null");
  }
}
