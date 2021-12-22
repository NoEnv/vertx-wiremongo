package com.noenv.wiremongo.mapping.index;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.index.CreateIndexWithOptionsCommand;
import com.noenv.wiremongo.matching.JsonMatcher;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.IndexOptions;

public class CreateIndexWithOptions extends CreateIndexBase<CreateIndexWithOptionsCommand, CreateIndexWithOptions> {

  private Matcher<IndexOptions> options;

  public CreateIndexWithOptions() {
    super("createIndexWithOptions");
  }

  public CreateIndexWithOptions(JsonObject json) {
    super(json);
    options = Matcher.create(json.getJsonObject("options"), j -> new IndexOptions((JsonObject) j), IndexOptions::toJson);
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof CreateIndexWithOptionsCommand)) {
      return false;
    }
    CreateIndexWithOptionsCommand c = (CreateIndexWithOptionsCommand) cmd;
    return options == null || options.matches(c.getOptions());
  }

  public CreateIndexWithOptions withOptions(IndexOptions options) {
    return withOptions(JsonMatcher.equalToJson(options.toJson(), IndexOptions::toJson));
  }

  public CreateIndexWithOptions withOptions(Matcher<IndexOptions> options) {
    this.options = options;
    return self();
  }
}
