package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientUpdateResult;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class ReplaceDocuments extends WithReplace<MongoClientUpdateResult> {

  public static class ReplaceDocumentsCommand extends WithReplaceCommand {

    public ReplaceDocumentsCommand(String collection, JsonObject query, JsonObject replace) {
      this("replaceDocuments", collection, query, replace);
    }

    public ReplaceDocumentsCommand(String method, String collection, JsonObject query, JsonObject replace) {
      super(method, collection, query, replace);
    }
  }

  public ReplaceDocuments() {
    this("replaceDocuments");
  }

  public ReplaceDocuments(String method) {
    super(method);
  }

  public ReplaceDocuments(JsonObject json) {
    super(json);
  }

  @Override
  protected MongoClientUpdateResult parseResponse(Object jsonValue) {
    return new MongoClientUpdateResult((JsonObject) jsonValue);
  }

  // fluent

  @Override
  public ReplaceDocuments priority(int priority) {
    return (ReplaceDocuments) super.priority(priority);
  }

  @Override
  public ReplaceDocuments inCollection(String collection) {
    return (ReplaceDocuments) super.inCollection(collection);
  }

  @Override
  public ReplaceDocuments inCollection(Matcher<String> collection) {
    return (ReplaceDocuments) super.inCollection(collection);
  }

  @Override
  public ReplaceDocuments withQuery(JsonObject query) {
    return (ReplaceDocuments) super.withQuery(query);
  }

  @Override
  public ReplaceDocuments withQuery(Matcher<JsonObject> query) {
    return (ReplaceDocuments) super.withQuery(query);
  }

  @Override
  public ReplaceDocuments withReplace(JsonObject replace) {
    return (ReplaceDocuments) super.withReplace(replace);
  }

  @Override
  public ReplaceDocuments withReplace(Matcher<JsonObject> replace) {
    return (ReplaceDocuments) super.withReplace(replace);
  }
}
