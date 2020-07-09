package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.command.find.FindBatchBaseCommand;
import com.noenv.wiremongo.mapping.stream.WithStreamQuery;
import io.vertx.core.json.JsonObject;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindBatchBase<U extends FindBatchBaseCommand, C extends FindBatchBase<U, C>> extends WithStreamQuery<U, C> {

  public FindBatchBase(String method) {
    super(method);
  }

  public FindBatchBase(JsonObject json) {
    super(json);
  }
}
