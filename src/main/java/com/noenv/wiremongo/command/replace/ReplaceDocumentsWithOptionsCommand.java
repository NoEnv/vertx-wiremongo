package com.noenv.wiremongo.command.replace;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.UpdateOptions;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class ReplaceDocumentsWithOptionsCommand extends ReplaceDocumentsBaseCommand {

  private final UpdateOptions options;

  public ReplaceDocumentsWithOptionsCommand(String collection, JsonObject query, JsonObject replace, UpdateOptions options) {
    super("replaceDocumentsWithOptions", collection, query, replace);
    this.options = options;
  }

  public UpdateOptions getOptions() {
    return options;
  }

  @Override
  public String toString() {
    return super.toString() + ", options: " + (options == null ? "null" : options.toJson().encode());
  }
}
