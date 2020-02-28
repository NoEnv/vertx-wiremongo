package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class ListIndexes extends WithCollection<JsonArray> {

  public static class ListIndexesCommand extends WithCollectionCommand {
    public ListIndexesCommand(String collection) {
      super("listIndexes", collection);
    }
  }

  public ListIndexes() {
    super("listIndexes");
  }

  public ListIndexes(JsonObject json) {
    super(json);
  }

  @Override
  protected JsonArray parseResponse(Object jsonValue) {
    return (JsonArray) jsonValue;
  }

  // fluent

  @Override
  public ListIndexes inCollection(String collection) {
    return (ListIndexes) super.inCollection(collection);
  }

  @Override
  public ListIndexes inCollection(Matcher<String> collection) {
    return (ListIndexes) super.inCollection(collection);
  }
}
