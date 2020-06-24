package com.noenv.wiremongo.mapping.remove;

import com.noenv.wiremongo.command.remove.RemoveDocumentsBaseCommand;
import com.noenv.wiremongo.mapping.WithQuery;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientDeleteResult;

public abstract class RemoveDocumentsBase<U extends RemoveDocumentsBaseCommand, C extends RemoveDocumentsBase<U, C>> extends WithQuery<MongoClientDeleteResult, U, C> {

  public RemoveDocumentsBase() {
    super("removeDocuments");
  }

  public RemoveDocumentsBase(String method) {
    super(method);
  }

  public RemoveDocumentsBase(JsonObject json) {
    super(json);
  }

  @Override
  protected MongoClientDeleteResult parseResponse(Object jsonValue) {
    return new MongoClientDeleteResult((JsonObject) jsonValue);
  }
}
