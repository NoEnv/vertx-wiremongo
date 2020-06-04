package com.noenv.wiremongo.mapping;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientDeleteResult;

public class RemoveDocument extends WithQuery<MongoClientDeleteResult, RemoveDocument> {

  public static class RemoveDocumentCommand extends WithQueryCommand {
    public RemoveDocumentCommand(String collection, JsonObject query) {
      super("removeDocument", collection, query);
    }

    public RemoveDocumentCommand(String method, String collection, JsonObject query) {
      super(method, collection, query);
    }
  }

  public RemoveDocument() {
    super("removeDocument");
  }

  public RemoveDocument(String method) {
    super(method);
  }

  public RemoveDocument(JsonObject json) {
    super(json);
  }

  @Override
  protected MongoClientDeleteResult parseResponse(Object jsonValue) {
    return new MongoClientDeleteResult((JsonObject) jsonValue);
  }
}
