package com.noenv.wiremongo.mapping.stream;

import com.noenv.wiremongo.MemoryStream;
import com.noenv.wiremongo.command.stream.WithStreamCommand;
import com.noenv.wiremongo.mapping.collection.WithCollection;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.streams.ReadStream;

import java.util.stream.Collectors;

public abstract class WithStream<U extends WithStreamCommand, C extends WithStream<U, C>> extends WithCollection<ReadStream<JsonObject>, U, C> {

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
