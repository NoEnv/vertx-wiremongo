package com.noenv.wiremongo.command.stream;

import io.vertx.core.json.JsonObject;

public abstract class WithStreamQueryCommand extends WithStreamCommand {

  private final JsonObject query;

  public WithStreamQueryCommand(String method, String collection, JsonObject query) {
    super(method, collection);
    this.query = query;
  }

  public JsonObject getQuery() {
    return query;
  }

  @Override
  public String toString() {
    return super.toString() + ", query: " + query;
  }
}
