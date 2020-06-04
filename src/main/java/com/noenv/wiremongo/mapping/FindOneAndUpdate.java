package com.noenv.wiremongo.mapping;

import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindOneAndUpdate extends WithUpdate<JsonObject, FindOneAndUpdate> {

  public static class FindOneAndUpdateCommand extends WithUpdateCommand {

    public FindOneAndUpdateCommand(String collection, JsonObject query, JsonObject update) {
      this("findOneAndUpdate", collection, query, update);
    }

    public FindOneAndUpdateCommand(String method, String collection, JsonObject query, JsonObject update) {
      super(method, collection, query, update);
    }
  }

  public FindOneAndUpdate() {
    this("findOneAndUpdate");
  }

  public FindOneAndUpdate(String method) {
    super(method);
  }

  public FindOneAndUpdate(JsonObject json) {
    super(json);
  }

  @Override
  protected JsonObject parseResponse(Object jsonValue) {
    return (JsonObject) jsonValue;
  }
}
