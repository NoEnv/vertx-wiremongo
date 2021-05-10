package com.noenv.rxjava3.wiremongo;

import io.vertx.core.json.JsonObject;
import io.vertx.rxjava3.core.Vertx;
import io.vertx.rxjava3.ext.mongo.MongoClient;

public class WireMongoClient extends MongoClient {

  WireMongoClient(com.noenv.wiremongo.WireMongoClient delegate) {
    super(delegate);
  }

  public static WireMongoClient createShared(Vertx vertx, JsonObject config, String dataSource) {
    return new WireMongoClient(com.noenv.wiremongo.WireMongoClient.createShared(vertx.getDelegate(), config, dataSource));
  }
}
