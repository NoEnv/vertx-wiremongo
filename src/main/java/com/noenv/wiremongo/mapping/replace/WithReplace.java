package com.noenv.wiremongo.mapping.replace;

import com.noenv.wiremongo.mapping.Command;
import com.noenv.wiremongo.mapping.WithQuery;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

public abstract class WithReplace<T, C extends WithReplace<T, C>> extends WithQuery<T, C> {

  public abstract static class WithReplaceCommand extends WithQueryCommand {

    private final JsonObject replace;

    public WithReplaceCommand(String method, String collection, JsonObject query, JsonObject replace) {
      super(method, collection, query);
      this.replace = replace;
    }

    @Override
    public String toString() {
      return super.toString() + ", replace: " + replace;
    }
  }

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
    return replace == null || replace.matches(c.replace);
  }

  public C withReplace(JsonObject replace) {
    return withReplace(equalTo(replace));
  }

  public C withReplace(Matcher<JsonObject> replace) {
    this.replace = replace;
    return self();
  }
}
