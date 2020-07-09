package com.noenv.wiremongo.mapping.remove;

import com.noenv.wiremongo.command.remove.RemoveDocumentBaseCommand;
import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class RemoveDocument extends RemoveDocumentBase<RemoveDocumentBaseCommand, RemoveDocument> {

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
