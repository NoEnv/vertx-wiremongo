package com.noenv.wiremongo.mapping.replace;

import com.noenv.wiremongo.command.replace.ReplaceDocumentsBaseCommand;
import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class ReplaceDocuments extends ReplaceDocumentsBase<ReplaceDocumentsBaseCommand, ReplaceDocuments> {

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
