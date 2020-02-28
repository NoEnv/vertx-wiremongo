package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientDeleteResult;

public class RemoveDocument extends WithQuery<MongoClientDeleteResult> {

  public static class RemoveDocumentCommand extends WithQueryCommand {
    public RemoveDocumentCommand(String collection, JsonObject query) {
      super("removeDocument", collection, query);
    }

    public RemoveDocumentCommand(String method, String collection, JsonObject query) {
      super(method, collection, query);
    }
  }

  public RemoveDocument() {
    super("removeDocument");
  }

  public RemoveDocument(String method) {
    super(method);
  }

  public RemoveDocument(JsonObject json) {
    super(json);
  }

  @Override
  protected MongoClientDeleteResult parseResponse(Object jsonValue) {
    return new MongoClientDeleteResult((JsonObject) jsonValue);
  }

  // fluent

  @Override
  public RemoveDocument inCollection(String collection) {
    return (RemoveDocument) super.inCollection(collection);
  }

  @Override
  public RemoveDocument inCollection(Matcher<String> collection) {
    return (RemoveDocument) super.inCollection(collection);
  }

  @Override
  public RemoveDocument withQuery(JsonObject query) {
    return (RemoveDocument) super.withQuery(query);
  }

  @Override
  public RemoveDocument withQuery(Matcher<JsonObject> query) {
    return (RemoveDocument) super.withQuery(query);
  }
}
