package com.noenv.wiremongo.mapping.update;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.update.WithUpdateCommand;
import com.noenv.wiremongo.mapping.WithQuery;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

public abstract class WithUpdate<V, T, U extends WithUpdateCommand<V>, C extends WithUpdate<V, T, U, C>> extends WithQuery<T, U, C> {

  private Matcher<V> update;

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
    WithUpdateCommand<V> c = (WithUpdateCommand<V>) cmd;
    return update == null || update.matches(c.getUpdate());
  }

  public C withUpdate(V update) {
    return withUpdate(equalTo(update));
  }

  public C withUpdate(Matcher<V> update) {
    this.update = update;
    return self();
  }
}
