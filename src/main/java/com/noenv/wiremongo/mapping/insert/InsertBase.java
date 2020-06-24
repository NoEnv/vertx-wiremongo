package com.noenv.wiremongo.mapping.insert;

import com.noenv.wiremongo.command.insert.InsertBaseCommand;
import com.noenv.wiremongo.mapping.WithDocument;
import io.vertx.core.json.JsonObject;

public abstract class InsertBase<U extends InsertBaseCommand, C extends InsertBase<U, C>> extends WithDocument<U, C> {

  public InsertBase(String method) {
    super(method);
  }

  public InsertBase(JsonObject json) {
    super(json);
  }
}
