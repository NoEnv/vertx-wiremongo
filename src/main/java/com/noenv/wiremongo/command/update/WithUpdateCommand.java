package com.noenv.wiremongo.command.update;

import com.noenv.wiremongo.command.WithQueryCommand;
import io.vertx.core.json.JsonObject;

public abstract class WithUpdateCommand extends WithQueryCommand {

  private final JsonObject update;

  public WithUpdateCommand(String method, String collection, JsonObject query, JsonObject update) {
    super(method, collection, query);
    this.update = update;
  }

  public JsonObject getUpdate() {
    return update;
  }

  @Override
  public String toString() {
    return super.toString() + ", update: " + update;
  }
}
