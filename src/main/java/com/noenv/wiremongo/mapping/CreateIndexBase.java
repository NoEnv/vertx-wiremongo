package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

public abstract class CreateIndexBase<C extends CreateIndexBase<C>> extends WithCollection<Void, C> {

  public static class CreateIndexBaseCommand extends WithCollectionCommand {

    private final JsonObject key;

    public CreateIndexBaseCommand(String collection, JsonObject key) {
      this("createIndex", collection, key);
    }

    public CreateIndexBaseCommand(String method, String collection, JsonObject key) {
      super(method, collection);
      this.key = key;
    }

    @Override
    public String toString() {
      return super.toString() + ", key: " + key;
    }
  }

  private Matcher<JsonObject> key;

  public CreateIndexBase() {
    this("createIndex");
  }

  public CreateIndexBase(String method) {
    super(method);
  }

  public CreateIndexBase(JsonObject json) {
    super(json);
    key = Matcher.create(json.getJsonObject("key"));
  }

  @Override
  protected Void parseResponse(Object jsonValue) {
    return null;
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof CreateIndexBase.CreateIndexBaseCommand)) {
      return false;
    }
    CreateIndexBaseCommand c = (CreateIndexBaseCommand) cmd;
    return key == null || key.matches(c.key);
  }

  public C withKey(JsonObject key) {
    return withKey(equalTo(key));
  }

  public C withKey(Matcher<JsonObject> key) {
    this.key = key;
    return self();
  }
}
