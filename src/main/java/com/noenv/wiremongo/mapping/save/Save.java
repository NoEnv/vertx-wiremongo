package com.noenv.wiremongo.mapping.save;

import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class Save extends SaveBase<Save> {

  public Save() {
    super("save");
  }

  public Save(String method) {
    super(method);
  }

  public Save(JsonObject json) {
    super(json);
  }
}
