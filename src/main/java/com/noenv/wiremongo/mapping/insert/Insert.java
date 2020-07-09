package com.noenv.wiremongo.mapping.insert;

import com.noenv.wiremongo.command.insert.InsertBaseCommand;
import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class Insert extends InsertBase<InsertBaseCommand, Insert> {

  public Insert() {
    super("insert");
  }

  public Insert(String method) {
    super(method);
  }

  public Insert(JsonObject json) {
    super(json);
  }
}
