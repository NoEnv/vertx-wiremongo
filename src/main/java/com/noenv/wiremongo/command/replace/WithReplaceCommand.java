package com.noenv.wiremongo.command.replace;

import com.noenv.wiremongo.command.WithQueryCommand;
import io.vertx.core.json.JsonObject;

public abstract class WithReplaceCommand extends WithQueryCommand {

  private final JsonObject replace;

  public WithReplaceCommand(String method, String collection, JsonObject query, JsonObject replace) {
    super(method, collection, query);
    this.replace = replace;
  }

  public JsonObject getReplace() {
    return replace;
  }

  @Override
  public String toString() {
    return super.toString() + ", replace: " + replace;
  }
}
