package com.noenv.wiremongo.command.find;

import com.noenv.wiremongo.command.WithQueryCommand;
import io.vertx.core.json.JsonObject;

public class FindBaseCommand extends WithQueryCommand {

  public FindBaseCommand(String collection, JsonObject query) {
    this("find", collection, query);
  }

  public FindBaseCommand(String method, String collection, JsonObject query) {
    super(method, collection, query);
  }
}
