package com.noenv.wiremongo.command.distinct;

import com.noenv.wiremongo.command.collection.WithCollectionCommand;
import io.vertx.ext.mongo.DistinctOptions;

public class DistinctWithOptionsCommand extends WithCollectionCommand {

  private final String fieldName;
  private final String resultClassname;
  private final DistinctOptions options;

  public DistinctWithOptionsCommand(String collection, String fieldName, String resultClassname, DistinctOptions options) {
    super("distinct", collection);
    this.fieldName = fieldName;
    this.resultClassname = resultClassname;
    this.options = options;
  }

  public DistinctOptions getOptions() {
    return options;
  }

  public String getFieldName() {
    return fieldName;
  }

  public String getResultClassname() {
    return resultClassname;
  }

  @Override
  public String toString() {
    return super.toString() + ", fieldName: " + fieldName + ", resultClassname: " + resultClassname + ", options: " + options;
  }
}
