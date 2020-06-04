package com.noenv.wiremongo.mapping;

import io.vertx.core.json.JsonObject;

public class DropCollection extends WithCollection<Void, DropCollection> {

  public static class DropCollectionCommand extends WithCollectionCommand {
    public DropCollectionCommand(String collection) {
      super("dropCollection", collection);
    }
  }

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
