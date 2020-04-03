package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

public class DropCollection extends WithCollection<Void> {

  public static class DropCollectionCommand extends WithCollectionCommand {
    public DropCollectionCommand(String collection) {
      super("dropCollection", collection);
    }
  }

  public DropCollection() {
    super("dropCollection");
  }

  public DropCollection(JsonObject json) {
    super(json);
  }

  @Override
  protected Void parseResponse(Object jsonValue) {
    return null;
  }

  // fluent

  @Override
  public DropCollection priority(int priority) {
    return (DropCollection) super.priority(priority);
  }

  @Override
  public DropCollection inCollection(String collection) {
    return (DropCollection) super.inCollection(collection);
  }

  @Override
  public DropCollection inCollection(Matcher<String> collection) {
    return (DropCollection) super.inCollection(collection);
  }
}
