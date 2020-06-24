package com.noenv.wiremongo.mapping.insert;

import com.noenv.wiremongo.mapping.WithDocument;
import io.vertx.core.json.JsonObject;

public abstract class InsertBase<C extends InsertBase<C>> extends WithDocument<C> {

  public static class InsertBaseCommand extends WithDocumentCommand {
    public InsertBaseCommand(String collection, JsonObject document) {
      super("insert", collection, document);
    }

    public InsertBaseCommand(String method, String collection, JsonObject document) {
      super(method, collection, document);
    }
  }

  public InsertBase(String method) {
    super(method);
  }

  public InsertBase(JsonObject json) {
    super(json);
  }
}
