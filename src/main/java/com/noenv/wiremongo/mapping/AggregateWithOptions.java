package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.AggregateOptions;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class AggregateWithOptions extends AggregateBase<AggregateWithOptions> {

  public static class AggregateWithOptionsCommand extends AggregateBaseCommand {

    private final AggregateOptions options;

    public AggregateWithOptionsCommand(String collection, JsonArray pipeline, AggregateOptions options) {
      super("aggregateWithOptions", collection, pipeline);
      this.options = options;
    }

    @Override
    public String toString() {
      return super.toString() + ", options: " + (options == null ? "null" : options.toJson().encode());
    }
  }

  private Matcher<AggregateOptions> options;

  public AggregateWithOptions() {
    super("aggregateWithOptions");
  }

  public AggregateWithOptions(JsonObject json) {
    super(json);
    options = Matcher.create(json.getJsonObject("options"), j -> new AggregateOptions((JsonObject) j), AggregateOptions::toJson);
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof AggregateWithOptionsCommand)) {
      return false;
    }
    AggregateWithOptionsCommand c = (AggregateWithOptionsCommand) cmd;
    return options == null || options.matches(c.options);
  }

  public AggregateWithOptions withOptions(AggregateOptions options) {
    return withOptions(equalTo(options));
  }

  public AggregateWithOptions withOptions(Matcher<AggregateOptions> options) {
    this.options = options;
    return self();
  }
}
