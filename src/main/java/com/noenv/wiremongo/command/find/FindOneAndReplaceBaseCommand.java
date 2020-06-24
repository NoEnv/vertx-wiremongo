package com.noenv.wiremongo.command.find;

import com.noenv.wiremongo.command.replace.WithReplaceCommand;
import io.vertx.core.json.JsonObject;

public class FindOneAndReplaceBaseCommand extends WithReplaceCommand {

  public FindOneAndReplaceBaseCommand(String collection, JsonObject query, JsonObject replace) {
    this("findOneAndReplace", collection, query, replace);
  }

  public FindOneAndReplaceBaseCommand(String method, String collection, JsonObject query, JsonObject replace) {
    super(method, collection, query, replace);
  }
}
