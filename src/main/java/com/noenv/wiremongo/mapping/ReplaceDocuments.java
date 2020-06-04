package com.noenv.wiremongo.mapping;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientUpdateResult;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class ReplaceDocuments extends WithReplace<MongoClientUpdateResult, ReplaceDocuments> {

  public static class ReplaceDocumentsCommand extends WithReplaceCommand {

    public ReplaceDocumentsCommand(String collection, JsonObject query, JsonObject replace) {
      this("replaceDocuments", collection, query, replace);
    }

    public ReplaceDocumentsCommand(String method, String collection, JsonObject query, JsonObject replace) {
      super(method, collection, query, replace);
    }
  }

  public ReplaceDocuments() {
    this("replaceDocuments");
  }

  public ReplaceDocuments(String method) {
    super(method);
  }

  public ReplaceDocuments(JsonObject json) {
    super(json);
  }

  @Override
  protected MongoClientUpdateResult parseResponse(Object jsonValue) {
    return new MongoClientUpdateResult((JsonObject) jsonValue);
  }
}
