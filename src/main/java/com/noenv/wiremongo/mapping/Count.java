package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

public class Count extends WithQuery<Long> {

  public static class CountCommand extends WithQueryCommand {
    public CountCommand(String collection, JsonObject query) {
      super("count", collection, query);
    }
  }

  public Count() {
    super("count");
  }

  public Count(JsonObject json) {
    super(json);
  }

  @Override
  protected Long parseResponse(Object jsonValue) {
    return ((Number)jsonValue).longValue();
  }

  // fluent

  @Override
  public Count inCollection(String collection) {
    return (Count) super.inCollection(collection);
  }

  @Override
  public Count inCollection(Matcher<String> collection) {
    return (Count) super.inCollection(collection);
  }

  @Override
  public Count withQuery(JsonObject query){
    return (Count) super.withQuery(query);
  }

  @Override
  public Count withQuery(Matcher<JsonObject> query) {
    return (Count) super.withQuery(query);
  }
}
