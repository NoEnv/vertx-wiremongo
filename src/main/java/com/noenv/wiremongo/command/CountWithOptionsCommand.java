package com.noenv.wiremongo.command;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.CountOptions;

public class CountWithOptionsCommand extends WithQueryCommand {

  private final CountOptions options;

  public CountWithOptionsCommand(String collection, JsonObject query, CountOptions options) {
    super("countWithOptions", collection, query);
    this.options = options;
  }

  public CountOptions getOptions() {
    return options;
  }

  @Override
  public String toString() {
    return super.toString() + ", options: " + (options != null ? options.toJson().encode() : "null");
  }
}
