package com.noenv.wiremongo.mapping;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientDeleteResult;

public abstract class RemoveDocumentsBase<C extends RemoveDocumentsBase<C>> extends WithQuery<MongoClientDeleteResult, C> {

  public static class RemoveDocumentsBaseCommand extends WithQueryCommand {
    public RemoveDocumentsBaseCommand(String collection, JsonObject query) {
      super("removeDocuments", collection, query);
    }

    public RemoveDocumentsBaseCommand(String method, String collection, JsonObject query) {
      super(method, collection, query);
    }
  }

  public RemoveDocumentsBase() {
    super("removeDocuments");
  }

  public RemoveDocumentsBase(String method) {
    super(method);
  }

  public RemoveDocumentsBase(JsonObject json) {
    super(json);
  }

  @Override
  protected MongoClientDeleteResult parseResponse(Object jsonValue) {
    return new MongoClientDeleteResult((JsonObject) jsonValue);
  }
}
