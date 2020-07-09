package com.noenv.wiremongo.command.find;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.mongo.UpdateOptions;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindOneAndUpdateWithOptionsCommand extends FindOneAndUpdateBaseCommand {

  private final FindOptions findOptions;
  private final UpdateOptions updateOptions;

  public FindOneAndUpdateWithOptionsCommand(String collection, JsonObject query, JsonObject update, FindOptions findOptions, UpdateOptions updateOptions) {
    super("findOneAndUpdateWithOptions", collection, query, update);
    this.findOptions = findOptions;
    this.updateOptions = updateOptions;
  }

  public FindOptions getFindOptions() {
    return findOptions;
  }

  public UpdateOptions getUpdateOptions() {
    return updateOptions;
  }

  @Override
  public String toString() {
    return super.toString()
      + ", findOptions: " + (findOptions != null ? findOptions.toJson().encode() : "null")
      + ", updateOptions: " + (updateOptions != null ? updateOptions.toJson().encode() : "null");
  }
}
