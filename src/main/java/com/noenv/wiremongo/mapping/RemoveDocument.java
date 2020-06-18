package com.noenv.wiremongo.mapping;

import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class RemoveDocument extends RemoveDocumentBase<RemoveDocument> {

  public RemoveDocument() {
    super("removeDocument");
  }

  public RemoveDocument(String method) {
    super(method);
  }

  public RemoveDocument(JsonObject json) {
    super(json);
  }
}
