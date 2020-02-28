package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindOneAndReplace extends WithReplace<JsonObject> {

  public static class FindOneAndReplaceCommand extends WithReplaceCommand {

    public FindOneAndReplaceCommand(String collection, JsonObject query, JsonObject replace) {
      this("findOneAndReplace", collection, query, replace);
    }

    public FindOneAndReplaceCommand(String method, String collection, JsonObject query, JsonObject replace) {
      super(method, collection, query, replace);
    }
  }

  public FindOneAndReplace() {
    this("findOneAndReplace");
  }

  public FindOneAndReplace(String method) {
    super(method);
  }

  public FindOneAndReplace(JsonObject json) {
    super(json);
  }

  @Override
  protected JsonObject parseResponse(Object jsonValue) {
    return (JsonObject) jsonValue;
  }

  // fluent

  @Override
  public FindOneAndReplace inCollection(String collection) {
    return (FindOneAndReplace) super.inCollection(collection);
  }

  @Override
  public FindOneAndReplace inCollection(Matcher<String> collection) {
    return (FindOneAndReplace) super.inCollection(collection);
  }

  @Override
  public FindOneAndReplace withQuery(JsonObject query) {
    return (FindOneAndReplace) super.withQuery(query);
  }

  @Override
  public FindOneAndReplace withQuery(Matcher<JsonObject> query) {
    return (FindOneAndReplace) super.withQuery(query);
  }

  @Override
  public FindOneAndReplace withReplace(JsonObject replace) {
    return (FindOneAndReplace) super.withReplace(replace);
  }

  @Override
  public FindOneAndReplace withReplace(Matcher<JsonObject> replace) {
    return (FindOneAndReplace) super.withReplace(replace);
  }
}
