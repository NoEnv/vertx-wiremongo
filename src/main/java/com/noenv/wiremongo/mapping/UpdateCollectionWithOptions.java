package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientUpdateResult;
import io.vertx.ext.mongo.UpdateOptions;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class UpdateCollectionWithOptions extends UpdateCollectionBase<UpdateCollectionWithOptions> {

  public static class UpdateCollectionWithOptionsCommand extends UpdateCollectionBaseCommand {

    private final UpdateOptions options;

    public UpdateCollectionWithOptionsCommand(String collection, JsonObject query, JsonObject update, UpdateOptions options) {
      super("updateCollectionWithOptions", collection, query, update);
      this.options = options;
    }

    @Override
    public String toString() {
      return super.toString() + ", options: " + (options != null ? options.toJson().encode() : "null");
    }
  }

  private Matcher<UpdateOptions> options;

  public UpdateCollectionWithOptions() {
    super("updateCollectionWithOptions");
  }

  public UpdateCollectionWithOptions(JsonObject json) {
    super(json);
    options = Matcher.create(json.getJsonObject("options"), j -> new UpdateOptions((JsonObject) j), UpdateOptions::toJson);
  }

  @Override
  protected MongoClientUpdateResult parseResponse(Object jsonValue) {
    return new MongoClientUpdateResult((JsonObject) jsonValue);
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof UpdateCollectionWithOptionsCommand)) {
      return false;
    }
    UpdateCollectionWithOptionsCommand c = (UpdateCollectionWithOptionsCommand) cmd;
    return options == null || options.matches(c.options);
  }

  public UpdateCollectionWithOptions withOptions(UpdateOptions options) {
    return withOptions(equalTo(options));
  }

  public UpdateCollectionWithOptions withOptions(Matcher<UpdateOptions> options) {
    this.options = options;
    return self();
  }
}
