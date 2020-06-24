package com.noenv.wiremongo.mapping.save;

import com.noenv.wiremongo.mapping.WithDocument;
import io.vertx.core.json.JsonObject;

public abstract class SaveBase<C extends SaveBase<C>> extends WithDocument<C> {

  public static class SaveBaseCommand extends WithDocumentCommand {
    public SaveBaseCommand(String collection, JsonObject document) {
      super("save", collection, document);
    }

    public SaveBaseCommand(String method, String collection, JsonObject document) {
      super(method, collection, document);
    }
  }

  public SaveBase(String method) {
    super(method);
  }

  public SaveBase(JsonObject json) {
    super(json);
  }
}
