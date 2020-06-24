package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.mapping.replace.WithReplace;
import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public abstract class FindOneAndReplaceBase<C extends FindOneAndReplaceBase<C>> extends WithReplace<JsonObject, C> {

  public static class FindOneAndReplaceBaseCommand extends WithReplaceCommand {

    public FindOneAndReplaceBaseCommand(String collection, JsonObject query, JsonObject replace) {
      this("findOneAndReplace", collection, query, replace);
    }

    public FindOneAndReplaceBaseCommand(String method, String collection, JsonObject query, JsonObject replace) {
      super(method, collection, query, replace);
    }
  }

  public FindOneAndReplaceBase() {
    this("findOneAndReplace");
  }

  public FindOneAndReplaceBase(String method) {
    super(method);
  }

  public FindOneAndReplaceBase(JsonObject json) {
    super(json);
  }

  @Override
  protected JsonObject parseResponse(Object jsonValue) {
    return (JsonObject) jsonValue;
  }
}
