package com.noenv.wiremongo.command.index;

import com.noenv.wiremongo.command.collection.WithCollectionCommand;
import io.vertx.core.json.JsonObject;

public class CreateIndexBaseCommand extends WithCollectionCommand {

  private final JsonObject key;

  public CreateIndexBaseCommand(String collection, JsonObject key) {
    this("createIndex", collection, key);
  }

  public CreateIndexBaseCommand(String method, String collection, JsonObject key) {
    super(method, collection);
    this.key = key;
  }

  public JsonObject getKey() {
    return key;
  }

  @Override
  public String toString() {
    return super.toString() + ", key: " + key;
  }
}
