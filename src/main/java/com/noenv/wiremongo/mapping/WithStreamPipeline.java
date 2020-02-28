package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

abstract class WithStreamPipeline extends WithStream {

  public abstract static class WithStreamPipelineCommand extends WithStreamCommand {

    private final JsonArray pipeline;

    public WithStreamPipelineCommand(String method, String collection, JsonArray pipeline) {
      super(method, collection);
      this.pipeline = pipeline;
    }

    @Override
    public String toString() {
      return super.toString() + ", pipeline: " + pipeline;
    }
  }

  private Matcher<JsonArray> pipeline;

  public WithStreamPipeline(String method) {
    super(method);
  }

  public WithStreamPipeline(JsonObject json) {
    super(json);
    pipeline = equalTo(json.getJsonArray("pipeline"));
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof WithStreamPipelineCommand)) {
      return false;
    }
    WithStreamPipelineCommand c = (WithStreamPipelineCommand) cmd;
    return pipeline == null || pipeline.matches(c.pipeline);
  }

  public WithStreamPipeline withPipeline(JsonArray pipeline) {
    return withPipeline(equalTo(pipeline));
  }

  public WithStreamPipeline withPipeline(Matcher<JsonArray> pipeline) {
    this.pipeline = pipeline;
    return this;
  }
}
