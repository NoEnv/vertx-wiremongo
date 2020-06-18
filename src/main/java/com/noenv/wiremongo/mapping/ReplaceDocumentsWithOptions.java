package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.UpdateOptions;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class ReplaceDocumentsWithOptions extends ReplaceDocumentsBase<ReplaceDocumentsWithOptions> {

  public static class ReplaceDocumentsWithOptionsCommand extends ReplaceDocumentsBaseCommand {

    private UpdateOptions options;

    public ReplaceDocumentsWithOptionsCommand(String collection, JsonObject query, JsonObject replace, UpdateOptions options) {
      super("replaceDocumentsWithOptions", collection, query, replace);
      this.options = options;
    }

    @Override
    public String toString() {
      return super.toString() + ", options: " + (options == null ? "null" : options.toJson().encode());
    }
  }

  private Matcher<UpdateOptions> options;

  public ReplaceDocumentsWithOptions() {
    super("replaceDocumentsWithOptions");
  }

  public ReplaceDocumentsWithOptions(JsonObject json) {
    super(json);
    options = Matcher.create(json.getJsonObject("options"), j -> new UpdateOptions((JsonObject) j), UpdateOptions::toJson);
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof ReplaceDocumentsWithOptionsCommand)) {
      return false;
    }
    ReplaceDocumentsWithOptionsCommand c = (ReplaceDocumentsWithOptionsCommand) cmd;
    return options == null || options.matches(c.options);
  }

  public ReplaceDocumentsWithOptions withOptions(UpdateOptions options) {
    return withOptions(equalTo(options));
  }

  public ReplaceDocumentsWithOptions withOptions(Matcher<UpdateOptions> options) {
    this.options = options;
    return self();
  }
}
