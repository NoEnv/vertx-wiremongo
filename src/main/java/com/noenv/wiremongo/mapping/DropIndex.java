package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

public class DropIndex extends WithCollection<Void> {

  public static class DropIndexCommand extends WithCollectionCommand {

    private final String name;

    public DropIndexCommand(String collection, String name) {
      super("dropIndex", collection);
      this.name = name;
    }

    @Override
    public String toString() {
      return super.toString() + ", name: " + name;
    }
  }

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
    return name == null || name.matches(c.name);
  }

  public DropIndex withName(String name) {
    return withName(equalTo(name));
  }

  public DropIndex withName(Matcher<String> name) {
    this.name = name;
    return this;
  }

  // fluent

  @Override
  public DropIndex priority(int priority) {
    return (DropIndex) super.priority(priority);
  }

  @Override
  public DropIndex inCollection(String collection) {
    return (DropIndex) super.inCollection(collection);
  }

  @Override
  public DropIndex inCollection(Matcher<String> collection) {
    return (DropIndex) super.inCollection(collection);
  }
}
