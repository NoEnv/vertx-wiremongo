package com.noenv.wiremongo.mapping;

import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public abstract class FindOneAndDeleteBase<C extends FindOneAndDeleteBase<C>> extends WithQuery<JsonObject, C> {

  public static class FindOneAndDeleteBaseCommand extends WithQueryCommand {

    public FindOneAndDeleteBaseCommand(String collection, JsonObject query) {
      super("findOneAndDelete", collection, query);
    }

    public FindOneAndDeleteBaseCommand(String method, String collection, JsonObject query) {
      super(method, collection, query);
    }
  }

  public FindOneAndDeleteBase() {
    this("findOneAndDelete");
  }

  public FindOneAndDeleteBase(String method) {
    super(method);
  }

  public FindOneAndDeleteBase(JsonObject json) {
    super(json);
  }

  @Override
  protected JsonObject parseResponse(Object jsonValue) {
    return (JsonObject) jsonValue;
  }
}
