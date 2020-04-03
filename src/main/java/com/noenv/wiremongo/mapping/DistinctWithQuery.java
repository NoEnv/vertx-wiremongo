package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

public class DistinctWithQuery extends WithQuery<JsonArray> {

  public static class DistinctWithQueryCommand extends WithQueryCommand {

    private final String fieldName;
    private final String resultClassname;

    public DistinctWithQueryCommand(String collection, String fieldName, String resultClassname, JsonObject query) {
      super("distinctWithQuery", collection, query);
      this.fieldName = fieldName;
      this.resultClassname = resultClassname;
    }

    @Override
    public String toString() {
      return super.toString() + ", fieldName: " + fieldName + ", resultClassname: " + resultClassname;
    }
  }

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
    return (fieldName == null || fieldName.matches(c.fieldName))
      && (resultClassname == null || resultClassname.matches(c.resultClassname));
  }

  public DistinctWithQuery withFieldName(String fieldName) {
    return withFieldName(equalTo(fieldName));
  }

  public DistinctWithQuery withFieldName(Matcher<String> fieldName) {
    this.fieldName = fieldName;
    return this;
  }

  public DistinctWithQuery withResultClassname(String resultClassname) {
    return withResultClassname(equalTo(resultClassname));
  }

  public DistinctWithQuery withResultClassname(Matcher<String> resultClassname) {
    this.resultClassname = resultClassname;
    return this;
  }

  // fluent

  @Override
  public DistinctWithQuery priority(int priority) {
    return (DistinctWithQuery) super.priority(priority);
  }

  @Override
  public DistinctWithQuery withQuery(JsonObject query) {
    return (DistinctWithQuery) super.withQuery(query);
  }

  @Override
  public DistinctWithQuery withQuery(Matcher<JsonObject> query) {
    return (DistinctWithQuery) super.withQuery(query);
  }

  @Override
  public DistinctWithQuery inCollection(String collection) {
    return (DistinctWithQuery) super.inCollection(collection);
  }

  @Override
  public DistinctWithQuery inCollection(Matcher<String> collection) {
    return (DistinctWithQuery) super.inCollection(collection);
  }
}
