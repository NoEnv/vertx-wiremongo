package com.noenv.wiremongo.mapping;

import io.vertx.core.json.JsonObject;

public class CreateIndex extends CreateIndexBase<CreateIndex> {

  public CreateIndex() {
    this("createIndex");
  }

  public CreateIndex(String method) {
    super(method);
  }

  public CreateIndex(JsonObject json) {
    super(json);
  }
}
