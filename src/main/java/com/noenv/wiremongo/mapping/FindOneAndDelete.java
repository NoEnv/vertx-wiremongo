package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindOneAndDelete extends WithQuery<JsonObject> {

  public static class FindOneAndDeleteCommand extends WithQueryCommand {

    public FindOneAndDeleteCommand(String collection, JsonObject query) {
      super("findOneAndDelete", collection, query);
    }

    public FindOneAndDeleteCommand(String method, String collection, JsonObject query) {
      super(method, collection, query);
    }
  }

  public FindOneAndDelete() {
    this("findOneAndDelete");
  }

  public FindOneAndDelete(String method) {
    super(method);
  }

  public FindOneAndDelete(JsonObject json) {
    super(json);
  }

  @Override
  protected JsonObject parseResponse(Object jsonValue) {
    return (JsonObject) jsonValue;
  }

  // fluent

  @Override
  public FindOneAndDelete priority(int priority) {
    return (FindOneAndDelete) super.priority(priority);
  }

  @Override
  public FindOneAndDelete inCollection(String collection) {
    return (FindOneAndDelete) super.inCollection(collection);
  }

  @Override
  public FindOneAndDelete inCollection(Matcher<String> collection) {
    return (FindOneAndDelete) super.inCollection(collection);
  }

  @Override
  public FindOneAndDelete withQuery(JsonObject query) {
    return (FindOneAndDelete) super.withQuery(query);
  }

  @Override
  public FindOneAndDelete withQuery(Matcher<JsonObject> query) {
    return (FindOneAndDelete) super.withQuery(query);
  }
}
