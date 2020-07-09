package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.command.find.FindOneAndDeleteBaseCommand;
import com.noenv.wiremongo.mapping.WithQuery;
import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public abstract class FindOneAndDeleteBase<U extends FindOneAndDeleteBaseCommand, C extends FindOneAndDeleteBase<U, C>> extends WithQuery<JsonObject, U, C> {

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
