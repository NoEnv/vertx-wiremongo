package com.noenv.wiremongo.command.save;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.WriteOption;

public class SaveWithOptionsCommand extends SaveBaseCommand {

  private final WriteOption options;

  public SaveWithOptionsCommand(String collection, JsonObject document, WriteOption options) {
    super("saveWithOptions", collection, document);
    this.options = options;
  }

  public WriteOption getOptions() {
    return options;
  }

  @Override
  public String toString() {
    return super.toString() + ", options: " + (options == null ? "null" : options.toString());
  }
}
