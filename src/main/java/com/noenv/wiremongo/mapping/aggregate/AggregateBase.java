package com.noenv.wiremongo.mapping.aggregate;

import com.noenv.wiremongo.mapping.stream.WithStreamPipeline;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public abstract class AggregateBase<C extends AggregateBase<C>> extends WithStreamPipeline<C> {

  public static class AggregateBaseCommand extends WithStreamPipelineCommand {
    public AggregateBaseCommand(String collection, JsonArray pipeline) {
      this("aggregate", collection, pipeline);
    }

    public AggregateBaseCommand(String method, String collection, JsonArray pipeline) {
      super(method, collection, pipeline);
    }
  }

  public AggregateBase(String method) {
    super(method);
  }

  public AggregateBase(JsonObject json) {
    super(json);
  }
}
