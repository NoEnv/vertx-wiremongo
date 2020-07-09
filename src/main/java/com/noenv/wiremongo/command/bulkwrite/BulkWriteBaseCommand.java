package com.noenv.wiremongo.command.bulkwrite;

import com.noenv.wiremongo.command.collection.WithCollectionCommand;
import io.vertx.ext.mongo.BulkOperation;

import java.util.List;
import java.util.stream.Collectors;

public class BulkWriteBaseCommand extends WithCollectionCommand {
  private final List<BulkOperation> operations;

  public BulkWriteBaseCommand(String collection, List<BulkOperation> operations) {
    this("bulkWrite", collection, operations);
  }

  public BulkWriteBaseCommand(String method, String collection, List<BulkOperation> operations) {
    super(method, collection);
    this.operations = operations;
  }

  public List<BulkOperation> getOperations() {
    return operations;
  }

  @Override
  public String toString() {
    return super.toString() + ", operations: " + (operations == null ? "null" : operations.stream()
      .map(o -> o.toJson().encode()).collect(Collectors.joining(",")));
  }
}
