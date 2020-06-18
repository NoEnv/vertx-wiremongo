package com.noenv.wiremongo.mapping;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientUpdateResult;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public abstract class ReplaceDocumentsBase<C extends ReplaceDocumentsBase<C>> extends WithReplace<MongoClientUpdateResult, C> {

  public static class ReplaceDocumentsBaseCommand extends WithReplaceCommand {

    public ReplaceDocumentsBaseCommand(String collection, JsonObject query, JsonObject replace) {
      this("replaceDocuments", collection, query, replace);
    }

    public ReplaceDocumentsBaseCommand(String method, String collection, JsonObject query, JsonObject replace) {
      super(method, collection, query, replace);
    }
  }

  public ReplaceDocumentsBase() {
    this("replaceDocuments");
  }

  public ReplaceDocumentsBase(String method) {
    super(method);
  }

  public ReplaceDocumentsBase(JsonObject json) {
    super(json);
  }

  @Override
  protected MongoClientUpdateResult parseResponse(Object jsonValue) {
    return new MongoClientUpdateResult((JsonObject) jsonValue);
  }
}
