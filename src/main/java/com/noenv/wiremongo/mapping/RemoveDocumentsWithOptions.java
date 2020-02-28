package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.WriteOption;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class RemoveDocumentsWithOptions extends RemoveDocuments {

  public static class RemoveDocumentsWithOptionsCommand extends RemoveDocuments.RemoveDocumentsCommand {

    private WriteOption options;

    public RemoveDocumentsWithOptionsCommand(String collection, JsonObject query, WriteOption options) {
      super("removeDocumentsWithOptions", collection, query);
      this.options = options;
    }

    @Override
    public String toString() {
      return super.toString() + ", options: " + (options != null ? options.name() : "null");
    }
  }

  private Matcher<WriteOption> options;

  public RemoveDocumentsWithOptions() {
    super("removeDocumentsWithOptions");
  }

  public RemoveDocumentsWithOptions(JsonObject json) {
    super(json);
    options = Matcher.create(json.getJsonObject("options"), j -> WriteOption.valueOf((String) j), Enum::name);
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof RemoveDocumentsWithOptionsCommand)) {
      return false;
    }
    RemoveDocumentsWithOptionsCommand c = (RemoveDocumentsWithOptionsCommand) cmd;
    return options == null || options.matches(c.options);
  }

  public RemoveDocumentsWithOptions withOptions(WriteOption options) {
    return withOptions(equalTo(options));
  }

  public RemoveDocumentsWithOptions withOptions(Matcher<WriteOption> options) {
    this.options = options;
    return this;
  }

  // fluent

  @Override
  public RemoveDocumentsWithOptions inCollection(String collection) {
    return (RemoveDocumentsWithOptions) super.inCollection(collection);
  }

  @Override
  public RemoveDocumentsWithOptions inCollection(Matcher<String> collection) {
    return (RemoveDocumentsWithOptions) super.inCollection(collection);
  }

  @Override
  public RemoveDocumentsWithOptions withQuery(JsonObject query) {
    return (RemoveDocumentsWithOptions) super.withQuery(query);
  }

  @Override
  public RemoveDocumentsWithOptions withQuery(Matcher<JsonObject> query) {
    return (RemoveDocumentsWithOptions) super.withQuery(query);
  }
}
