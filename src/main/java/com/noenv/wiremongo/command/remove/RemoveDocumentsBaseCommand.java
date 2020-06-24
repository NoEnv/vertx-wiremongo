package com.noenv.wiremongo.command.remove;

import com.noenv.wiremongo.command.WithQueryCommand;
import io.vertx.core.json.JsonObject;

public class RemoveDocumentsBaseCommand extends WithQueryCommand {

  public RemoveDocumentsBaseCommand(String collection, JsonObject query) {
    super("removeDocuments", collection, query);
  }

  public RemoveDocumentsBaseCommand(String method, String collection, JsonObject query) {
    super(method, collection, query);
  }
}
