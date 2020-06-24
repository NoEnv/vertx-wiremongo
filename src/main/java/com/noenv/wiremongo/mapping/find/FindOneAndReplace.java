package com.noenv.wiremongo.mapping.find;

import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindOneAndReplace extends FindOneAndReplaceBase<FindOneAndReplace> {

  public FindOneAndReplace() {
    this("findOneAndReplace");
  }

  public FindOneAndReplace(String method) {
    super(method);
  }

  public FindOneAndReplace(JsonObject json) {
    super(json);
  }
}
