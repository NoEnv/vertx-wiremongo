package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.mapping.update.WithUpdate;
import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public abstract class FindOneAndUpdateBase<C extends FindOneAndUpdateBase<C>> extends WithUpdate<JsonObject, C> {

  public static class FindOneAndUpdateBaseCommand extends WithUpdateCommand {

    public FindOneAndUpdateBaseCommand(String collection, JsonObject query, JsonObject update) {
      this("findOneAndUpdate", collection, query, update);
    }

    public FindOneAndUpdateBaseCommand(String method, String collection, JsonObject query, JsonObject update) {
      super(method, collection, query, update);
    }
  }

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
