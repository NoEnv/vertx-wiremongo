package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.IndexOptions;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

public class CreateIndexWithOptions extends CreateIndexBase<CreateIndexWithOptions> {

  public static class CreateIndexWithOptionsCommand extends CreateIndexBaseCommand {

    private final IndexOptions options;

    public CreateIndexWithOptionsCommand(String collection, JsonObject key, IndexOptions options) {
      super("createIndexWithOptions", collection, key);
      this.options = options;
    }

    @Override
    public String toString() {
      return super.toString() + ", options: " + (options != null ? options.toJson().encode() : "null");
    }
  }

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
    return options == null || options.matches(c.options);
  }

  public CreateIndexWithOptions withOptions(IndexOptions options) {
    return withOptions(equalTo(options));
  }

  public CreateIndexWithOptions withOptions(Matcher<IndexOptions> options) {
    this.options = options;
    return self();
  }
}
