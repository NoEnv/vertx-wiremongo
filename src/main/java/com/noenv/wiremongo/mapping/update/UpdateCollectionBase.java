package com.noenv.wiremongo.mapping.update;

import com.noenv.wiremongo.command.update.UpdateCollectionBaseCommand;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientUpdateResult;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public abstract class UpdateCollectionBase<U extends UpdateCollectionBaseCommand, C extends UpdateCollectionBase<U, C>> extends WithUpdate<MongoClientUpdateResult, U, C> {

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
