package com.noenv.wiremongo.mapping.distinct;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.distinct.DistinctCommand;
import com.noenv.wiremongo.mapping.collection.WithCollection;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

public class Distinct extends WithCollection<JsonArray, DistinctCommand, Distinct> {

  private Matcher<String> fieldName;
  private Matcher<String> resultClassname;

  public Distinct() {
    super("distinct");
  }

  public Distinct(JsonObject json) {
    super(json);
    fieldName = Matcher.create(json.getJsonObject("fieldName"));
    resultClassname = Matcher.create(json.getJsonObject("resultClassname"));
  }

  @Override
  public Distinct returns(final JsonArray response) {
    return stub(c -> null == response ? null : response.copy());
  }

  @Override
  protected JsonArray parseResponse(Object jsonValue) {
    return (JsonArray) jsonValue;
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof DistinctCommand)) {
      return false;
    }
    DistinctCommand c = (DistinctCommand) cmd;
    return (fieldName == null || fieldName.matches(c.getFieldName()))
      && (resultClassname == null || resultClassname.matches(c.getResultClassname()));
  }

  public Distinct withFieldName(String fieldName) {
    return withFieldName(equalTo(fieldName));
  }

  public Distinct withFieldName(Matcher<String> fieldName) {
    this.fieldName = fieldName;
    return self();
  }

  public Distinct withResultClassname(String resultClassname) {
    return withResultClassname(equalTo(resultClassname));
  }

  public Distinct withResultClassname(Matcher<String> resultClassname) {
    this.resultClassname = resultClassname;
    return self();
  }
}
