package com.noenv.wiremongo.mapping.collection;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.collection.WithCollectionCommand;
import com.noenv.wiremongo.mapping.MappingBase;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;


public abstract class WithCollection<T, U extends WithCollectionCommand, C extends WithCollection<T, U, C>> extends MappingBase<T, U, C> {

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
    return collection == null || collection.matches(c.getCollection());
  }
}
