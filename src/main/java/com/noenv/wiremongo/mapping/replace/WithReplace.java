package com.noenv.wiremongo.mapping.replace;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.replace.WithReplaceCommand;
import com.noenv.wiremongo.mapping.WithQuery;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

public abstract class WithReplace<T, U extends WithReplaceCommand, C extends WithReplace<T, U, C>> extends WithQuery<T, U, C> {

  private Matcher<JsonObject> replace;

  public WithReplace(String method) {
    super(method);
  }

  public WithReplace(JsonObject json) {
    super(json);
    replace = Matcher.create(json.getJsonObject("replace"));
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof WithReplaceCommand)) {
      return false;
    }
    WithReplaceCommand c = (WithReplaceCommand) cmd;
    return replace == null || replace.matches(c.getReplace());
  }

  public C withReplace(JsonObject replace) {
    return withReplace(equalTo(replace));
  }

  public C withReplace(Matcher<JsonObject> replace) {
    this.replace = replace;
    return self();
  }
}
