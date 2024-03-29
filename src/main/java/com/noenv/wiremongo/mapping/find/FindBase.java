package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.command.find.FindBaseCommand;
import com.noenv.wiremongo.mapping.WithQuery;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.stream.Collectors;

public abstract class FindBase<U extends FindBaseCommand, C extends FindBase<U, C>> extends WithQuery<List<JsonObject>, U, C> {

  protected FindBase() {
    this("find");
  }

  protected FindBase(String method) {
    super(method);
  }

  protected FindBase(JsonObject json) {
    super(json);
  }

  @Override
  public C returns(final List<JsonObject> response) {
    return super.stub(c -> null == response ? null : response.stream().map(JsonObject::copy).collect(java.util.stream.Collectors.toList()));
  }

  @Override
  protected List<JsonObject> parseResponse(Object jsonValue) {
    return ((JsonArray) jsonValue).stream()
      .map(JsonObject.class::cast)
      .collect(Collectors.toList());
  }
}
