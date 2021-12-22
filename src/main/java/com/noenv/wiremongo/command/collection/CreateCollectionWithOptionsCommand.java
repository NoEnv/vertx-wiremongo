package com.noenv.wiremongo.command.collection;

import io.vertx.ext.mongo.CreateCollectionOptions;

public class CreateCollectionWithOptionsCommand extends WithCollectionCommand {

  private final CreateCollectionOptions options;

  public CreateCollectionWithOptionsCommand(String collection, CreateCollectionOptions options) {
    super("createCollectionWithOptions", collection);
    this.options = options;
  }

  public CreateCollectionOptions getOptions() {
    return options;
  }

  @Override
  public String toString() {
    return super.toString() + ", options: " + (options != null ? options.toJson().encode() : "null");
  }
}
