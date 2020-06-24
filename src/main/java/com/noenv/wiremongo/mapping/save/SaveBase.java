package com.noenv.wiremongo.mapping.save;

import com.noenv.wiremongo.command.save.SaveBaseCommand;
import com.noenv.wiremongo.mapping.WithDocument;
import io.vertx.core.json.JsonObject;

public abstract class SaveBase<U extends SaveBaseCommand, C extends SaveBase<U, C>> extends WithDocument<U, C> {

  public SaveBase(String method) {
    super(method);
  }

  public SaveBase(JsonObject json) {
    super(json);
  }
}
