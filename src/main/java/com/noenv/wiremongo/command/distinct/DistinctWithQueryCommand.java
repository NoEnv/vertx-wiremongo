package com.noenv.wiremongo.command.distinct;

import com.noenv.wiremongo.command.WithQueryCommand;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.DistinctOptions;

public class DistinctWithQueryCommand extends WithQueryCommand {

  private final String fieldName;
  private final String resultClassname;
  private final DistinctOptions options;

  public DistinctWithQueryCommand(String collection, String fieldName, String resultClassname, JsonObject query) {
    this(collection, fieldName, resultClassname, query, null);
  }

  public DistinctWithQueryCommand(String collection, String fieldName, String resultClassname, JsonObject query, DistinctOptions options) {
    super("distinctWithQuery", collection, query);
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
    return super.toString() + ", fieldName: " + fieldName + ", resultClassname: " + resultClassname;
  }
}
