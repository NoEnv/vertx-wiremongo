package com.noenv.wiremongo.mapping;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientUpdateResult;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public abstract class UpdateCollectionBase<C extends UpdateCollectionBase<C>> extends WithUpdate<MongoClientUpdateResult, C> {

  public static class UpdateCollectionBaseCommand extends WithUpdateCommand {

    public UpdateCollectionBaseCommand(String collection, JsonObject query, JsonObject update) {
      this("updateCollection", collection, query, update);
    }

    public UpdateCollectionBaseCommand(String method, String collection, JsonObject query, JsonObject update) {
      super(method, collection, query, update);
    }
  }

  public UpdateCollectionBase() {
    this("updateCollection");
  }

  public UpdateCollectionBase(String method) {
    super(method);
  }

  public UpdateCollectionBase(JsonObject json) {
    super(json);
  }

  @Override
  protected MongoClientUpdateResult parseResponse(Object jsonValue) {
    return new MongoClientUpdateResult((JsonObject) jsonValue);
  }
}
