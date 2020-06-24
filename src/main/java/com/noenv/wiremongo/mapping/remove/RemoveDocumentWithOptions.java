package com.noenv.wiremongo.mapping.remove;

import com.noenv.wiremongo.mapping.Command;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.WriteOption;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class RemoveDocumentWithOptions extends RemoveDocumentBase<RemoveDocumentWithOptions> {

  public static class RemoveDocumentWithOptionsCommand extends RemoveDocumentBaseCommand {

    private WriteOption options;

    public RemoveDocumentWithOptionsCommand(String collection, JsonObject query, WriteOption options) {
      super("removeDocumentWithOptions", collection, query);
      this.options = options;
    }

    @Override
    public String toString() {
      return super.toString() + ", options: " + (options != null ? options.name() : "null");
    }
  }

  private Matcher<WriteOption> options;

  public RemoveDocumentWithOptions() {
    super("removeDocumentWithOptions");
  }

  public RemoveDocumentWithOptions(JsonObject json) {
    super(json);
    options = Matcher.create(json.getJsonObject("options"), j -> WriteOption.valueOf((String) j), Enum::name);
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof RemoveDocumentWithOptionsCommand)) {
      return false;
    }
    RemoveDocumentWithOptionsCommand c = (RemoveDocumentWithOptionsCommand) cmd;
    return options == null || options.matches(c.options);
  }

  public RemoveDocumentWithOptions withOptions(WriteOption options) {
    return withOptions(equalTo(options));
  }

  public RemoveDocumentWithOptions withOptions(Matcher<WriteOption> options) {
    this.options = options;
    return self();
  }
}
