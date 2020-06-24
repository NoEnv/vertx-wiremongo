package com.noenv.wiremongo.mapping.collection;

import com.noenv.wiremongo.command.collection.DropCollectionCommand;
import io.vertx.core.json.JsonObject;

public class DropCollection extends WithCollection<Void, DropCollectionCommand, DropCollection> {

  public DropCollection() {
    super("dropCollection");
  }

  public DropCollection(JsonObject json) {
    super(json);
  }

  @Override
  protected Void parseResponse(Object jsonValue) {
    return null;
  }
}
