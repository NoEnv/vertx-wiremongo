package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.find.FindOneAndDeleteWithOptionsCommand;
import com.noenv.wiremongo.matching.JsonMatcher;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindOneAndDeleteWithOptions extends FindOneAndDeleteBase<FindOneAndDeleteWithOptionsCommand, FindOneAndDeleteWithOptions> {

  private Matcher<FindOptions> options;

  public FindOneAndDeleteWithOptions() {
    this("findOneAndDeleteWithOptions");
  }

  public FindOneAndDeleteWithOptions(String method) {
    super(method);
  }

  public FindOneAndDeleteWithOptions(JsonObject json) {
    super(json);
    this.options = Matcher.create(json.getJsonObject("options"), j -> new FindOptions((JsonObject) j), FindOptions::toJson);
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof FindOneAndDeleteWithOptionsCommand)) {
      return false;
    }
    FindOneAndDeleteWithOptionsCommand c = (FindOneAndDeleteWithOptionsCommand) cmd;
    return options == null || options.matches(c.getOptions());
  }

  public FindOneAndDeleteWithOptions withOptions(FindOptions options) {
    return withOptions(JsonMatcher.equalToJson(options.toJson(), FindOptions::toJson));
  }

  public FindOneAndDeleteWithOptions withOptions(Matcher<FindOptions> options) {
    this.options = options;
    return self();
  }
}
