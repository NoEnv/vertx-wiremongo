package com.noenv.wiremongo.mapping;

import io.vertx.core.json.JsonObject;

public class CreateCollection extends WithCollection<Void, CreateCollection> {

  public static class CreateCollectionCommand extends WithCollectionCommand {
    public CreateCollectionCommand(String collection) {
      super("createCollection", collection);
    }
  }

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
