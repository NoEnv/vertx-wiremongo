package com.noenv.wiremongo.command.collection;

import io.vertx.ext.mongo.RenameCollectionOptions;

public class RenameCollectionWithOptionsCommand extends RenameCollectionCommand {
  private final RenameCollectionOptions options;

  public RenameCollectionWithOptionsCommand(String oldCollectionName, String newCollectionName, RenameCollectionOptions options) {
    super("renameCollectionWithOptions", oldCollectionName, newCollectionName);
    this.options = options;
  }

  public RenameCollectionOptions getOptions() {
    return options;
  }

  @Override
  public String toString() {
    return super.toString() + ", " + (options != null ? options.toJson().encode() : "null");
  }
}
