package com.noenv.wiremongo.command.save;

import com.noenv.wiremongo.command.WithDocumentCommand;
import io.vertx.core.json.JsonObject;

public class SaveBaseCommand extends WithDocumentCommand {

  public SaveBaseCommand(String collection, JsonObject document) {
    super("save", collection, document);
  }

  public SaveBaseCommand(String method, String collection, JsonObject document) {
    super(method, collection, document);
  }
}
