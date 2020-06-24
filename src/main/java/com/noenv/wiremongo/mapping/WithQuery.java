package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.WithQueryCommand;
import com.noenv.wiremongo.mapping.collection.WithCollection;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

public abstract class WithQuery<T, U extends WithQueryCommand, C extends WithQuery<T, U, C>> extends WithCollection<T, U, C> {

  private Matcher<JsonObject> query;

  public WithQuery(String method) {
    super(method);
  }

  public WithQuery(JsonObject json) {
    super(json);
    query = Matcher.create(json.getJsonObject("query"));
  }

  public C withQuery(JsonObject query) {
    return withQuery(equalTo(query));
  }

  public C withQuery(Matcher<JsonObject> query) {
    this.query = query;
    return self();
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof WithQueryCommand)) {
      return false;
    }
    WithQueryCommand c = (WithQueryCommand) cmd;
    return query == null || query.matches(c.getQuery());
  }
}
