package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.command.PingCommand;
import io.vertx.core.json.JsonObject;

public class Ping extends MappingBase<JsonObject, PingCommand, Ping> {

  public Ping() {
    super("ping");
  }

  public Ping(JsonObject json) {
    super(json);
  }

  @Override
  public Ping returns(final JsonObject response) {
    return stub(c -> null == response ? null : response.copy());
  }

  @Override
  protected JsonObject parseResponse(Object jsonValue) {
    return (JsonObject) jsonValue;
  }
}
