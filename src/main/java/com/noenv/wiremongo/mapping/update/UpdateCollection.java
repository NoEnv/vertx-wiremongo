package com.noenv.wiremongo.mapping.update;

import com.noenv.wiremongo.command.update.UpdateCollectionBaseCommand;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientUpdateResult;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class UpdateCollection extends UpdateCollectionBase<UpdateCollectionBaseCommand, UpdateCollection> {

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
