package com.noenv.wiremongo.mapping.remove;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.remove.RemoveDocumentsWithOptionsCommand;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.WriteOption;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class RemoveDocumentsWithOptions extends RemoveDocumentsBase<RemoveDocumentsWithOptionsCommand, RemoveDocumentsWithOptions> {

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
    return options == null || options.matches(c.getOptions());
  }

  public RemoveDocumentsWithOptions withOptions(WriteOption options) {
    return withOptions(equalTo(options));
  }

  public RemoveDocumentsWithOptions withOptions(Matcher<WriteOption> options) {
    this.options = options;
    return self();
  }
}
