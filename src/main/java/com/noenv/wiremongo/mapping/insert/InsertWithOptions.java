package com.noenv.wiremongo.mapping.insert;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.insert.InsertWithOptionsCommand;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.WriteOption;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class InsertWithOptions extends InsertBase<InsertWithOptionsCommand, InsertWithOptions> {

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
    return options == null || options.matches(c.getOptions());
  }

  public InsertWithOptions withOptions(WriteOption options) {
    return withOptions(equalTo(options));
  }

  public InsertWithOptions withOptions(Matcher<WriteOption> options) {
    this.options = options;
    return self();
  }
}
