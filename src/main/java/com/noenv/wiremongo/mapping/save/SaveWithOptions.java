package com.noenv.wiremongo.mapping.save;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.save.SaveWithOptionsCommand;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.WriteOption;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class SaveWithOptions extends SaveBase<SaveWithOptionsCommand, SaveWithOptions> {

  private Matcher<WriteOption> options;

  public SaveWithOptions() {
    super("saveWithOptions");
  }

  public SaveWithOptions(JsonObject json) {
    super(json);
    options = Matcher.create(json.getJsonObject("options"), j -> WriteOption.valueOf((String) j), Enum::name);
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof SaveWithOptionsCommand)) {
      return false;
    }
    SaveWithOptionsCommand c = (SaveWithOptionsCommand) cmd;
    return options == null || options.matches(c.getOptions());
  }

  public SaveWithOptions withOptions(WriteOption options) {
    return withOptions(equalTo(options));
  }

  public SaveWithOptions withOptions(Matcher<WriteOption> options) {
    this.options = options;
    return self();
  }
}
