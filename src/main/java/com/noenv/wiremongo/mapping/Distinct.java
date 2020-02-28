package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

public class Distinct extends WithCollection<JsonArray> {

  public static class DistinctCommand extends WithCollectionCommand {

    private final String fieldName;
    private final String resultClassname;

    public DistinctCommand(String collection, String fieldName, String resultClassname) {
      super("distinct", collection);
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

  public Distinct() {
    super("distinct");
  }

  public Distinct(JsonObject json) {
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
    if (!(cmd instanceof DistinctCommand)) {
      return false;
    }
    DistinctCommand c = (DistinctCommand) cmd;
    return (fieldName == null || fieldName.matches(c.fieldName))
      && (resultClassname == null || resultClassname.matches(c.resultClassname));
  }

  public Distinct withFieldName(String fieldName) {
    return withFieldName(equalTo(fieldName));
  }

  public Distinct withFieldName(Matcher<String> fieldName) {
    this.fieldName = fieldName;
    return this;
  }

  public Distinct withResultClassname(String resultClassname) {
    return withResultClassname(equalTo(resultClassname));
  }

  public Distinct withResultClassname(Matcher<String> resultClassname) {
    this.resultClassname = resultClassname;
    return this;
  }

  // fluent

  @Override
  public Distinct inCollection(String collection) {
    return (Distinct) super.inCollection(collection);
  }

  @Override
  public Distinct inCollection(Matcher<String> collection) {
    return (Distinct) super.inCollection(collection);
  }
}
