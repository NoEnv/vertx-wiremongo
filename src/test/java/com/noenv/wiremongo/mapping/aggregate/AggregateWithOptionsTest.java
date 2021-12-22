package com.noenv.wiremongo.mapping.aggregate;

import com.noenv.wiremongo.MemoryStream;
import com.noenv.wiremongo.TestBase;
import io.reactivex.rxjava3.core.Flowable;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.AggregateOptions;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.rxjava3.CompletableHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class AggregateWithOptionsTest extends TestBase {

  @Test
  public void testAggregateWithOptions(TestContext ctx) {
    Async async = ctx.async();

    mock.aggregateWithOptions()
      .inCollection("aggregateWithOptions")
      .withPipeline(new JsonArray().add(new JsonObject().put("$match", new JsonObject().put("id", "myId"))))
      .withOptions(new AggregateOptions().setAllowDiskUse(true).setMaxTime(2345))
      .returns(MemoryStream.of(new JsonObject().put("x", "y")));

    db.aggregateWithOptions("aggregateWithOptions",
        new JsonArray().add(new JsonObject().put("$match", new JsonObject().put("id", "myId"))),
        new AggregateOptions().setAllowDiskUse(true).setMaxTime(2345))
      .handler(r -> {
        ctx.assertEquals("y", r.getString("x"));
        async.complete();
      }).exceptionHandler(ctx::fail);
  }

  @Test
  public void testAggregateWithOptionsFile(TestContext ctx) {
    Async async = ctx.async(3);
    db.aggregateWithOptions("aggregateWithOptions",
        new JsonArray().add(new JsonObject().put("test", "testAggregateWithOptionsFile")),
        new AggregateOptions().setAllowDiskUse(false).setMaxTime(345))
      .handler(r -> {
        ctx.assertEquals("value1", r.getString("field1"));
        async.countDown();
      }).exceptionHandler(ctx::fail);
  }

  @Test
  public void testAggregateWithOptionsError(TestContext ctx) {
    Async async = ctx.async();

    mock.aggregateWithOptions()
      .inCollection("aggregateWithOptionsError")
      .withPipeline(new JsonArray().add(new JsonObject().put("$match", new JsonObject().put("id", "myId"))))
      .withOptions(new AggregateOptions().setAllowDiskUse(false).setMaxTime(2345))
      .returnsError(new Exception("intentional"));

    db.aggregateWithOptions("aggregateWithOptionsError",
        new JsonArray().add(new JsonObject().put("$match", new JsonObject().put("id", "myId"))),
        new AggregateOptions().setAllowDiskUse(false).setMaxTime(2345)
      )
      .handler(r -> ctx.fail("should fail"))
      .exceptionHandler(assertHandleIntentionalError(ctx, "intentional", async));
  }

  @Test
  public void testAggregateWithOptionsFileError(TestContext ctx) {
    Async async = ctx.async();
    db.aggregateWithOptions("aggregateWithOptions",
        new JsonArray().add(new JsonObject().put("test", "testAggregateWithOptionsFileError")),
        new AggregateOptions().setAllowDiskUse(false).setMaxTime(234))
      .handler(r -> ctx.fail("should fail"))
      .exceptionHandler(assertHandleIntentionalError(ctx, "intentional", async));
  }

  @Test
  public void testAggregateWithOptionsReturnedObjectNotModified(TestContext ctx) {
    final Async async = ctx.async(2);
    final JsonObject given = new JsonObject()
      .put("field1", "value1")
      .put("field2", "value2")
      .put("field3", new JsonObject()
        .put("field4", "value3")
        .put("field5", "value4")
        .put("field6", new JsonArray()
          .add("value5")
          .add("value6")
        )
      );
    final JsonObject expected = given.copy();

    mock.aggregateWithOptions()
      .inCollection("aggregateWithOptions")
      .withPipeline(new JsonArray().add(new JsonObject().put("$match", new JsonObject().put("id", "myId"))))
      .withOptions(new AggregateOptions().setAllowDiskUse(true).setMaxTime(2345))
      .returns(MemoryStream.of(given));

    Flowable.mergeArray(
        db.aggregateWithOptions("aggregateWithOptions", new JsonArray().add(new JsonObject().put("$match", new JsonObject().put("id", "myId"))), new AggregateOptions().setAllowDiskUse(true).setMaxTime(2345)).toFlowable(),
        db.aggregateWithOptions("aggregateWithOptions", new JsonArray().add(new JsonObject().put("$match", new JsonObject().put("id", "myId"))), new AggregateOptions().setAllowDiskUse(true).setMaxTime(2345)).toFlowable()
      )
      .doOnNext(actual -> async.countDown())
      .doOnNext(actual -> ctx.assertEquals(expected, actual))
      .doOnNext(actual -> {
        actual.put("field1", "replace");
        actual.remove("field2");
        actual.put("add", "add");
        actual.getJsonObject("field3").put("field4", "replace");
        actual.getJsonObject("field3").remove("field5");
        actual.getJsonObject("field3").put("add", "add");
        actual.getJsonObject("field3").getJsonArray("field6").remove(0);
        actual.getJsonObject("field3").getJsonArray("field6").add("add");
      })
      .ignoreElements()
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testAggregateWithOptionsFileReturnedObjectNotModified(TestContext ctx) {
    final Async async = ctx.async(3 * 2);
    final JsonObject expected = new JsonObject().put("field1", "value1");

    Flowable.mergeArray(
        db.aggregateWithOptions("aggregateWithOptions", new JsonArray().add(new JsonObject().put("test", "testAggregateWithOptionsFile")), new AggregateOptions().setAllowDiskUse(false).setMaxTime(345)).toFlowable(),
        db.aggregateWithOptions("aggregateWithOptions", new JsonArray().add(new JsonObject().put("test", "testAggregateWithOptionsFile")), new AggregateOptions().setAllowDiskUse(false).setMaxTime(345)).toFlowable()
      )
      .doOnNext(actual -> async.countDown())
      .doOnNext(actual -> ctx.assertEquals(expected, actual))
      .doOnNext(actual -> {
        actual.put("field1", "replace");
        actual.put("add", "add");
      })
      .ignoreElements()
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }
}
