package com.noenv.wiremongo.mapping.index;

import com.noenv.wiremongo.command.index.ListIndexesCommand;
import com.noenv.wiremongo.mapping.collection.WithCollection;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class ListIndexes extends WithCollection<JsonArray, ListIndexesCommand, ListIndexes> {

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
}
