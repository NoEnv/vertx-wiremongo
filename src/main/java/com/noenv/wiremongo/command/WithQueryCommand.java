package com.noenv.wiremongo.command;

import com.noenv.wiremongo.command.collection.WithCollectionCommand;
import io.vertx.core.json.JsonObject;

public abstract class WithQueryCommand extends WithCollectionCommand {

  private final JsonObject query;

  public WithQueryCommand(String method, String collection, JsonObject query) {
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
