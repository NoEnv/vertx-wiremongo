package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.command.find.FindBatchBaseCommand;
import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindBatch extends FindBatchBase<FindBatchBaseCommand, FindBatch> {

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
