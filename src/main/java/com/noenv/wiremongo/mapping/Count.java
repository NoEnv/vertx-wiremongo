package com.noenv.wiremongo.mapping;

import io.vertx.core.json.JsonObject;

public class Count extends WithQuery<Long, Count> {

  public static class CountCommand extends WithQueryCommand {
    public CountCommand(String collection, JsonObject query) {
      super("count", collection, query);
    }
  }

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
