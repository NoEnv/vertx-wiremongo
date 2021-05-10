package com.noenv.wiremongo;

import com.mongodb.MongoSocketOpenException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.MongoWriteException;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.rxjava3.core.Vertx;
import io.vertx.rxjava3.ext.mongo.MongoClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class WireMongoTest {

  private JsonObject config = new JsonObject()
    .put("class", WireMongoClient.class.getName());
  private MongoClient db;
  private com.noenv.rxjava3.wiremongo.WireMongo mock;

  @Before
  public void setUp() {
    Vertx vertx = Vertx.vertx();
    db = com.noenv.rxjava3.wiremongo.WireMongoClient.createShared(vertx, config, null);
    mock = new com.noenv.rxjava3.wiremongo.WireMongo(vertx);
  }

  @After
  public void tearDown() {
    mock.clear();
  }

  @Test
  public void testDuplicate(TestContext ctx) {
    Async async = ctx.async();

    mock.insert()
      .inCollection("foobar")
      .returnsDuplicateKeyError();

    db.rxInsert("foobar", new JsonObject())
      .subscribe(x -> ctx.fail(), ex -> {
        ctx.assertTrue(ex instanceof MongoWriteException);
        ctx.assertEquals(11000, ((MongoWriteException)ex).getCode());
        async.complete();
      });
  }

  @Test
  public void testConnectionError(TestContext ctx) {
    Async async = ctx.async();

    mock.find()
      .inCollection("foobar")
      .returnsConnectionException();

    db.rxFind("foobar", new JsonObject())
      .subscribe(x -> ctx.fail(), ex -> {
        ctx.assertTrue(ex instanceof MongoSocketOpenException);
        async.complete();
      });
  }

  @Test
  public void testTimeout(TestContext ctx) {
    Async async = ctx.async();

    mock.find()
      .inCollection("foobar")
      .returnsTimeoutException();

    db.rxFind("foobar", new JsonObject())
      .subscribe(x -> ctx.fail(), ex -> {
        ctx.assertTrue(ex instanceof MongoTimeoutException);
        async.complete();
      });
  }

  @Test
  public void testMatchError(TestContext ctx) {
    Async async = ctx.async();

    mock.find()
      .withQuery(j -> {
        throw new RuntimeException("intentional");
      })
      .returnsConnectionException();

    db.rxFind("foobar", new JsonObject())
      .doOnError(ex -> ctx.assertTrue(ex.getMessage().startsWith("no mapping found")))
      .subscribe(x -> ctx.fail(), ex -> async.complete());
  }
}
