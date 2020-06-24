package com.noenv.wiremongo.command.find;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindBatchWithOptionsCommand extends FindBatchBaseCommand {
  private final FindOptions options;

  public FindBatchWithOptionsCommand(String collection, JsonObject query, FindOptions options) {
    super("findBatchWithOptions", collection, query);
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
