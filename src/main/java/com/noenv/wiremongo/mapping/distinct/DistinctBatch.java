package com.noenv.wiremongo.mapping.distinct;

import com.noenv.wiremongo.command.distinct.DistinctBatchBaseCommand;
import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class DistinctBatch extends DistinctBatchBase<DistinctBatchBaseCommand, DistinctBatch> {

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
