package com.noenv.wiremongo.mapping.distinct;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.distinct.DistinctWithQueryCommand;
import com.noenv.wiremongo.mapping.WithQuery;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

public class DistinctWithQuery extends WithQuery<JsonArray, DistinctWithQueryCommand, DistinctWithQuery> {

  private Matcher<String> fieldName;
  private Matcher<String> resultClassname;

  public DistinctWithQuery() {
    super("distinctWithQuery");
  }

  public DistinctWithQuery(JsonObject json) {
    super(json);
    fieldName = Matcher.create(json.getJsonObject("fieldName"));
    resultClassname = Matcher.create(json.getJsonObject("resultClassname"));
  }

  @Override
  public DistinctWithQuery returns(final JsonArray response) {
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
    if (!(cmd instanceof DistinctWithQueryCommand)) {
      return false;
    }
    DistinctWithQueryCommand c = (DistinctWithQueryCommand) cmd;
    return (fieldName == null || fieldName.matches(c.getFieldName()))
      && (resultClassname == null || resultClassname.matches(c.getResultClassname()));
  }

  public DistinctWithQuery withFieldName(String fieldName) {
    return withFieldName(equalTo(fieldName));
  }

  public DistinctWithQuery withFieldName(Matcher<String> fieldName) {
    this.fieldName = fieldName;
    return self();
  }

  public DistinctWithQuery withResultClassname(String resultClassname) {
    return withResultClassname(equalTo(resultClassname));
  }

  public DistinctWithQuery withResultClassname(Matcher<String> resultClassname) {
    this.resultClassname = resultClassname;
    return self();
  }
}
