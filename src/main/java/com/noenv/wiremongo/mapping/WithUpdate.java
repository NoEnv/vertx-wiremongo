package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

abstract class WithUpdate<T, C extends WithUpdate<T, C>> extends WithQuery<T, C> {

  public abstract static class WithUpdateCommand extends WithQueryCommand {

    private final JsonObject update;

    public WithUpdateCommand(String method, String collection, JsonObject query, JsonObject update) {
      super(method, collection, query);
      this.update = update;
    }

    @Override
    public String toString() {
      return super.toString() + ", update: " + update;
    }
  }

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
    return update == null || update.matches(c.update);
  }

  public C withUpdate(JsonObject update) {
    return withUpdate(equalTo(update));
  }

  public C withUpdate(Matcher<JsonObject> update) {
    this.update = update;
    return self();
  }
}
