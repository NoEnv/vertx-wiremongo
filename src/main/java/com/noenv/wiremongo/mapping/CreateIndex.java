package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

public class CreateIndex extends WithCollection<Void> {

  public static class CreateIndexCommand extends WithCollectionCommand {

    private final JsonObject key;

    public CreateIndexCommand(String collection, JsonObject key) {
      this("createIndex", collection, key);
    }

    public CreateIndexCommand(String method, String collection, JsonObject key) {
      super(method, collection);
      this.key = key;
    }

    @Override
    public String toString() {
      return super.toString() + ", key: " + key;
    }
  }

  private Matcher<JsonObject> key;

  public CreateIndex() {
    this("createIndex");
  }

  public CreateIndex(String method) {
    super(method);
  }

  public CreateIndex(JsonObject json) {
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
    if (!(cmd instanceof CreateIndexCommand)) {
      return false;
    }
    CreateIndexCommand c = (CreateIndexCommand) cmd;
    return key == null || key.matches(c.key);
  }

  public CreateIndex withKey(JsonObject key) {
    return withKey(equalTo(key));
  }

  public CreateIndex withKey(Matcher<JsonObject> key) {
    this.key = key;
    return this;
  }

  // fluent

  @Override
  public CreateIndex inCollection(String collection) {
    return (CreateIndex) super.inCollection(collection);
  }

  @Override
  public CreateIndex inCollection(Matcher<String> collection) {
    return (CreateIndex) super.inCollection(collection);
  }
}
