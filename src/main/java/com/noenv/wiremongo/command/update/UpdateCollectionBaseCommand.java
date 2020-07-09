package com.noenv.wiremongo.command.update;

import io.vertx.core.json.JsonObject;

public class UpdateCollectionBaseCommand extends WithUpdateCommand {

  public UpdateCollectionBaseCommand(String collection, JsonObject query, JsonObject update) {
    this("updateCollection", collection, query, update);
  }

  public UpdateCollectionBaseCommand(String method, String collection, JsonObject query, JsonObject update) {
    super(method, collection, query, update);
  }
}
