package com.noenv.wiremongo.mapping.distinct;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.distinct.DistinctBatchWithQueryCommand;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.DistinctOptions;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class DistinctBatchWithQuery extends DistinctBatchBase<DistinctBatchWithQueryCommand, DistinctBatchWithQuery> {

  private Matcher<JsonObject> query;
  private Matcher<Integer> batchSize;

  public DistinctBatchWithQuery() {
    super("distinctBatchWithQuery");
  }

  public DistinctBatchWithQuery(String method) {
    super(method);
  }

  public DistinctBatchWithQuery(JsonObject json) {
    super(json);
    this.query = Matcher.create(json.getJsonObject("query"));
    this.batchSize = Matcher.create(json.getJsonObject("batchSize"));
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof DistinctBatchWithQueryCommand)) {
      return false;
    }
    DistinctBatchWithQueryCommand c = (DistinctBatchWithQueryCommand) cmd;
    return (query == null || query.matches(c.getQuery()))
      && (batchSize == null || batchSize.matches(c.getBatchSize()));
  }

  public DistinctBatchWithQuery withQuery(JsonObject query) {
    return withQuery(equalTo(query));
  }

  public DistinctBatchWithQuery withQuery(Matcher<JsonObject> query) {
    this.query = query;
    return self();
  }

}
