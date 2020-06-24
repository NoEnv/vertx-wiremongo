package com.noenv.wiremongo.mapping.bulkwrite;

import io.vertx.core.json.JsonObject;

public class BulkWrite extends BulkWriteBase<BulkWrite> {

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
