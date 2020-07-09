package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.command.CountCommand;
import io.vertx.core.json.JsonObject;

public class Count extends WithQuery<Long, CountCommand, Count> {

  public Count() {
    super("count");
  }

  public Count(JsonObject json) {
    super(json);
  }

  @Override
  protected Long parseResponse(Object jsonValue) {
    return ((Number) jsonValue).longValue();
  }
}
