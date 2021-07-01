package com.noenv.wiremongo.command.update;

import io.vertx.core.json.JsonObject;

public class UpdateCollectionBaseCommand<T> extends WithUpdateCommand<T> {

  public UpdateCollectionBaseCommand(String collection, JsonObject query, T update) {
    this("updateCollection", collection, query, update);
  }

  public UpdateCollectionBaseCommand(String method, String collection, JsonObject query, T update) {
    super(method, collection, query, update);
  }
}
