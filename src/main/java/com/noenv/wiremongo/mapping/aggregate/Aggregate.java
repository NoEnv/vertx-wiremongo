package com.noenv.wiremongo.mapping.aggregate;

import com.noenv.wiremongo.command.aggregate.AggregateBaseCommand;
import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class Aggregate extends AggregateBase<AggregateBaseCommand, Aggregate> {

  public Aggregate() {
    this("aggregate");
  }

  public Aggregate(String method) {
    super(method);
  }

  public Aggregate(JsonObject json) {
    super(json);
  }
}
