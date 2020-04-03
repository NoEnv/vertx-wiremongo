package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindOneAndUpdate extends WithUpdate<JsonObject> {

  public static class FindOneAndUpdateCommand extends WithUpdateCommand {

    public FindOneAndUpdateCommand(String collection, JsonObject query, JsonObject update) {
      this("findOneAndUpdate", collection, query, update);
    }

    public FindOneAndUpdateCommand(String method, String collection, JsonObject query, JsonObject update) {
      super(method, collection, query, update);
    }
  }

  public FindOneAndUpdate() {
    this("findOneAndUpdate");
  }

  public FindOneAndUpdate(String method) {
    super(method);
  }

  public FindOneAndUpdate(JsonObject json) {
    super(json);
  }

  @Override
  protected JsonObject parseResponse(Object jsonValue) {
    return (JsonObject) jsonValue;
  }

  // fluent

  @Override
  public FindOneAndUpdate priority(int priority) {
    return (FindOneAndUpdate) super.priority(priority);
  }

  @Override
  public FindOneAndUpdate inCollection(String collection) {
    return (FindOneAndUpdate) super.inCollection(collection);
  }

  @Override
  public FindOneAndUpdate inCollection(Matcher<String> collection) {
    return (FindOneAndUpdate) super.inCollection(collection);
  }

  @Override
  public FindOneAndUpdate withQuery(JsonObject query) {
    return (FindOneAndUpdate) super.withQuery(query);
  }

  @Override
  public FindOneAndUpdate withQuery(Matcher<JsonObject> query) {
    return (FindOneAndUpdate) super.withQuery(query);
  }

  @Override
  public FindOneAndUpdate withUpdate(JsonObject update) {
    return (FindOneAndUpdate) super.withUpdate(update);
  }

  @Override
  public FindOneAndUpdate withUpdate(Matcher<JsonObject> update) {
    return (FindOneAndUpdate) super.withUpdate(update);
  }
}
