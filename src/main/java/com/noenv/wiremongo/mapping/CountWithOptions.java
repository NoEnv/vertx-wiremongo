package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.CountWithOptionsCommand;
import com.noenv.wiremongo.matching.JsonMatcher;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.CountOptions;

public class CountWithOptions extends WithQuery<Long, CountWithOptionsCommand, CountWithOptions> {

  private Matcher<CountOptions> options;

  public CountWithOptions() {
    super("countWithOptions");
  }

  public CountWithOptions(JsonObject json) {
    super(json);
    options = Matcher.create(json.getJsonObject("options"), j -> new CountOptions((JsonObject) j), CountOptions::toJson);
  }

  @Override
  protected Long parseResponse(Object jsonValue) {
    return ((Number) jsonValue).longValue();
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof CountWithOptionsCommand)) {
      return false;
    }
    CountWithOptionsCommand c = (CountWithOptionsCommand) cmd;
    return options == null || options.matches(c.getOptions());
  }

  public CountWithOptions withOptions(CountOptions options) {
    return withOptions(JsonMatcher.equalToJson(options.toJson(), CountOptions::toJson));
  }

  public CountWithOptions withOptions(Matcher<CountOptions> options) {
    this.options = options;
    return self();
  }
}
