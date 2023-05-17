package com.noenv.wiremongo.examples;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.mongo.MongoClientDeleteResult;

import java.time.Instant;

public class FruitDatabase {

  private final MongoClient mongo;

  public FruitDatabase(MongoClient mongo) {
    this.mongo = mongo;
  }

  public Future<Void> addApple(int mass, Instant expiration) {
    return insertFruit("apple", mass, expiration).mapEmpty();
  }

  public Future<Void> addBanana(int mass, Instant expiration) {
    return insertFruit("banana", mass, expiration).mapEmpty();
  }

  private Future<String> insertFruit(String type, int mass, Instant expiration) {
    return mongo.insert("fruits", new JsonObject()
      .put("type", type)
      .put("mass", mass)
      .put("expiration", new JsonObject().put("$date", expiration)));
  }

  public Future<Long> countFruitByType(String type) {
    return mongo.count("fruits", new JsonObject().put("type", type));
  }

  public Future<Long> removeExpiredFruit() {
    return removeExpiredFruit(Instant.now());
  }

  public Future<Long> removeExpiredFruit(Instant cutoff) {
    return mongo.removeDocument("fruits", new JsonObject()
      .put("expiration", new JsonObject()
        .put("$lt", new JsonObject().put("$date", cutoff))))
      .map(MongoClientDeleteResult::getRemovedCount);
  }
}
