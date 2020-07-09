package com.noenv.wiremongo.mapping.stream;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.stream.WithStreamPipelineCommand;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

public abstract class WithStreamPipeline<U extends WithStreamPipelineCommand, C extends WithStreamPipeline<U, C>> extends WithStream<U, C> {

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
    return pipeline == null || pipeline.matches(c.getPipeline());
  }

  public C withPipeline(JsonArray pipeline) {
    return withPipeline(equalTo(pipeline));
  }

  public C withPipeline(Matcher<JsonArray> pipeline) {
    this.pipeline = pipeline;
    return self();
  }
}
