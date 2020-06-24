package com.noenv.wiremongo.command.stream;

import io.vertx.core.json.JsonArray;

public abstract class WithStreamPipelineCommand extends WithStreamCommand {

  private final JsonArray pipeline;

  public WithStreamPipelineCommand(String method, String collection, JsonArray pipeline) {
    super(method, collection);
    this.pipeline = pipeline;
  }

  public JsonArray getPipeline() {
    return pipeline;
  }

  @Override
  public String toString() {
    return super.toString() + ", pipeline: " + pipeline;
  }
}
