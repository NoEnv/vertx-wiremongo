package com.noenv.wiremongo.mapping;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientDeleteResult;

public abstract class RemoveDocumentBase<C extends RemoveDocumentBase<C>> extends WithQuery<MongoClientDeleteResult, C> {

  public static class RemoveDocumentBaseCommand extends WithQueryCommand {
    public RemoveDocumentBaseCommand(String collection, JsonObject query) {
      super("removeDocument", collection, query);
    }

    public RemoveDocumentBaseCommand(String method, String collection, JsonObject query) {
      super(method, collection, query);
    }
  }

  public RemoveDocumentBase() {
    super("removeDocument");
  }

  public RemoveDocumentBase(String method) {
    super(method);
  }

  public RemoveDocumentBase(JsonObject json) {
    super(json);
  }

  @Override
  protected MongoClientDeleteResult parseResponse(Object jsonValue) {
    return new MongoClientDeleteResult((JsonObject) jsonValue);
  }
}
