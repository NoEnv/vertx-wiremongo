package com.noenv.wiremongo.mapping;

import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindBatch extends FindBatchBase<FindBatch> {

  public FindBatch() {
    this("findBatch");
  }

  public FindBatch(String method) {
    super(method);
  }

  public FindBatch(JsonObject json) {
    super(json);
  }
}
