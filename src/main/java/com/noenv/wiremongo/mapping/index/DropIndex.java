package com.noenv.wiremongo.mapping.index;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.index.DropIndexCommand;
import com.noenv.wiremongo.mapping.collection.WithCollection;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

public class DropIndex extends WithCollection<Void, DropIndexCommand, DropIndex> {

  private Matcher<String> name;

  public DropIndex() {
    super("dropIndex");
  }

  public DropIndex(JsonObject json) {
    super(json);
    name = Matcher.create(json.getJsonObject("name"));
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
    if (!(cmd instanceof DropIndexCommand)) {
      return false;
    }
    DropIndexCommand c = (DropIndexCommand) cmd;
    return name == null || name.matches(c.getName());
  }

  public DropIndex withName(String name) {
    return withName(equalTo(name));
  }

  public DropIndex withName(Matcher<String> name) {
    this.name = name;
    return self();
  }
}
