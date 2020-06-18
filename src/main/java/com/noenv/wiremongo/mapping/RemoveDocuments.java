package com.noenv.wiremongo.mapping;

import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class RemoveDocuments extends RemoveDocumentBase<RemoveDocuments> {

  public RemoveDocuments() {
    super("removeDocuments");
  }

  public RemoveDocuments(String method) {
    super(method);
  }

  public RemoveDocuments(JsonObject json) {
    super(json);
  }
}
