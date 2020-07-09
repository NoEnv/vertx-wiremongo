package com.noenv.wiremongo.command.replace;

import io.vertx.core.json.JsonObject;

public class ReplaceDocumentsBaseCommand extends WithReplaceCommand {

  public ReplaceDocumentsBaseCommand(String collection, JsonObject query, JsonObject replace) {
    this("replaceDocuments", collection, query, replace);
  }

  public ReplaceDocumentsBaseCommand(String method, String collection, JsonObject query, JsonObject replace) {
    super(method, collection, query, replace);
  }
}
