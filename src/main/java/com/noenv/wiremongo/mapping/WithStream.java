package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.MemoryStream;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.streams.ReadStream;

import java.util.stream.Collectors;

abstract class WithStream<C extends WithStream<C>> extends WithCollection<ReadStream<JsonObject>, C> {

  public abstract static class WithStreamCommand extends WithCollectionCommand {
    public WithStreamCommand(String method, String collection) {
      super(method, collection);
    }
  }

  public WithStream(String method) {
    super(method);
  }

  public WithStream(JsonObject json) {
    super(json);
  }

  @Override
  protected ReadStream<JsonObject> parseResponse(Object jsonValue) {
    return MemoryStream.fromList(((JsonArray) jsonValue).stream()
      .map(o -> (JsonObject) o)
      .collect(Collectors.toList()));
  }
}
