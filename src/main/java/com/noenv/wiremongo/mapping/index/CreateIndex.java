package com.noenv.wiremongo.mapping.index;

import com.noenv.wiremongo.command.index.CreateIndexBaseCommand;
import io.vertx.core.json.JsonObject;

public class CreateIndex extends CreateIndexBase<CreateIndexBaseCommand, CreateIndex> {

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
