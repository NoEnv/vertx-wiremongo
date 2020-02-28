package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientDeleteResult;

public class RemoveDocuments extends WithQuery<MongoClientDeleteResult> {

  public static class RemoveDocumentsCommand extends WithQueryCommand {
    public RemoveDocumentsCommand(String collection, JsonObject query) {
      super("removeDocuments", collection, query);
    }

    public RemoveDocumentsCommand(String method, String collection, JsonObject query) {
      super(method, collection, query);
    }
  }

  public RemoveDocuments() {
    super("removeDocuments");
  }

  public RemoveDocuments(String method) {
    super(method);
  }

  public RemoveDocuments(JsonObject json) {
    super(json);
  }

  @Override
  protected MongoClientDeleteResult parseResponse(Object jsonValue) {
    return new MongoClientDeleteResult((JsonObject) jsonValue);
  }

  // fluent

  @Override
  public RemoveDocuments inCollection(String collection) {
    return (RemoveDocuments) super.inCollection(collection);
  }

  @Override
  public RemoveDocuments inCollection(Matcher<String> collection) {
    return (RemoveDocuments) super.inCollection(collection);
  }

  @Override
  public RemoveDocuments withQuery(JsonObject query) {
    return (RemoveDocuments) super.withQuery(query);
  }

  @Override
  public RemoveDocuments withQuery(Matcher<JsonObject> query) {
    return (RemoveDocuments) super.withQuery(query);
  }
}
