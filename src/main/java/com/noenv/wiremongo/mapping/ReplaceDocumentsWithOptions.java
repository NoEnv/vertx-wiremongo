package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.UpdateOptions;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class ReplaceDocumentsWithOptions extends ReplaceDocuments {

  public static class ReplaceDocumentsWithOptionsCommand extends ReplaceDocumentsCommand {

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
    return this;
  }

  // fluent

  @Override
  public ReplaceDocumentsWithOptions priority(int priority) {
    return (ReplaceDocumentsWithOptions) super.priority(priority);
  }

  @Override
  public ReplaceDocumentsWithOptions inCollection(String collection) {
    return (ReplaceDocumentsWithOptions) super.inCollection(collection);
  }

  @Override
  public ReplaceDocumentsWithOptions inCollection(Matcher<String> collection) {
    return (ReplaceDocumentsWithOptions) super.inCollection(collection);
  }

  @Override
  public ReplaceDocumentsWithOptions withQuery(JsonObject query) {
    return (ReplaceDocumentsWithOptions) super.withQuery(query);
  }

  @Override
  public ReplaceDocumentsWithOptions withQuery(Matcher<JsonObject> query) {
    return (ReplaceDocumentsWithOptions) super.withQuery(query);
  }

  @Override
  public ReplaceDocumentsWithOptions withReplace(JsonObject replace) {
    return (ReplaceDocumentsWithOptions) super.withReplace(replace);
  }

  @Override
  public ReplaceDocumentsWithOptions withReplace(Matcher<JsonObject> replace) {
    return (ReplaceDocumentsWithOptions) super.withReplace(replace);
  }
}
