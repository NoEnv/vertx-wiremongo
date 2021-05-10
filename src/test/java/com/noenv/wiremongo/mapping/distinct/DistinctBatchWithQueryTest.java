package com.noenv.wiremongo.mapping.distinct;

import com.noenv.wiremongo.MemoryStream;
import com.noenv.wiremongo.TestBase;
import io.reactivex.rxjava3.core.Flowable;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.rxjava3.CompletableHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class DistinctBatchWithQueryTest extends TestBase {

  @Test
  public void testDistinctBatchWithQuery(TestContext ctx) {
    Async async = ctx.async();
    mock.distinctBatchWithQuery()
      .inCollection("distinctBatchWithQuery")
      .withFieldName("testDistinctBatchWithQuery")
      .withQuery(new JsonObject().put("foo", "bar"))
      .withResultClassname("io.vertx.core.json.JsonObject")
      .returns(MemoryStream.of(new JsonObject().put("x", "y")));
    db.distinctBatchWithQuery("distinctBatchWithQuery", "testDistinctBatchWithQuery",
      "io.vertx.core.json.JsonObject", new JsonObject().put("foo", "bar"))
      .handler(r -> {
        ctx.assertEquals("y", r.getString("x"));
        async.complete();
      }).exceptionHandler(ctx::fail);
  }

  @Test
  public void testDistinctBatchWithQueryFile(TestContext ctx) {
    Async async = ctx.async(3);
    db.distinctBatchWithQuery("distinctBatchWithQuery", "testDistinctBatchWithQueryFile", "io.vertx.core.json.JsonObject", new JsonObject().put("foo", "bar"))
      .handler(r -> {
        ctx.assertEquals("value1", r.getString("field1"));
        async.countDown();
      });
  }

  @Test
  public void testDistinctBatchWithQueryFileError(TestContext ctx) {
    Async async = ctx.async();
    db.distinctBatchWithQuery("distinctBatchWithQuery", "testDistinctBatchWithQueryFileError", "io.vertx.core.json.JsonObject", new JsonObject().put("foo", "bar"))
      .exceptionHandler(ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testDistinctBatchWithQueryReturnedObjectNotModified(TestContext ctx) {
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

    mock.distinctBatchWithQuery()
      .inCollection("distinctBatchWithQuery")
      .withFieldName("testDistinctBatchWithQuery")
      .withQuery(new JsonObject().put("foo", "bar"))
      .withResultClassname("io.vertx.core.json.JsonObject")
      .returns(MemoryStream.of(given));

    Flowable.mergeArray(
      db.distinctBatchWithQuery("distinctBatchWithQuery", "testDistinctBatchWithQuery", "io.vertx.core.json.JsonObject", new JsonObject().put("foo", "bar")).toFlowable(),
      db.distinctBatchWithQuery("distinctBatchWithQuery", "testDistinctBatchWithQuery", "io.vertx.core.json.JsonObject", new JsonObject().put("foo", "bar")).toFlowable()
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
  public void testDistinctBatchWithQueryFileReturnedObjectNotModified(TestContext ctx) {
    final Async async = ctx.async(3 * 2);
    final JsonObject expected = new JsonObject().put("field1", "value1");

    Flowable.mergeArray(
      db.distinctBatchWithQuery("distinctBatchWithQuery", "testDistinctBatchWithQueryFile", "io.vertx.core.json.JsonObject", new JsonObject().put("foo", "bar")).toFlowable(),
      db.distinctBatchWithQuery("distinctBatchWithQuery", "testDistinctBatchWithQueryFile", "io.vertx.core.json.JsonObject", new JsonObject().put("foo", "bar")).toFlowable()
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
