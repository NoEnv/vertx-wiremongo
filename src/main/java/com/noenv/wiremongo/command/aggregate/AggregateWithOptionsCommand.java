package com.noenv.wiremongo.command.aggregate;

import io.vertx.core.json.JsonArray;
import io.vertx.ext.mongo.AggregateOptions;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class AggregateWithOptionsCommand extends AggregateBaseCommand {

  private final AggregateOptions options;

  public AggregateWithOptionsCommand(String collection, JsonArray pipeline, AggregateOptions options) {
    super("aggregateWithOptions", collection, pipeline);
    this.options = options;
  }

  public AggregateOptions getOptions() {
    return options;
  }

  @Override
  public String toString() {
    return super.toString() + ", options: " + (options == null ? "null" : options.toJson().encode());
  }
}
