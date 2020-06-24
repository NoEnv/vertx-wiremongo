package com.noenv.wiremongo.mapping.remove;

import com.noenv.wiremongo.command.remove.RemoveDocumentBaseCommand;
import com.noenv.wiremongo.mapping.WithQuery;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientDeleteResult;

public abstract class RemoveDocumentBase<U extends RemoveDocumentBaseCommand, C extends RemoveDocumentBase<U, C>> extends WithQuery<MongoClientDeleteResult, U, C> {

  public RemoveDocumentBase() {
    super("removeDocument");
  }

  public RemoveDocumentBase(String method) {
    super(method);
  }

  public RemoveDocumentBase(JsonObject json) {
    super(json);
  }

  @Override
  protected MongoClientDeleteResult parseResponse(Object jsonValue) {
    return new MongoClientDeleteResult((JsonObject) jsonValue);
  }
}
