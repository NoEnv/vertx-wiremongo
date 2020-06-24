package com.noenv.wiremongo.mapping.distinct;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.distinct.DistinctBatchBaseCommand;
import com.noenv.wiremongo.mapping.stream.WithStream;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public abstract class DistinctBatchBase<U extends DistinctBatchBaseCommand, C extends DistinctBatchBase<U, C>> extends WithStream<U, C> {

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
    if (!(cmd instanceof DistinctBatchBaseCommand)) {
      return false;
    }
    DistinctBatchBaseCommand c = (DistinctBatchBaseCommand) cmd;
    return (fieldName == null || fieldName.matches(c.getFieldName()))
      && (resultClassname == null || resultClassname.matches(c.getResultClassname()));
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
