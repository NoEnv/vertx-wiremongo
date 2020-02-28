package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class DistinctBatch extends WithStream {

  public static class DistinctBatchCommand extends WithStreamCommand {

    private final String fieldName;
    private final String resultClassname;

    public DistinctBatchCommand(String collection, String fieldName, String resultClassname) {
      this("distinctBatch", collection, fieldName, resultClassname);
    }

    public DistinctBatchCommand(String method, String collection, String fieldName, String resultClassname) {
      super(method, collection);
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

  public DistinctBatch() {
    super("distinctBatch");
  }

  public DistinctBatch(String method) {
    super(method);
  }

  public DistinctBatch(JsonObject json) {
    super(json);
    fieldName = Matcher.create(json.getJsonObject("fieldName"));
    resultClassname = Matcher.create(json.getJsonObject("resultClassname"));
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof DistinctBatchCommand)) {
      return false;
    }
    DistinctBatchCommand c = (DistinctBatchCommand) cmd;
    return (fieldName == null || fieldName.matches(c.fieldName))
      && (resultClassname == null || resultClassname.matches(c.resultClassname));
  }

  public DistinctBatch withFieldName(String fieldName) {
    return withFieldName(equalTo(fieldName));
  }

  public DistinctBatch withFieldName(Matcher<String> fieldName) {
    this.fieldName = fieldName;
    return this;
  }

  public DistinctBatch withResultClassname(String resultClassname) {
    return withResultClassname(equalTo(resultClassname));
  }

  public DistinctBatch withResultClassname(Matcher<String> resultClassname) {
    this.resultClassname = resultClassname;
    return this;
  }

  // fluent

  @Override
  public DistinctBatch inCollection(String collection) {
    return (DistinctBatch) super.inCollection(collection);
  }

  @Override
  public DistinctBatch inCollection(Matcher<String> collection) {
    return (DistinctBatch) super.inCollection(collection);
  }
}
