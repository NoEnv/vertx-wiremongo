package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public abstract class DistinctBatchBase<C extends DistinctBatchBase<C>> extends WithStream<C> {

  public static class DistinctBatchBaseCommand extends WithStreamCommand {

    private final String fieldName;
    private final String resultClassname;

    public DistinctBatchBaseCommand(String collection, String fieldName, String resultClassname) {
      this("distinctBatch", collection, fieldName, resultClassname);
    }

    public DistinctBatchBaseCommand(String method, String collection, String fieldName, String resultClassname) {
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

  public DistinctBatchBase(String method) {
    super(method);
  }

  public DistinctBatchBase(JsonObject json) {
    super(json);
    fieldName = Matcher.create(json.getJsonObject("fieldName"));
    resultClassname = Matcher.create(json.getJsonObject("resultClassname"));
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof DistinctBatchBase.DistinctBatchBaseCommand)) {
      return false;
    }
    DistinctBatchBaseCommand c = (DistinctBatchBaseCommand) cmd;
    return (fieldName == null || fieldName.matches(c.fieldName))
      && (resultClassname == null || resultClassname.matches(c.resultClassname));
  }

  public C withFieldName(String fieldName) {
    return withFieldName(equalTo(fieldName));
  }

  public C withFieldName(Matcher<String> fieldName) {
    this.fieldName = fieldName;
    return self();
  }

  public C withResultClassname(String resultClassname) {
    return withResultClassname(equalTo(resultClassname));
  }

  public C withResultClassname(Matcher<String> resultClassname) {
    this.resultClassname = resultClassname;
    return self();
  }
}
