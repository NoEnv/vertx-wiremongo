package com.noenv.wiremongo.mapping.aggregate;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.aggregate.AggregateWithOptionsCommand;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.AggregateOptions;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class AggregateWithOptions extends AggregateBase<AggregateWithOptionsCommand, AggregateWithOptions> {

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
    return options == null || options.matches(c.getOptions());
  }

  public AggregateWithOptions withOptions(AggregateOptions options) {
    return withOptions(equalTo(options));
  }

  public AggregateWithOptions withOptions(Matcher<AggregateOptions> options) {
    this.options = options;
    return self();
  }
}
