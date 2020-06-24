package com.noenv.wiremongo.command.insert;

import com.noenv.wiremongo.command.WithDocumentCommand;
import io.vertx.core.json.JsonObject;

public class InsertBaseCommand extends WithDocumentCommand {

  public InsertBaseCommand(String collection, JsonObject document) {
    super("insert", collection, document);
  }

  public InsertBaseCommand(String method, String collection, JsonObject document) {
    super(method, collection, document);
  }
}
