package com.noenv.wiremongo.command.index;

import com.noenv.wiremongo.command.collection.WithCollectionCommand;
import io.vertx.ext.mongo.IndexModel;

import java.util.List;
import java.util.stream.Collectors;

public class CreateIndexesCommand extends WithCollectionCommand {

  private final List<IndexModel> indexModels;

  public CreateIndexesCommand(String collection, List<IndexModel> indexModels) {
    this("createIndexes", collection, indexModels);
  }

  public CreateIndexesCommand(String method, String collection, List<IndexModel> indexModels) {
    super(method, collection);
    this.indexModels = indexModels;
  }

  public List<IndexModel> getIndexModels() {
    return indexModels;
  }

  @Override
  public String toString() {
    return super.toString() + ", indexModels: " + (indexModels == null ? "null" : indexModels.stream()
      .map(IndexModel::toString).collect(Collectors.joining(",")));
  }
}
