package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.command.find.FindOneAndReplaceBaseCommand;
import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindOneAndReplace extends FindOneAndReplaceBase<FindOneAndReplaceBaseCommand, FindOneAndReplace> {

  public FindOneAndReplace() {
    this("findOneAndReplace");
  }

  public FindOneAndReplace(String method) {
    super(method);
  }

  public FindOneAndReplace(JsonObject json) {
    super(json);
  }
}
