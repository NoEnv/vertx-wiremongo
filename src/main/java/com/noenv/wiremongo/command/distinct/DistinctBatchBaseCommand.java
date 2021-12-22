package com.noenv.wiremongo.command.distinct;

import com.noenv.wiremongo.command.stream.WithStreamCommand;
import io.vertx.ext.mongo.DistinctOptions;

public class DistinctBatchBaseCommand extends WithStreamCommand {

  private final String fieldName;
  private final String resultClassname;
  private final DistinctOptions options;

  public DistinctBatchBaseCommand(String collection, String fieldName, String resultClassname) {
    this("distinctBatch", collection, fieldName, resultClassname,null);
  }

  public DistinctBatchBaseCommand(String collection, String fieldName, String resultClassname, DistinctOptions options) {
    this("distinctBatch", collection, fieldName, resultClassname,null);
  }

  protected DistinctBatchBaseCommand(String method, String collection, String fieldName, String resultClassname, DistinctOptions options) {
    super(method, collection);
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
