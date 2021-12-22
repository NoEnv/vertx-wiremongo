package com.noenv.wiremongo;

import com.mongodb.MongoSocketOpenException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.MongoWriteException;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.rxjava3.MaybeHelper;
import io.vertx.rxjava3.SingleHelper;
import io.vertx.rxjava3.core.Vertx;
import io.vertx.rxjava3.ext.mongo.MongoClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.noenv.wiremongo.TestBase.assertMongoException;

@RunWith(VertxUnitRunner.class)
public class WireMongoTest {

  private final JsonObject config = new JsonObject()
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
    mock.insert()
      .inCollection("foobar")
      .returnsDuplicateKeyError();

    db.rxInsert("foobar", new JsonObject())
      .doOnError(assertMongoException(ctx, MongoWriteException.class, t -> ctx.assertEquals(11000, t.getCode())))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertFailure()));
  }

  @Test
  public void testConnectionError(TestContext ctx) {
    mock.find()
      .inCollection("foobar")
      .returnsConnectionException();

    db.rxFind("foobar", new JsonObject())
      .doOnError(assertMongoException(ctx, MongoSocketOpenException.class))
      .subscribe(SingleHelper.toObserver(ctx.asyncAssertFailure()));
  }

  @Test
  public void testTimeout(TestContext ctx) {
    mock.find()
      .inCollection("foobar")
      .returnsTimeoutException();

    db.rxFind("foobar", new JsonObject())
      .doOnError(assertMongoException(ctx, MongoTimeoutException.class))
      .subscribe(SingleHelper.toObserver(ctx.asyncAssertFailure()));
  }

  @Test
  public void testMatchError(TestContext ctx) {
    mock.find()
      .withQuery(j -> {
        throw new RuntimeException("intentional runtime exception");
      })
      .returnsConnectionException();

    db.rxFind("foobar", new JsonObject())
      .doOnError(TestBase.assertNoMappingFoundError(ctx))
      .subscribe(SingleHelper.toObserver(ctx.asyncAssertFailure()));
  }
}
