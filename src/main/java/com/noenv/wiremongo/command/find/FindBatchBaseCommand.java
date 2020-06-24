package com.noenv.wiremongo.command.find;

import com.noenv.wiremongo.command.stream.WithStreamQueryCommand;
import io.vertx.core.json.JsonObject;

public class FindBatchBaseCommand extends WithStreamQueryCommand {

  public FindBatchBaseCommand(String collection, JsonObject query) {
    this("findBatch", collection, query);
  }

  public FindBatchBaseCommand(String method, String collection, JsonObject query) {
    super(method, collection, query);
  }
}
