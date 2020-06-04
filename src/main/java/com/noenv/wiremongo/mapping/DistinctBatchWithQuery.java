package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class DistinctBatchWithQuery extends DistinctBatchBase<DistinctBatchWithQuery> {

  public static class DistinctBatchWithQueryCommand extends DistinctBatchBaseCommand {

    private final JsonObject query;
    private final int batchSize;

    public DistinctBatchWithQueryCommand(String collection, String fieldName, String resultClassname, JsonObject query) {
      this(collection, fieldName, resultClassname, query, 20);
    }

    public DistinctBatchWithQueryCommand(String collection, String fieldName, String resultClassname, JsonObject query, int batchSize) {
      super(collection, "distinctBatchWithQuery", fieldName, resultClassname);
      this.query = query;
      this.batchSize = batchSize;
    }

    @Override
    public String toString() {
      return super.toString() + ", query: " + (query != null ? query.encode() : "null");
    }
  }

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
    return (query == null || query.matches(c.query))
      && (batchSize == null || batchSize.matches(c.batchSize));
  }

  public DistinctBatchWithQuery withQuery(JsonObject query) {
    return withQuery(equalTo(query));
  }

  public DistinctBatchWithQuery withQuery(Matcher<JsonObject> query) {
    this.query = query;
    return this;
  }
}
