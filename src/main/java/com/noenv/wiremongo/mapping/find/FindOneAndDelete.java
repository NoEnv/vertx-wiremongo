package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.command.find.FindOneAndDeleteBaseCommand;
import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindOneAndDelete extends FindOneAndDeleteBase<FindOneAndDeleteBaseCommand, FindOneAndDelete> {

  public FindOneAndDelete() {
    this("findOneAndDelete");
  }

  public FindOneAndDelete(String method) {
    super(method);
  }

  public FindOneAndDelete(JsonObject json) {
    super(json);
  }
}
