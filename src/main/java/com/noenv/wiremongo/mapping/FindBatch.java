package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindBatch extends WithStreamQuery {

  public static class FindBatchCommand extends WithStreamQueryCommand {
    public FindBatchCommand(String collection, JsonObject query) {
      this("findBatch", collection, query);
    }
    public FindBatchCommand(String method, String collection, JsonObject query) {
      super(method, collection, query);
    }
  }

  public FindBatch() {
    this("findBatch");
  }

  public FindBatch(String method) {
    super(method);
  }

  public FindBatch(JsonObject json) {
    super(json);
  }

  // fluent

  @Override
  public FindBatch inCollection(String collection) {
    return (FindBatch) super.inCollection(collection);
  }

  @Override
  public FindBatch inCollection(Matcher<String> collection) {
    return (FindBatch) super.inCollection(collection);
  }

  @Override
  public FindBatch withQuery(JsonObject query) {
    return (FindBatch) super.withQuery(query);
  }

  @Override
  public FindBatch withQuery(Matcher<JsonObject> query) {
    return (FindBatch) super.withQuery(query);
  }
}
