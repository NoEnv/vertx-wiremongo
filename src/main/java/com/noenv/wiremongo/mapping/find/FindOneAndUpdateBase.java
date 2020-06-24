package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.command.find.FindOneAndUpdateBaseCommand;
import com.noenv.wiremongo.mapping.update.WithUpdate;
import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public abstract class FindOneAndUpdateBase<U extends FindOneAndUpdateBaseCommand, C extends FindOneAndUpdateBase<U, C>> extends WithUpdate<JsonObject, U, C> {

  public FindOneAndUpdateBase() {
    this("findOneAndUpdate");
  }

  public FindOneAndUpdateBase(String method) {
    super(method);
  }

  public FindOneAndUpdateBase(JsonObject json) {
    super(json);
  }

  @Override
  protected JsonObject parseResponse(Object jsonValue) {
    return (JsonObject) jsonValue;
  }
}
