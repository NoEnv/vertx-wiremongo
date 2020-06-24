package com.noenv.wiremongo.mapping.aggregate;

import com.noenv.wiremongo.command.aggregate.AggregateBaseCommand;
import com.noenv.wiremongo.mapping.stream.WithStreamPipeline;
import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public abstract class AggregateBase<U extends AggregateBaseCommand, C extends AggregateBase<U, C>> extends WithStreamPipeline<U, C> {

  public AggregateBase(String method) {
    super(method);
  }

  public AggregateBase(JsonObject json) {
    super(json);
  }
}
