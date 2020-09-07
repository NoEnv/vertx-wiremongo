package com.noenv.wiremongo.mapping.aggregate;

import com.noenv.wiremongo.MemoryStream;
import com.noenv.wiremongo.TestBase;
import io.reactivex.Flowable;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.reactivex.CompletableHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class AggregateTest extends TestBase {

  @Test
  public void testAggregate(TestContext ctx) {
    Async async = ctx.async();

    mock.aggregate()
      .inCollection("aggregate")
      .withPipeline(new JsonArray().add(new JsonObject().put("$match",new JsonObject().put("id","myId"))))
      .returns(MemoryStream.of(new JsonObject().put("x", "y")));

    db.aggregate("aggregate", new JsonArray().add(new JsonObject().put("$match",new JsonObject().put("id","myId"))))
      .handler(r -> {
        ctx.assertEquals("y", r.getString("x"));
        async.complete();
      }).exceptionHandler(ctx::fail);
  }

  @Test
  public void testAggregateError(TestContext ctx) {
    Async async = ctx.async();

    mock.aggregate()
      .inCollection("aggregate")
      .withPipeline(new JsonArray().add(new JsonObject().put("$match",new JsonObject().put("id","myIdError"))))
      .returnsError(new Exception("intentional"));

    db.aggregate("aggregate", new JsonArray().add(new JsonObject().put("$match",new JsonObject().put("id","myIdError"))))
      .exceptionHandler(ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }

  @Test
  public void testAggregateFile(TestContext ctx) {
    Async async = ctx.async(3);
    db.aggregate("aggregate", new JsonArray().add(new JsonObject().put("test", "testAggregateFile")))
      .handler(r -> {
        ctx.assertEquals("value1", r.getString("field1"));
        async.countDown();
      });
  }

  @Test
  public void testAggregateFileError(TestContext ctx) {
    Async async = ctx.async();
    db.aggregate("aggregate", new JsonArray().add(new JsonObject().put("test", "testAggregateFileError")))
      .exceptionHandler(ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testAggregateReturnedObjectNotModified(TestContext ctx) {
    final Async async = ctx.async(1 * 2);
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

    mock.aggregate()
      .inCollection("aggregate")
      .withPipeline(new JsonArray().add(new JsonObject().put("$match",new JsonObject().put("id","myId"))))
      .returns(MemoryStream.of(given));

    Flowable.mergeArray(
      db.aggregate("aggregate", new JsonArray().add(new JsonObject().put("$match",new JsonObject().put("id","myId")))).toFlowable(),
      db.aggregate("aggregate", new JsonArray().add(new JsonObject().put("$match",new JsonObject().put("id","myId")))).toFlowable()
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
  @SuppressWarnings("unchecked")
  public void testAggregateFileReturnedObjectNotModified(TestContext ctx) {
    final Async async = ctx.async(3 * 2);
    final JsonObject expected = new JsonObject().put("field1", "value1");

    Flowable.mergeArray(
      db.aggregate("aggregate", new JsonArray().add(new JsonObject().put("test", "testAggregateFile"))).toFlowable(),
      db.aggregate("aggregate", new JsonArray().add(new JsonObject().put("test", "testAggregateFile"))).toFlowable()
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
