package com.noenv.reactivex.wiremongo;

import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.mongo.MongoClient;

public class WireMongoClient extends MongoClient {

  WireMongoClient(com.noenv.wiremongo.WireMongoClient delegate) {
    super(delegate);
  }

  public static WireMongoClient createShared(Vertx vertx, JsonObject config, String dataSource) {
    return new WireMongoClient(com.noenv.wiremongo.WireMongoClient.createShared(vertx.getDelegate(), config, dataSource));
  }
}
