package com.noenv.wiremongo.command;

import com.noenv.wiremongo.command.collection.WithCollectionCommand;
import io.vertx.core.json.JsonObject;

public abstract class WithDocumentCommand extends WithCollectionCommand {

  private final JsonObject document;

  public WithDocumentCommand(String method, String collection, JsonObject document) {
    super(method, collection);
    this.document = document;
  }

  public JsonObject getDocument() {
    return document;
  }

  @Override
  public String toString() {
    return super.toString() + ", document: " + document;
  }
}
