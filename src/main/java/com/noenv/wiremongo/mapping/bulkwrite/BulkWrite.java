package com.noenv.wiremongo.mapping.bulkwrite;

import com.noenv.wiremongo.command.bulkwrite.BulkWriteBaseCommand;
import io.vertx.core.json.JsonObject;

public class BulkWrite extends BulkWriteBase<BulkWriteBaseCommand, BulkWrite> {

  public BulkWrite() {
    this("bulkWrite");
  }

  public BulkWrite(String method) {
    super(method);
  }

  public BulkWrite(JsonObject json) {
    super(json);
  }
}
