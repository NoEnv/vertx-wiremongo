package com.noenv.wiremongo.command.insert;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.WriteOption;

public class InsertWithOptionsCommand extends InsertBaseCommand {

  private WriteOption options;

  public InsertWithOptionsCommand(String collection, JsonObject document, WriteOption options) {
    super("insertWithOptions", collection, document);
    this.options = options;
  }

  public WriteOption getOptions() {
    return options;
  }

  @Override
  public String toString() {
    return super.toString() + ", options: " + (options != null ? options.name() : "null");
  }
}
