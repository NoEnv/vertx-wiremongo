package com.noenv.wiremongo.command.bulkwrite;

import io.vertx.ext.mongo.BulkOperation;
import io.vertx.ext.mongo.BulkWriteOptions;

import java.util.List;

public class BulkWriteWithOptionsCommand extends BulkWriteBaseCommand {

  private final BulkWriteOptions options;

  public BulkWriteWithOptionsCommand(String collection, List<BulkOperation> operations, BulkWriteOptions options) {
    super("bulkWriteWithOptions", collection, operations);
    this.options = options;
  }

  public BulkWriteOptions getOptions() {
    return options;
  }

  @Override
  public String toString() {
    return super.toString() + ", options: " + (options != null ? options.toJson().encode() : "null");
  }
}
