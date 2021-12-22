package com.noenv.wiremongo.mapping.collection;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.collection.CreateCollectionWithOptionsCommand;
import com.noenv.wiremongo.matching.JsonMatcher;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.CreateCollectionOptions;

public class CreateCollectionWithOptions extends WithCollection<Void, CreateCollectionWithOptionsCommand, CreateCollectionWithOptions> {

  private Matcher<CreateCollectionOptions> options;

  public CreateCollectionWithOptions() {
    super("createCollectionWithOptions");
  }

  public CreateCollectionWithOptions(JsonObject json) {
    super(json);
    options = Matcher.create(json.getJsonObject("options"), j -> new CreateCollectionOptions((JsonObject) j), CreateCollectionOptions::toJson);
  }

  @Override
  protected Void parseResponse(Object jsonValue) {
    return null;
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof CreateCollectionWithOptionsCommand)) {
      return false;
    }
    CreateCollectionWithOptionsCommand c = (CreateCollectionWithOptionsCommand) cmd;
    return options == null || options.matches(c.getOptions());
  }

  public CreateCollectionWithOptions withOptions(CreateCollectionOptions options) {
    return withOptions(JsonMatcher.equalToJson(options.toJson(), CreateCollectionOptions::toJson));
  }

  public CreateCollectionWithOptions withOptions(Matcher<CreateCollectionOptions> options) {
    this.options = options;
    return self();
  }
}
