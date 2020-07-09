package com.noenv.wiremongo.command;

import io.vertx.core.json.JsonObject;

public class CountCommand extends WithQueryCommand {
  public CountCommand(String collection, JsonObject query) {
    super("count", collection, query);
  }
}
