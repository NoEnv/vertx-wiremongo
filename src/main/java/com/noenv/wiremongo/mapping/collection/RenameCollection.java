package com.noenv.wiremongo.mapping.collection;

import com.noenv.wiremongo.command.collection.RenameCollectionCommand;
import io.vertx.core.json.JsonObject;

public class RenameCollection extends WithCollection<Void, RenameCollectionCommand, RenameCollection> {

  public RenameCollection() {
    super("renameCollection");
  }

  public RenameCollection(JsonObject json) {
    super(json);
  }

  @Override
  protected Void parseResponse(Object jsonValue) {
    return null;
  }
}
