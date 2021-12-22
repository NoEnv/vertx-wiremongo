package com.noenv.wiremongo.mapping.find;


import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.find.FindBatchWithOptionsCommand;
import com.noenv.wiremongo.matching.JsonMatcher;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.CreateCollectionOptions;
import io.vertx.ext.mongo.FindOptions;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;


@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindBatchWithOptions extends FindBatchBase<FindBatchWithOptionsCommand, FindBatchWithOptions> {

  private Matcher<FindOptions> options;

  public FindBatchWithOptions() {
    super("findBatchWithOptions");
  }

  public FindBatchWithOptions(JsonObject json) {
    super(json);
    options = Matcher.create(json.getJsonObject("options"), j -> new FindOptions((JsonObject) j), FindOptions::toJson);
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof FindBatchWithOptionsCommand)) {
      return false;
    }
    FindBatchWithOptionsCommand c = (FindBatchWithOptionsCommand) cmd;
    return options == null || options.matches(c.getOptions());
  }

  public FindBatchWithOptions withOptions(FindOptions options) {
    return withOptions(JsonMatcher.equalToJson(options.toJson(), FindOptions::toJson));
  }

  public FindBatchWithOptions withOptions(Matcher<FindOptions> options) {
    this.options = options;
    return self();
  }
}
