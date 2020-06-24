package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.command.find.FindOneAndUpdateBaseCommand;
import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindOneAndUpdate extends FindOneAndUpdateBase<FindOneAndUpdateBaseCommand, FindOneAndUpdate> {

  public FindOneAndUpdate() {
    this("findOneAndUpdate");
  }

  public FindOneAndUpdate(String method) {
    super(method);
  }

  public FindOneAndUpdate(JsonObject json) {
    super(json);
  }
}
