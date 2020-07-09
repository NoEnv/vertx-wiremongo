package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.find.FindOneCommand;
import com.noenv.wiremongo.mapping.WithQuery;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

public class FindOne extends WithQuery<JsonObject, FindOneCommand, FindOne> {

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
    return fields == null || fields.matches(c.getFields());
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
    return self();
  }
}
