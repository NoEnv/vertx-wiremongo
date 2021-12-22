package com.noenv.wiremongo.mapping.replace;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.replace.ReplaceDocumentsWithOptionsCommand;
import com.noenv.wiremongo.matching.JsonMatcher;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.UpdateOptions;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class ReplaceDocumentsWithOptions extends ReplaceDocumentsBase<ReplaceDocumentsWithOptionsCommand, ReplaceDocumentsWithOptions> {

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
    return options == null || options.matches(c.getOptions());
  }

  public ReplaceDocumentsWithOptions withOptions(UpdateOptions options) {
    return withOptions(JsonMatcher.equalToJson(options.toJson(), UpdateOptions::toJson));
  }

  public ReplaceDocumentsWithOptions withOptions(Matcher<UpdateOptions> options) {
    this.options = options;
    return self();
  }
}
