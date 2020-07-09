package com.noenv.wiremongo.command.find;

import com.noenv.wiremongo.command.update.WithUpdateCommand;
import io.vertx.core.json.JsonObject;

public class FindOneAndUpdateBaseCommand extends WithUpdateCommand {

  public FindOneAndUpdateBaseCommand(String collection, JsonObject query, JsonObject update) {
    this("findOneAndUpdate", collection, query, update);
  }

  public FindOneAndUpdateBaseCommand(String method, String collection, JsonObject query, JsonObject update) {
    super(method, collection, query, update);
  }
}
