package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.WriteOption;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class InsertWithOptions extends Insert {

  public static class InsertWithOptionsCommand extends InsertCommand {

    private WriteOption options;

    public InsertWithOptionsCommand(String collection, JsonObject document, WriteOption options) {
      super("insertWithOptions", collection, document);
      this.options = options;
    }

    @Override
    public String toString() {
      return super.toString() + ", options: " + (options != null ? options.name() : "null");
    }
  }

  private Matcher<WriteOption> options;

  public InsertWithOptions() {
    super("insertWithOptions");
  }

  public InsertWithOptions(JsonObject json) {
    super(json);
    options = Matcher.create(json.getJsonObject("options"), j -> WriteOption.valueOf((String) j), Enum::name);
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof InsertWithOptionsCommand)) {
      return false;
    }
    InsertWithOptionsCommand c = (InsertWithOptionsCommand) cmd;
    return options == null || options.matches(c.options);
  }

  public InsertWithOptions withOptions(WriteOption options) {
    return withOptions(equalTo(options));
  }

  public InsertWithOptions withOptions(Matcher<WriteOption> options) {
    this.options = options;
    return this;
  }

  // fluent

  @Override
  public InsertWithOptions inCollection(String collection) {
    return (InsertWithOptions) super.inCollection(collection);
  }

  @Override
  public InsertWithOptions inCollection(Matcher<String> collection) {
    return (InsertWithOptions) super.inCollection(collection);
  }

  @Override
  public InsertWithOptions withDocument(JsonObject document) {
    return (InsertWithOptions) super.withDocument(document);
  }

  @Override
  public InsertWithOptions withDocument(Matcher<JsonObject> document) {
    return (InsertWithOptions) super.withDocument(document);
  }
}
