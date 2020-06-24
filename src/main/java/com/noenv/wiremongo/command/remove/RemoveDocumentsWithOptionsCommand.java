package com.noenv.wiremongo.command.remove;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.WriteOption;

public class RemoveDocumentsWithOptionsCommand extends RemoveDocumentsBaseCommand {

  private final WriteOption options;

  public RemoveDocumentsWithOptionsCommand(String collection, JsonObject query, WriteOption options) {
    super("removeDocumentsWithOptions", collection, query);
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
