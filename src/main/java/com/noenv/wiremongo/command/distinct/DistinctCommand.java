package com.noenv.wiremongo.command.distinct;

import com.noenv.wiremongo.command.collection.WithCollectionCommand;

public class DistinctCommand extends WithCollectionCommand {

  private final String fieldName;
  private final String resultClassname;

  public DistinctCommand(String collection, String fieldName, String resultClassname) {
    super("distinct", collection);
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
