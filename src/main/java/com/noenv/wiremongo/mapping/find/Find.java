package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.command.find.FindBaseCommand;
import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class Find extends FindBase<FindBaseCommand, Find> {

  public Find() {
    this("find");
  }

  public Find(String method) {
    super(method);
  }

  public Find(JsonObject json) {
    super(json);
  }
}
