package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.find.FindWithOptionsCommand;
import com.noenv.wiremongo.matching.JsonMatcher;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindWithOptions extends FindBase<FindWithOptionsCommand, FindWithOptions> {

  private Matcher<FindOptions> options;

  public FindWithOptions() {
    super("findWithOptions");
  }

  public FindWithOptions(JsonObject json) {
    super(json);
    options = Matcher.create(json.getJsonObject("options"), j -> new FindOptions((JsonObject) j), FindOptions::toJson);
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof FindWithOptionsCommand)) {
      return false;
    }
    FindWithOptionsCommand c = (FindWithOptionsCommand) cmd;
    return options == null || options.matches(c.getOptions());
  }

  public FindWithOptions withOptions(FindOptions options) {
    return withOptions(JsonMatcher.equalToJson(options.toJson(), FindOptions::toJson));
  }

  public FindWithOptions withOptions(Matcher<FindOptions> options) {
    this.options = options;
    return self();
  }
}
