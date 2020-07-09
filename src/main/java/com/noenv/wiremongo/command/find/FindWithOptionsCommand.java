package com.noenv.wiremongo.command.find;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;

public class FindWithOptionsCommand extends FindBaseCommand {

  private final FindOptions options;

  public FindWithOptionsCommand(String collection, JsonObject query, FindOptions options) {
    super("findWithOptions", collection, query);
    this.options = options;
  }

  public FindOptions getOptions() {
    return options;
  }

  @Override
  public String toString() {
    return super.toString() + ", options: " + (options != null ? options.toJson().encode() : "null");
  }
}
