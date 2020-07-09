package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.command.find.FindBaseCommand;
import com.noenv.wiremongo.mapping.WithQuery;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.stream.Collectors;

public abstract class FindBase<U extends FindBaseCommand, C extends FindBase<U, C>> extends WithQuery<List<JsonObject>, U, C> {

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
