package com.noenv.wiremongo.mapping.replace;

import com.noenv.wiremongo.command.replace.ReplaceDocumentsBaseCommand;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientUpdateResult;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public abstract class ReplaceDocumentsBase<U extends ReplaceDocumentsBaseCommand, C extends ReplaceDocumentsBase<U, C>> extends WithReplace<MongoClientUpdateResult, U, C> {

  public ReplaceDocumentsBase() {
    this("replaceDocuments");
  }

  public ReplaceDocumentsBase(String method) {
    super(method);
  }

  public ReplaceDocumentsBase(JsonObject json) {
    super(json);
  }

  @Override
  protected MongoClientUpdateResult parseResponse(Object jsonValue) {
    return new MongoClientUpdateResult((JsonObject) jsonValue);
  }
}
