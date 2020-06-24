package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.command.find.FindOneAndReplaceBaseCommand;
import com.noenv.wiremongo.mapping.replace.WithReplace;
import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public abstract class FindOneAndReplaceBase<U extends FindOneAndReplaceBaseCommand, C extends FindOneAndReplaceBase<U, C>> extends WithReplace<JsonObject, U, C> {

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
