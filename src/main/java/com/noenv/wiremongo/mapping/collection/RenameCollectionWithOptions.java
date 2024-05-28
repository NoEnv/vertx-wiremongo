package com.noenv.wiremongo.mapping.collection;

import com.noenv.wiremongo.command.collection.RenameCollectionWithOptionsCommand;
import io.vertx.core.json.JsonObject;

public class RenameCollectionWithOptions extends WithCollection<Void, RenameCollectionWithOptionsCommand, RenameCollectionWithOptions> {

  public RenameCollectionWithOptions() {
    super("renameCollectionWithOptions");
  }

  public RenameCollectionWithOptions(JsonObject json) {
    super(json);
  }

  @Override
  protected Void parseResponse(Object jsonValue) {
    return null;
  }
}
