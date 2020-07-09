package com.noenv.wiremongo.mapping.collection;

import com.noenv.wiremongo.command.collection.CreateCollectionCommand;
import io.vertx.core.json.JsonObject;

public class CreateCollection extends WithCollection<Void, CreateCollectionCommand, CreateCollection> {

  public CreateCollection() {
    super("createCollection");
  }

  public CreateCollection(JsonObject json) {
    super(json);
  }

  @Override
  protected Void parseResponse(Object jsonValue) {
    return null;
  }
}
