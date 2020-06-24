package com.noenv.wiremongo.command.find;

import com.noenv.wiremongo.command.WithQueryCommand;
import io.vertx.core.json.JsonObject;

public class FindOneAndDeleteBaseCommand extends WithQueryCommand {

  public FindOneAndDeleteBaseCommand(String collection, JsonObject query) {
    super("findOneAndDelete", collection, query);
  }

  public FindOneAndDeleteBaseCommand(String method, String collection, JsonObject query) {
    super(method, collection, query);
  }
}
