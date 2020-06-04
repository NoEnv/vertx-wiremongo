package com.noenv.wiremongo.mapping;

import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class Insert extends InsertBase<Insert> {

  public Insert() {
    super("insert");
  }

  public Insert(String method) {
    super(method);
  }

  public Insert(JsonObject json) {
    super(json);
  }
}
