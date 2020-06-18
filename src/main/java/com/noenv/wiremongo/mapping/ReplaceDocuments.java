package com.noenv.wiremongo.mapping;

import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class ReplaceDocuments extends ReplaceDocumentsBase<ReplaceDocuments> {

  public ReplaceDocuments() {
    this("replaceDocuments");
  }

  public ReplaceDocuments(String method) {
    super(method);
  }

  public ReplaceDocuments(JsonObject json) {
    super(json);
  }
}
