package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class Aggregate extends WithStreamPipeline {

  public static class AggregateCommand extends WithStreamPipelineCommand {
    public AggregateCommand(String collection, JsonArray pipeline) {
      this("aggregate", collection, pipeline);
    }

    public AggregateCommand(String method, String collection, JsonArray pipeline) {
      super(method, collection, pipeline);
    }
  }

  public Aggregate() {
    this("aggregate");
  }

  public Aggregate(String method) {
    super(method);
  }

  public Aggregate(JsonObject json) {
    super(json);
  }

  // fluent


  @Override
  public Aggregate priority(int priority) {
    return (Aggregate) super.priority(priority);
  }

  @Override
  public Aggregate inCollection(String collection) {
    return (Aggregate) super.inCollection(collection);
  }

  @Override
  public Aggregate inCollection(Matcher<String> collection) {
    return (Aggregate) super.inCollection(collection);
  }

  @Override
  public Aggregate withPipeline(JsonArray query) {
    return (Aggregate) super.withPipeline(query);
  }

  @Override
  public Aggregate withPipeline(Matcher<JsonArray> query) {
    return (Aggregate) super.withPipeline(query);
  }
}
