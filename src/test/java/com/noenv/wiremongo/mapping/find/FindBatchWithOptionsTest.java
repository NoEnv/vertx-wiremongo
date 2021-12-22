package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.MemoryStream;
import com.noenv.wiremongo.TestBase;
import io.reactivex.rxjava3.core.Flowable;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.CollationOptions;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.rxjava3.CompletableHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class FindBatchWithOptionsTest extends TestBase {

  @Test
  public void testFindBatchWithOptions(TestContext ctx) {
    Async async = ctx.async();

    mock.findBatchWithOptions()
      .inCollection("findbatchwithoptions")
      .withQuery(new JsonObject().put("test", "testFindBatchWithOptions"))
      .withOptions(new FindOptions().setLimit(17).setCollation(new CollationOptions().setLocale("de_AT").setStrength(1)))
      .returns(MemoryStream.of(new JsonObject().put("field1", "value1")));

    db.findBatchWithOptions("findbatchwithoptions", new JsonObject().put("test", "testFindBatchWithOptions"),
        new FindOptions().setLimit(17).setCollation(new CollationOptions().setLocale("de_AT").setStrength(1))
      )
      .handler(r -> {
        ctx.assertEquals("value1", r.getString("field1"));
        async.complete();
      }).exceptionHandler(ctx::fail);
  }

  @Test
  public void testFindBatchWithOptionsError(TestContext ctx) {
    Async async = ctx.async();

    mock.findBatchWithOptions()
      .inCollection("findbatchwithoptions")
      .withQuery(new JsonObject().put("test", "testFindBatchWithOptionsError"))
      .withOptions(new FindOptions().setLimit(19))
      .returnsError(new Exception("intentional"));

    db.findBatchWithOptions("findbatchwithoptions", new JsonObject().put("test", "testFindBatchWithOptionsError"), new FindOptions().setLimit(19))
      .handler(r -> ctx.fail("should fail"))
      .exceptionHandler(assertHandleIntentionalError(ctx, "intentional", async));
  }

  @Test
  public void testFindBatchWithOptionsFile(TestContext ctx) {
    Async async = ctx.async(3);
    db.findBatchWithOptions("findbatchwithoptions", new JsonObject().put("test", "testFindBatchWithOptionsFile"), new FindOptions().setSkip(21))
      .handler(r -> {
        ctx.assertEquals("value1", r.getString("field1"));
        async.countDown();
      }).exceptionHandler(ctx::fail);
  }

  @Test
  public void testFindBatchWithOptionsFileError(TestContext ctx) {
    Async async = ctx.async();
    db.findBatchWithOptions("findbatchwithoptions", new JsonObject().put("test", "testFindBatchWithOptionsFileError"), new FindOptions().setSkip(27))
      .handler(r -> ctx.fail("should fail"))
      .exceptionHandler(assertHandleIntentionalError(ctx, "intentional", async));
  }

  @Test
  public void testFindBatchWithOptionsReturnedObjectNotModified(TestContext ctx) {
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

    mock.findBatchWithOptions()
      .inCollection("findbatchwithoptions")
      .withQuery(new JsonObject().put("test", "testFindBatchWithOptions"))
      .withOptions(new FindOptions().setLimit(17))
      .returns(MemoryStream.of(given));

    Flowable.mergeArray(
        db.findBatchWithOptions("findbatchwithoptions", new JsonObject().put("test", "testFindBatchWithOptions"), new FindOptions().setLimit(17)).toFlowable(),
        db.findBatchWithOptions("findbatchwithoptions", new JsonObject().put("test", "testFindBatchWithOptions"), new FindOptions().setLimit(17)).toFlowable()
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
  public void testFindBatchWithOptionsFileReturnedObjectNotModified(TestContext ctx) {
    final Async async = ctx.async(3 * 2);
    final JsonObject expected = new JsonObject().put("field1", "value1");

    Flowable.mergeArray(
        db.findBatchWithOptions("findbatchwithoptions", new JsonObject().put("test", "testFindBatchWithOptionsFile"), new FindOptions().setSkip(21)).toFlowable(),
        db.findBatchWithOptions("findbatchwithoptions", new JsonObject().put("test", "testFindBatchWithOptionsFile"), new FindOptions().setSkip(21)).toFlowable()
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
