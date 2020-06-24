package com.noenv.wiremongo.mapping.find;

import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindOneAndDelete extends FindOneAndDeleteBase<FindOneAndDelete> {

  public FindOneAndDelete() {
    this("findOneAndDelete");
  }

  public FindOneAndDelete(String method) {
    super(method);
  }

  public FindOneAndDelete(JsonObject json) {
    super(json);
  }
}
