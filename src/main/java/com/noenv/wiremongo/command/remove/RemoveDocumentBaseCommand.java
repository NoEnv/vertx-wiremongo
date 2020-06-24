package com.noenv.wiremongo.command.remove;

import com.noenv.wiremongo.command.WithQueryCommand;
import io.vertx.core.json.JsonObject;

public class RemoveDocumentBaseCommand extends WithQueryCommand {

  public RemoveDocumentBaseCommand(String collection, JsonObject query) {
    super("removeDocument", collection, query);
  }

  public RemoveDocumentBaseCommand(String method, String collection, JsonObject query) {
    super(method, collection, query);
  }
}
