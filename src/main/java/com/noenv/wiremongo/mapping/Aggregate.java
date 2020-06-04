package com.noenv.wiremongo.mapping;

import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class Aggregate extends AggregateBase<Aggregate> {

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
