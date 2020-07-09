package com.noenv.wiremongo.command.distinct;

import com.noenv.wiremongo.command.stream.WithStreamCommand;

public class DistinctBatchBaseCommand extends WithStreamCommand {

  private final String fieldName;
  private final String resultClassname;

  public DistinctBatchBaseCommand(String collection, String fieldName, String resultClassname) {
    this("distinctBatch", collection, fieldName, resultClassname);
  }

  public DistinctBatchBaseCommand(String method, String collection, String fieldName, String resultClassname) {
    super(method, collection);
    this.fieldName = fieldName;
    this.resultClassname = resultClassname;
  }

  public String getFieldName() {
    return fieldName;
  }

  public String getResultClassname() {
    return resultClassname;
  }

  @Override
  public String toString() {
    return super.toString() + ", fieldName: " + fieldName + ", resultClassname: " + resultClassname;
  }
}
