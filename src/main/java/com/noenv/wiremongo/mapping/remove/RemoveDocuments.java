package com.noenv.wiremongo.mapping.remove;

import com.noenv.wiremongo.command.remove.RemoveDocumentBaseCommand;
import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class RemoveDocuments extends RemoveDocumentBase<RemoveDocumentBaseCommand, RemoveDocuments> {

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
