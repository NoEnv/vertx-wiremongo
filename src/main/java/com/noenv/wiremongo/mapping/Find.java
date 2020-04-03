package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.stream.Collectors;

public class Find extends WithQuery<List<JsonObject>> {

  public static class FindCommand extends WithQueryCommand {
    public FindCommand(String collection, JsonObject query) {
      this("find", collection, query);
    }

    public FindCommand(String method, String collection, JsonObject query) {
      super(method, collection, query);
    }
  }

  public Find() {
    this("find");
  }

  public Find(String method) {
    super(method);
  }

  public Find(JsonObject json) {
    super(json);
  }

  @Override
  protected List<JsonObject> parseResponse(Object jsonValue) {
    return ((JsonArray) jsonValue).stream()
      .map(o -> (JsonObject) o)
      .collect(Collectors.toList());
  }

  // fluent

  @Override
  public Find priority(int priority) {
    return (Find) super.priority(priority);
  }

  @Override
  public Find inCollection(String collection) {
    return (Find) super.inCollection(collection);
  }

  @Override
  public Find inCollection(Matcher<String> collection) {
    return (Find) super.inCollection(collection);
  }

  @Override
  public Find withQuery(JsonObject query) {
    return (Find) super.withQuery(query);
  }

  @Override
  public Find withQuery(Matcher<JsonObject> query) {
    return (Find) super.withQuery(query);
  }
}
