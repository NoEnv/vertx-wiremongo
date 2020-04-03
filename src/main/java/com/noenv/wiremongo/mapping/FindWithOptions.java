package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindWithOptions extends Find {

  public static class FindWithOptionsCommand extends FindCommand {

    private final FindOptions options;

    public FindWithOptionsCommand(String collection, JsonObject query, FindOptions options) {
      super("findWithOptions", collection, query);
      this.options = options;
    }

    @Override
    public String toString() {
      return super.toString() + ", options: " + (options != null ? options.toJson().encode() : "null");
    }
  }

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
    return options == null || options.matches(c.options);
  }

  public FindWithOptions withOptions(FindOptions options) {
    return withOptions(equalTo(options));
  }

  public FindWithOptions withOptions(Matcher<FindOptions> options) {
    this.options = options;
    return this;
  }

  // fluent

  @Override
  public FindWithOptions priority(int priority) {
    return (FindWithOptions) super.priority(priority);
  }

  @Override
  public FindWithOptions inCollection(Matcher<String> collection) {
    return (FindWithOptions) super.inCollection(collection);
  }

  @Override
  public FindWithOptions inCollection(String collection) {
    return (FindWithOptions) super.inCollection(collection);
  }

  @Override
  public FindWithOptions withQuery(Matcher<JsonObject> query) {
    return (FindWithOptions) super.withQuery(query);
  }

  @Override
  public FindWithOptions withQuery(JsonObject query) {
    return (FindWithOptions) super.withQuery(query);
  }
}
