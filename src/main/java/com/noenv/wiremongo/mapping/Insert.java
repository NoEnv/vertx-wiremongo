package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

public class Insert extends WithDocument {

  public static class InsertCommand extends WithDocumentCommand {
    public InsertCommand(String collection, JsonObject document) {
      super("insert", collection, document);
    }

    public InsertCommand(String method, String collection, JsonObject document) {
      super(method, collection, document);
    }
  }

  public Insert() {
    super("insert");
  }

  public Insert(String method) {
    super(method);
  }

  public Insert(JsonObject json) {
    super(json);
  }

  // fluent

  @Override
  public Insert inCollection(String collection) {
    return (Insert) super.inCollection(collection);
  }

  @Override
  public Insert inCollection(Matcher<String> collection) {
    return (Insert) super.inCollection(collection);
  }

  @Override
  public Insert withDocument(JsonObject document) {
    return (Insert) super.withDocument(document);
  }

  @Override
  public Insert withDocument(Matcher<JsonObject> document) {
    return (Insert) super.withDocument(document);
  }

}
