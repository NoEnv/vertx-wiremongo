package com.noenv.wiremongo.mapping.bulkwrite;

import com.mongodb.MongoBulkWriteException;
import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.BulkOperation;
import io.vertx.ext.mongo.BulkWriteOptions;
import io.vertx.ext.mongo.MongoClientBulkWriteResult;
import io.vertx.ext.mongo.WriteOption;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.rxjava3.CompletableHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@RunWith(VertxUnitRunner.class)
public class BulkWriteWithOptionsTest extends TestBase {

  @Test
  public void testBulkWriteWithOptions(TestContext ctx) {
    Async async = ctx.async();

    mock.bulkWriteWithOptions()
      .inCollection("bulkwritewithoptions")
      .withOperations(Arrays.asList(BulkOperation.createInsert(new JsonObject().put("test", "testBulkWriteWithOptions"))))
      .withOptions(new BulkWriteOptions().setOrdered(false))
      .returns(new MongoClientBulkWriteResult(0, 24, 0, 0, null));

    db.rxBulkWriteWithOptions("bulkwritewithoptions",
      Arrays.asList(BulkOperation.createInsert(new JsonObject().put("test", "testBulkWriteWithOptions"))),
      new BulkWriteOptions().setOrdered(false))
      .subscribe(r -> {
        ctx.assertEquals(24L, r.getMatchedCount());
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testBulkWriteWithOptionsFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxBulkWriteWithOptions("bulkwritewithoptions",
      Arrays.asList(BulkOperation.createInsert(new JsonObject().put("test", "testBulkWriteWithOptionsFile"))),
      new BulkWriteOptions().setWriteOption(WriteOption.ACKNOWLEDGED))
      .subscribe(r -> {
        ctx.assertEquals(71L, r.getModifiedCount());
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testBulkWriteWithOptionsFileError(TestContext ctx) {
    Async async = ctx.async();
    db.rxBulkWriteWithOptions("bulkwritewithoptions",
      Arrays.asList(BulkOperation.createInsert(new JsonObject().put("test", "testBulkWriteWithOptionsFileError"))),
      new BulkWriteOptions().setWriteOption(WriteOption.JOURNALED))
      .subscribe(r -> ctx.fail(), ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }

  @Test
  public void testBulkWriteWithOptionsReturnedObjectNotModified(TestContext ctx) {
    final MongoClientBulkWriteResult given = new MongoClientBulkWriteResult(1, 2, 3, 4, new ArrayList<>(Collections.singletonList(
      new JsonObject()
        .put("field1", "value1")
        .put("field2", "value2")
        .put("field3", new JsonObject()
          .put("field4", "value3")
          .put("field5", "value4")
          .put("field6", new JsonArray()
            .add("value5")
            .add("value6")
          )
        )
    )));
    final MongoClientBulkWriteResult expected = new MongoClientBulkWriteResult(given.toJson().copy());

    mock.bulkWriteWithOptions()
      .inCollection("bulkwritewithoptions")
      .withOperations(Arrays.asList(BulkOperation.createInsert(new JsonObject().put("test", "testBulkWriteWithOptions"))))
      .withOptions(new BulkWriteOptions().setOrdered(false))
      .returns(given);

    db.rxBulkWriteWithOptions("bulkwritewithoptions", Arrays.asList(BulkOperation.createInsert(new JsonObject().put("test", "testBulkWriteWithOptions"))), new BulkWriteOptions().setOrdered(false))
      .doOnSuccess(actual -> ctx.assertEquals(expected.toJson(), actual.toJson()))
      .doOnSuccess(actual -> {
        actual.getUpserts().get(0).put("field1", "replace");
        actual.getUpserts().get(0).remove("field2");
        actual.getUpserts().get(0).put("add", "add");
        actual.getUpserts().get(0).getJsonObject("field3").put("field4", "replace");
        actual.getUpserts().get(0).getJsonObject("field3").remove("field5");
        actual.getUpserts().get(0).getJsonObject("field3").put("add", "add");
        actual.getUpserts().get(0).getJsonObject("field3").getJsonArray("field6").remove(0);
        actual.getUpserts().get(0).getJsonObject("field3").getJsonArray("field6").add("add");
      })
      .repeat(2)
      .ignoreElements()
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testBulkWriteWithOptionsFileReturnedObjectNotModified(TestContext ctx) {
    final MongoClientBulkWriteResult expected = new MongoClientBulkWriteResult(0, 0, 0, 71, new ArrayList<>(Collections.singletonList(new JsonObject().put("field1", "value1"))));

    db.rxBulkWriteWithOptions("bulkwritewithoptions", Arrays.asList(BulkOperation.createInsert(new JsonObject().put("test", "testBulkWriteWithOptionsFile"))), new BulkWriteOptions().setWriteOption(WriteOption.ACKNOWLEDGED))
      .doOnSuccess(actual -> ctx.assertEquals(expected.toJson(), actual.toJson()))
      .doOnSuccess(actual -> {
        actual.getUpserts().get(0).put("field1", "replace");
        actual.getUpserts().get(0).put("add", "add");
      })
      .repeat(2)
      .ignoreElements()
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testBulkWriteWithOptionsDuplicateKeyError(TestContext ctx) {
    mock.bulkWriteWithOptions()
      .inCollection("bulkwritewithoptions")
      .withOperations(Arrays.asList(BulkOperation.createInsert(new JsonObject().put("test", "testBulkWriteWithOptions"))))
      .withOptions(new BulkWriteOptions().setOrdered(false))
      .returnsDuplicateKeyError();

    db.rxBulkWriteWithOptions("bulkwritewithoptions", Arrays.asList(BulkOperation.createInsert(new JsonObject().put("test", "testBulkWriteWithOptions"))), new BulkWriteOptions().setOrdered(false))
      .doOnError(cause -> {
        ctx.assertTrue(cause instanceof MongoBulkWriteException);
        final MongoBulkWriteException actual = (MongoBulkWriteException) cause;
        ctx.assertEquals(1, actual.getWriteErrors().size());
        ctx.assertEquals(11000, actual.getWriteErrors().get(0).getCode());
      })
      .ignoreElement()
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertFailure()));
  }

  @Test
  public void testBulkWriteWithOptionsOtherError(TestContext ctx) {
    mock.bulkWriteWithOptions()
      .inCollection("bulkwritewithoptions")
      .withOperations(Arrays.asList(BulkOperation.createInsert(new JsonObject().put("test", "testBulkWriteWithOptions"))))
      .withOptions(new BulkWriteOptions().setOrdered(false))
      .returnsOtherBulkWriteError();

    db.rxBulkWriteWithOptions("bulkwritewithoptions", Arrays.asList(BulkOperation.createInsert(new JsonObject().put("test", "testBulkWriteWithOptions"))), new BulkWriteOptions().setOrdered(false))
      .doOnError(cause -> {
        ctx.assertTrue(cause instanceof MongoBulkWriteException);
        final MongoBulkWriteException actual = (MongoBulkWriteException) cause;
        ctx.assertEquals(1, actual.getWriteErrors().size());
        ctx.assertEquals(22000, actual.getWriteErrors().get(0).getCode());
      })
      .ignoreElement()
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertFailure()));
  }
}
