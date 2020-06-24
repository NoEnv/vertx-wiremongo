package com.noenv.wiremongo.command.index;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.IndexOptions;

public class CreateIndexWithOptionsCommand extends CreateIndexBaseCommand {

  private final IndexOptions options;

  public CreateIndexWithOptionsCommand(String collection, JsonObject key, IndexOptions options) {
    super("createIndexWithOptions", collection, key);
    this.options = options;
  }

  public IndexOptions getOptions() {
    return options;
  }

  @Override
  public String toString() {
    return super.toString() + ", options: " + (options != null ? options.toJson().encode() : "null");
  }
}
