package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

public class FindOne extends WithQuery<JsonObject, FindOne> {

  public static class FindOneCommand extends WithQueryCommand {

    private final JsonObject fields;

    public FindOneCommand(String collection, JsonObject query, JsonObject fields) {
      super("findOne", collection, query);
      this.fields = fields;
    }

    @Override
    public String toString() {
      return super.toString() + ", fields: " + fields;
    }
  }

  private Matcher<JsonObject> fields;

  public FindOne() {
    super("findOne");
  }

  public FindOne(JsonObject json) {
    super(json);
    fields = Matcher.create(json.getJsonObject("fields"));
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof FindOneCommand)) {
      return false;
    }
    FindOneCommand c = (FindOneCommand) cmd;
    return fields == null || fields.matches(c.fields);
  }

  @Override
  protected JsonObject parseResponse(Object jsonValue) {
    return (JsonObject) jsonValue;
  }

  public FindOne withFields(JsonObject fields) {
    return withFields(equalTo(fields));
  }

  public FindOne withFields(Matcher<JsonObject> fields) {
    this.fields = fields;
    return this;
  }
}
