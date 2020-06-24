package com.noenv.wiremongo.command.find;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;

public class FindOneAndDeleteWithOptionsCommand extends FindOneAndDeleteBaseCommand {

  private FindOptions options;

  public FindOneAndDeleteWithOptionsCommand(String collection, JsonObject query, FindOptions options) {
    super("findOneAndDeleteWithOptions", collection, query);
    this.options = options;
  }

  public FindOptions getOptions() {
    return options;
  }

  @Override
  public String toString() {
    return super.toString() + ", options: " + (options == null ? "null" : options.toJson().encode());
  }
}
