package com.noenv.wiremongo.command.distinct;

import com.noenv.wiremongo.command.WithQueryCommand;
import io.vertx.core.json.JsonObject;

public class DistinctWithQueryCommand extends WithQueryCommand {

  private final String fieldName;
  private final String resultClassname;

  public DistinctWithQueryCommand(String collection, String fieldName, String resultClassname, JsonObject query) {
    super("distinctWithQuery", collection, query);
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
