package com.noenv.wiremongo.mapping.update;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.update.WithUpdateCommand;
import com.noenv.wiremongo.mapping.WithQuery;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

public abstract class WithUpdate<T, U extends WithUpdateCommand, C extends WithUpdate<T, U, C>> extends WithQuery<T, U, C> {

  private Matcher<JsonObject> update;

  public WithUpdate(String method) {
    super(method);
  }

  public WithUpdate(JsonObject json) {
    super(json);
    update = Matcher.create(json.getJsonObject("update"));
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof WithUpdateCommand)) {
      return false;
    }
    WithUpdateCommand c = (WithUpdateCommand) cmd;
    return update == null || update.matches(c.getUpdate());
  }

  public C withUpdate(JsonObject update) {
    return withUpdate(equalTo(update));
  }

  public C withUpdate(Matcher<JsonObject> update) {
    this.update = update;
    return self();
  }
}
