package com.noenv.wiremongo.mapping;

import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindOneAndUpdate extends FindOneAndUpdateBase<FindOneAndUpdate> {

  public FindOneAndUpdate() {
    this("findOneAndUpdate");
  }

  public FindOneAndUpdate(String method) {
    super(method);
  }

  public FindOneAndUpdate(JsonObject json) {
    super(json);
  }
}
