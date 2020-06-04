package com.noenv.wiremongo.mapping;

import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindOneAndDelete extends WithQuery<JsonObject, FindOneAndDelete> {

  public static class FindOneAndDeleteCommand extends WithQueryCommand {

    public FindOneAndDeleteCommand(String collection, JsonObject query) {
      super("findOneAndDelete", collection, query);
    }

    public FindOneAndDeleteCommand(String method, String collection, JsonObject query) {
      super(method, collection, query);
    }
  }

  public FindOneAndDelete() {
    this("findOneAndDelete");
  }

  public FindOneAndDelete(String method) {
    super(method);
  }

  public FindOneAndDelete(JsonObject json) {
    super(json);
  }

  @Override
  protected JsonObject parseResponse(Object jsonValue) {
    return (JsonObject) jsonValue;
  }
}
