package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.mapping.stream.WithStreamQuery;
import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindBatchBase<C extends FindBatchBase<C>> extends WithStreamQuery<C> {

  public static class FindBatchBaseCommand extends WithStreamQueryCommand {
    public FindBatchBaseCommand(String collection, JsonObject query) {
      this("findBatch", collection, query);
    }

    public FindBatchBaseCommand(String method, String collection, JsonObject query) {
      super(method, collection, query);
    }
  }

  public FindBatchBase(String method) {
    super(method);
  }

  public FindBatchBase(JsonObject json) {
    super(json);
  }
}
