package com.noenv.wiremongo.command.find;

import com.noenv.wiremongo.command.WithQueryCommand;
import io.vertx.core.json.JsonObject;

public class FindOneCommand extends WithQueryCommand {

  private final JsonObject fields;

  public FindOneCommand(String collection, JsonObject query, JsonObject fields) {
    super("findOne", collection, query);
    this.fields = fields;
  }

  public JsonObject getFields() {
    return fields;
  }

  @Override
  public String toString() {
    return super.toString() + ", fields: " + fields;
  }
}
