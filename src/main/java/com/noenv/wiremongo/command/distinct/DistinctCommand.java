package com.noenv.wiremongo.command.distinct;

import com.noenv.wiremongo.command.collection.WithCollectionCommand;
import io.vertx.ext.mongo.DistinctOptions;

public class DistinctCommand extends WithCollectionCommand {

  private final String fieldName;
  private final String resultClassname;
  private final DistinctOptions options;

  public DistinctCommand(String collection, String fieldName, String resultClassname) {
    this(collection, fieldName, resultClassname, null);
  }

  public DistinctCommand(String collection, String fieldName, String resultClassname, DistinctOptions options) {
    super("distinct", collection);
    this.fieldName = fieldName;
    this.resultClassname = resultClassname;
    this.options = options;
  }

  public String getFieldName() {
    return fieldName;
  }

  public String getResultClassname() {
    return resultClassname;
  }

  public DistinctOptions getOptions() {
    return options;
  }

  @Override
  public String toString() {
    return super.toString() + ", fieldName: " + fieldName + ", resultClassname: " + resultClassname + ", options: " + (options != null ? options.toJson().encode() : "null");
  }
}
