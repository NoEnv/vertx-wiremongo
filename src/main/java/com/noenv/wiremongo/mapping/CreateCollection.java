package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

public class CreateCollection extends WithCollection<Void> {

  public static class CreateCollectionCommand extends WithCollectionCommand {
    public CreateCollectionCommand(String collection) {
      super("createCollection", collection);
    }
  }

  public CreateCollection() {
    super("createCollection");
  }

  public CreateCollection(JsonObject json) {
    super(json);
  }

  @Override
  protected Void parseResponse(Object jsonValue) {
    return null;
  }

  // fluent

  @Override
  public CreateCollection inCollection(String collection) {
    return (CreateCollection) super.inCollection(collection);
  }

  @Override
  public CreateCollection inCollection(Matcher<String> collection) {
    return (CreateCollection) super.inCollection(collection);
  }
}
