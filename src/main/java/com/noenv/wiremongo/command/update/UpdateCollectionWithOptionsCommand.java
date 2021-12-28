package com.noenv.wiremongo.command.update;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.UpdateOptions;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class UpdateCollectionWithOptionsCommand<T> extends UpdateCollectionBaseCommand<T> {

  private final UpdateOptions options;

  public UpdateCollectionWithOptionsCommand(String collection, JsonObject query, T update, UpdateOptions options) {
    this("updateCollectionWithOptions", collection, query, update, options);
  }

  public UpdateCollectionWithOptionsCommand(String method, String collection, JsonObject query, T update, UpdateOptions options) {
    super(method, collection, query, update);
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
