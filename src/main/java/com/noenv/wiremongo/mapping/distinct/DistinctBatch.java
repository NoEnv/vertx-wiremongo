package com.noenv.wiremongo.mapping.distinct;

import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class DistinctBatch extends DistinctBatchBase<DistinctBatch> {

  public DistinctBatch() {
    super("distinctBatch");
  }

  public DistinctBatch(String method) {
    super(method);
  }

  public DistinctBatch(JsonObject json) {
    super(json);
  }
}
