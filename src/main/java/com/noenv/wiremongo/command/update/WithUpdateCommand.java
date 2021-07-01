package com.noenv.wiremongo.command.update;

import com.noenv.wiremongo.command.WithQueryCommand;
import io.vertx.core.json.JsonObject;

public abstract class WithUpdateCommand<T> extends WithQueryCommand {

  private final T update;

  public WithUpdateCommand(String method, String collection, JsonObject query, T update) {
    super(method, collection, query);
    this.update = update;
  }

  public T getUpdate() {
    return update;
  }

  @Override
  public String toString() {
    return super.toString() + ", update: " + update;
  }
}
