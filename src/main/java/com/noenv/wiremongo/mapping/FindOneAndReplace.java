package com.noenv.wiremongo.mapping;

import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindOneAndReplace extends WithReplace<JsonObject, FindOneAndReplace> {

  public static class FindOneAndReplaceCommand extends WithReplaceCommand {

    public FindOneAndReplaceCommand(String collection, JsonObject query, JsonObject replace) {
      this("findOneAndReplace", collection, query, replace);
    }

    public FindOneAndReplaceCommand(String method, String collection, JsonObject query, JsonObject replace) {
      super(method, collection, query, replace);
    }
  }

  public FindOneAndReplace() {
    this("findOneAndReplace");
  }

  public FindOneAndReplace(String method) {
    super(method);
  }

  public FindOneAndReplace(JsonObject json) {
    super(json);
  }

  @Override
  protected JsonObject parseResponse(Object jsonValue) {
    return (JsonObject) jsonValue;
  }
}
