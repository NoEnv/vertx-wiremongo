package com.noenv.wiremongo.mapping.stream;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.stream.WithStreamQueryCommand;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;


public abstract class WithStreamQuery<U extends WithStreamQueryCommand, C extends WithStreamQuery<U, C>> extends WithStream<U, C> {

  private Matcher<JsonObject> query;

  public WithStreamQuery(String method) {
    super(method);
  }

  public WithStreamQuery(JsonObject json) {
    super(json);
    query = Matcher.create(json.getJsonObject("query"));
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof WithStreamQueryCommand)) {
      return false;
    }
    WithStreamQueryCommand c = (WithStreamQueryCommand) cmd;
    return query == null || query.matches(c.getQuery());
  }

  public C withQuery(JsonObject query) {
    return withQuery(equalTo(query));
  }

  public C withQuery(Matcher<JsonObject> query) {
    this.query = query;
    return self();
  }
}
