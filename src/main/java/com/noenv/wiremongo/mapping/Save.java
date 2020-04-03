package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

public class Save extends WithDocument {

  public static class SaveCommand extends WithDocumentCommand {
    public SaveCommand(String collection, JsonObject document) {
      super("save", collection, document);
    }

    public SaveCommand(String method, String collection, JsonObject document) {
      super(method, collection, document);
    }
  }

  public Save() {
    super("save");
  }

  public Save(String method) {
    super(method);
  }

  public Save(JsonObject json) {
    super(json);
  }

  // fluent

  @Override
  public Save priority(int priority) {
    return (Save) super.priority(priority);
  }

  @Override
  public Save inCollection(String collection) {
    return (Save) super.inCollection(collection);
  }

  @Override
  public Save inCollection(Matcher<String> collection) {
    return (Save) super.inCollection(collection);
  }

  @Override
  public Save withDocument(JsonObject document) {
    return (Save) super.withDocument(document);
  }

  @Override
  public Save withDocument(Matcher<JsonObject> document) {
    return (Save) super.withDocument(document);
  }

}
