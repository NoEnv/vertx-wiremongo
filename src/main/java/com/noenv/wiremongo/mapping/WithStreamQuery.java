package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;


abstract class WithStreamQuery extends WithStream {

  public abstract static class WithStreamQueryCommand extends WithStreamCommand {

    private final JsonObject query;

    public WithStreamQueryCommand(String method, String collection, JsonObject query) {
      super(method, collection);
      this.query = query;
    }

    @Override
    public String toString() {
      return super.toString() + ", query: " + query;
    }
  }

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
    return query == null || query.matches(c.query);
  }

  public WithStreamQuery withQuery(JsonObject query) {
    return withQuery(equalTo(query));
  }

  public WithStreamQuery withQuery(Matcher<JsonObject> query) {
    this.query = query;
    return this;
  }
}
