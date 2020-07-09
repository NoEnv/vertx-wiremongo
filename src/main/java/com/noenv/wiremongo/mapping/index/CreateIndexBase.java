package com.noenv.wiremongo.mapping.index;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.index.CreateIndexBaseCommand;
import com.noenv.wiremongo.mapping.collection.WithCollection;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

public abstract class CreateIndexBase<U extends CreateIndexBaseCommand, C extends CreateIndexBase<U, C>> extends WithCollection<Void, U, C> {

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
    if (!(cmd instanceof CreateIndexBaseCommand)) {
      return false;
    }
    CreateIndexBaseCommand c = (CreateIndexBaseCommand) cmd;
    return key == null || key.matches(c.getKey());
  }

  public C withKey(JsonObject key) {
    return withKey(equalTo(key));
  }

  public C withKey(Matcher<JsonObject> key) {
    this.key = key;
    return self();
  }
}
