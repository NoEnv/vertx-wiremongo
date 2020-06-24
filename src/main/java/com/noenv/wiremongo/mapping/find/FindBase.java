package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.mapping.WithQuery;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.stream.Collectors;

public abstract class FindBase<C extends FindBase<C>> extends WithQuery<List<JsonObject>, C> {

  public static class FindBaseCommand extends WithQueryCommand {
    public FindBaseCommand(String collection, JsonObject query) {
      this("find", collection, query);
    }

    public FindBaseCommand(String method, String collection, JsonObject query) {
      super(method, collection, query);
    }
  }

  public FindBase() {
    this("find");
  }

  public FindBase(String method) {
    super(method);
  }

  public FindBase(JsonObject json) {
    super(json);
  }

  @Override
  protected List<JsonObject> parseResponse(Object jsonValue) {
    return ((JsonArray) jsonValue).stream()
      .map(o -> (JsonObject) o)
      .collect(Collectors.toList());
  }
}
