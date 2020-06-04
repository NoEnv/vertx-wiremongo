package com.noenv.wiremongo.mapping;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientUpdateResult;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class UpdateCollection extends WithUpdate<MongoClientUpdateResult, UpdateCollection> {

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
    return new MongoClientUpdateResult((JsonObject) jsonValue);
  }
}
