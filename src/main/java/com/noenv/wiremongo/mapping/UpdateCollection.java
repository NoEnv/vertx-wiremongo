package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientUpdateResult;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class UpdateCollection extends WithUpdate<MongoClientUpdateResult> {

  public static class UpdateCollectionCommand extends WithUpdateCommand {

    public UpdateCollectionCommand(String collection, JsonObject query, JsonObject update) {
      this("updateCollection", collection, query, update);
    }

    public UpdateCollectionCommand(String method, String collection, JsonObject query, JsonObject update) {
      super(method, collection, query, update);
    }
  }

  public UpdateCollection() {
    this("updateCollection");
  }

  public UpdateCollection(String method) {
    super(method);
  }

  public UpdateCollection(JsonObject json) {
    super(json);
  }

  @Override
  protected MongoClientUpdateResult parseResponse(Object jsonValue) {
    return new MongoClientUpdateResult((JsonObject)jsonValue);
  }

  // fluent

  @Override
  public UpdateCollection inCollection(String collection) {
    return (UpdateCollection) super.inCollection(collection);
  }

  @Override
  public UpdateCollection inCollection(Matcher<String> collection) {
    return (UpdateCollection) super.inCollection(collection);
  }

  @Override
  public UpdateCollection withQuery(JsonObject query) {
    return (UpdateCollection) super.withQuery(query);
  }

  @Override
  public UpdateCollection withQuery(Matcher<JsonObject> query) {
    return (UpdateCollection) super.withQuery(query);
  }

  @Override
  public UpdateCollection withUpdate(JsonObject update) {
    return (UpdateCollection) super.withUpdate(update);
  }

  @Override
  public UpdateCollection withUpdate(Matcher<JsonObject> update) {
    return (UpdateCollection) super.withUpdate(update);
  }
}
