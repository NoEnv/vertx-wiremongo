package com.noenv.wiremongo.mapping;


import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;


@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindBatchWithOptions extends FindBatch {

  public static class FindBatchWithOptionsCommand extends FindBatchCommand {
    private final FindOptions options;

    public FindBatchWithOptionsCommand(String collection, JsonObject query, FindOptions options) {
      super("findBatchWithOptions", collection, query);
      this.options = options;
    }

    @Override
    public String toString() {
      return super.toString() + ", options: " + (options != null ? options.toJson().encode() : "null");
    }
  }

  private Matcher<FindOptions> options;

  public FindBatchWithOptions() {
    super("findBatchWithOptions");
  }

  public FindBatchWithOptions(JsonObject json) {
    super(json);
    options = Matcher.create(json.getJsonObject("options"), j -> new FindOptions((JsonObject)j), FindOptions::toJson);
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
    return options == null || options.matches(c.options);
  }

  public FindBatchWithOptions withOptions(FindOptions options) {
    return withOptions(equalTo(options));
  }

  public FindBatchWithOptions withOptions(Matcher<FindOptions> options) {
    this.options = options;
    return this;
  }

  // fluent

  @Override
  public FindBatchWithOptions inCollection(String collection) {
    return (FindBatchWithOptions) super.inCollection(collection);
  }

  @Override
  public FindBatchWithOptions inCollection(Matcher<String> collection) {
    return (FindBatchWithOptions) super.inCollection(collection);
  }

  @Override
  public FindBatchWithOptions withQuery(JsonObject query) {
    return (FindBatchWithOptions) super.withQuery(query);
  }

  @Override
  public FindBatchWithOptions withQuery(Matcher<JsonObject> query) {
    return (FindBatchWithOptions) super.withQuery(query);
  }
}
