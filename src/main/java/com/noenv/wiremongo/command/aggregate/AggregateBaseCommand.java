package com.noenv.wiremongo.command.aggregate;

import com.noenv.wiremongo.command.stream.WithStreamPipelineCommand;
import io.vertx.core.json.JsonArray;

public class AggregateBaseCommand extends WithStreamPipelineCommand {

  public AggregateBaseCommand(String collection, JsonArray pipeline) {
    this("aggregate", collection, pipeline);
  }

  public AggregateBaseCommand(String method, String collection, JsonArray pipeline) {
    super(method, collection, pipeline);
  }
}
