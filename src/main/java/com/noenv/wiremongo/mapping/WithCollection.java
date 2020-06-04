package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;


public abstract class WithCollection<T, C extends WithCollection<T, C>> extends MappingBase<T, C> {

  public abstract static class WithCollectionCommand extends CommandBase {

    private final String collection;

    public WithCollectionCommand(String method, String collection) {
      super(method);
      this.collection = collection;
    }

    @Override
    public String toString() {
      return super.toString() + ", collection: " + collection;
    }
  }

  private Matcher<String> collection;

  public WithCollection(String method) {
    super(method);
  }

  public WithCollection(JsonObject json) {
    super(json);
    collection = Matcher.create(json.getJsonObject("collection"));
  }

  public C inCollection(String collection) {
    return inCollection(equalTo(collection));
  }

  public C inCollection(Matcher<String> collection) {
    this.collection = collection;
    return self();
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof WithCollectionCommand)) {
      return false;
    }
    WithCollectionCommand c = (WithCollectionCommand) cmd;
    return collection == null || collection.matches(c.collection);
  }
}
