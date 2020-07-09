package com.noenv.wiremongo.command.update;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.UpdateOptions;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class UpdateCollectionWithOptionsCommand extends UpdateCollectionBaseCommand {

  private final UpdateOptions options;

  public UpdateCollectionWithOptionsCommand(String collection, JsonObject query, JsonObject update, UpdateOptions options) {
    super("updateCollectionWithOptions", collection, query, update);
    this.options = options;
  }

  public UpdateOptions getOptions() {
    return options;
  }

  @Override
  public String toString() {
    return super.toString() + ", options: " + (options != null ? options.toJson().encode() : "null");
  }
}
