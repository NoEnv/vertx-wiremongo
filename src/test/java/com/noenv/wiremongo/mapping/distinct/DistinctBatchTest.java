package com.noenv.wiremongo.mapping.distinct;

import com.noenv.wiremongo.MemoryStream;
import com.noenv.wiremongo.TestBase;
import io.reactivex.rxjava3.core.Flowable;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.CollationOptions;
import io.vertx.ext.mongo.DistinctOptions;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.rxjava3.CompletableHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class DistinctBatchTest extends TestBase {

  @Test
  public void testDistinctBatch(TestContext ctx) {
    Async async = ctx.async();
    mock.distinctBatch()
      .inCollection("distinctBatch")
      .withFieldName("testDistinctBatch")
      .withResultClassname("io.vertx.core.json.JsonObject")
      .returns(MemoryStream.of(new JsonObject().put("x", "y")));
    db.distinctBatch("distinctBatch", "testDistinctBatch", "io.vertx.core.json.JsonObject")
      .handler(r -> {
        ctx.assertEquals("y", r.getString("x"));
        async.complete();
      }).exceptionHandler(ctx::fail);
  }

  @Test
  public void testDistinctBatchFile(TestContext ctx) {
    Async async = ctx.async(3);
    db.distinctBatch("distinctBatch", "testDistinctBatchFile", "io.vertx.core.json.JsonObject")
      .handler(r -> {
        ctx.assertEquals("value1", r.getString("field1"));
        async.countDown();
      }).exceptionHandler(ctx::fail);
  }

  @Test
  public void testDistinctBatchFileError(TestContext ctx) {
    Async async = ctx.async();
    db.distinctBatch("distinctBatch", "testDistinctBatchFileError", "io.vertx.core.json.JsonObject")
      .handler(r -> ctx.fail("should fail"))
      .exceptionHandler(assertHandleIntentionalError(ctx, "intentional", async));
  }

  @Test
  public void testDistinctBatchReturnedObjectNotModified(TestContext ctx) {
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

    mock.distinctBatch()
      .inCollection("distinctBatch")
      .withFieldName("testDistinctBatch")
      .withResultClassname("io.vertx.core.json.JsonObject")
      .returns(MemoryStream.of(given));

    Flowable.mergeArray(
      db.distinctBatch("distinctBatch", "testDistinctBatch", "io.vertx.core.json.JsonObject").toFlowable(),
      db.distinctBatch("distinctBatch", "testDistinctBatch", "io.vertx.core.json.JsonObject").toFlowable()
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
  public void testDistinctBatchFileReturnedObjectNotModified(TestContext ctx) {
    final Async async = ctx.async(3 * 2);
    final JsonObject expected = new JsonObject().put("field1", "value1");

    Flowable.mergeArray(
      db.distinctBatch("distinctBatch", "testDistinctBatchFile", "io.vertx.core.json.JsonObject").toFlowable(),
      db.distinctBatch("distinctBatch", "testDistinctBatchFile", "io.vertx.core.json.JsonObject").toFlowable()
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

  @Test
  public void testDistinctBatchWithOptions(TestContext ctx) {
    Async async = ctx.async();
    mock.distinctBatch()
      .inCollection("testDistinctBatchWithOptions")
      .withFieldName("testDistinctBatchWithOptions")
      .withResultClassname("io.vertx.core.json.JsonObject")
      .withOptions(new DistinctOptions().setCollation(new CollationOptions().setLocale("no-way")))
      .returns(MemoryStream.of(new JsonObject().put("x", "y")));
    db.distinctBatch("testDistinctBatchWithOptions", "testDistinctBatchWithOptions", "io.vertx.core.json.JsonObject", new DistinctOptions().setCollation(new CollationOptions().setLocale("no-way")))
      .handler(r -> {
        ctx.assertEquals("y", r.getString("x"));
        async.complete();
      }).exceptionHandler(ctx::fail);
  }

  @Test
  public void testDistinctBatchWithOptionsFile(TestContext ctx) {
    Async async = ctx.async(2);
    db.distinctBatch("testDistinctBatchWithOptions", "testDistinctBatchWithOptionsFile", "io.vertx.core.json.JsonObject", new DistinctOptions().setCollation(new CollationOptions().setLocale("de_AT")))
      .handler(r -> {
        switch(async.count()) {
          case 2:
            ctx.assertEquals("value1", r.getString("field1"));
            break;
          case 1:
            ctx.assertEquals("value2", r.getString("field2"));
            break;
          default:
            ctx.fail("unexpected stream");
            break;
        }
        async.countDown();
      }).exceptionHandler(ctx::fail);
  }

  @Test
  public void testDistinctBatchWithOptionsError(TestContext ctx) {
    Async async = ctx.async();
    mock.distinctBatch()
      .inCollection("testDistinctBatchWithOptionsError")
      .withFieldName("testDistinctBatchWithOptionsError")
      .withResultClassname("io.vertx.core.json.JsonObject")
      .withOptions(new DistinctOptions().setCollation(new CollationOptions().setLocale("de_AT")))
      .returnsError(new Exception("intentional"));
    db.distinctBatch("testDistinctBatchWithOptionsError", "testDistinctBatchWithOptionsError", "io.vertx.core.json.JsonObject",
        new DistinctOptions().setCollation(new CollationOptions().setLocale("de_AT")))
      .handler(r -> ctx.fail("should not succeed"))
      .exceptionHandler(assertHandleIntentionalError(ctx, "intentional", async));
  }

  @Test
  public void testDistinctBatchWithOptionsFileError(TestContext ctx) {
    Async async = ctx.async();
    db.distinctBatch("testDistinctBatchWithOptionsFileError", "testDistinctBatchWithOptionsFileError", "io.vertx.core.json.JsonObject", new DistinctOptions().setCollation(new CollationOptions().setLocale("de_AT")))
      .handler(r -> ctx.fail("should not succeed"))
      .exceptionHandler(assertHandleIntentionalError(ctx, "intentional", async));
  }

  @Test
  public void testDistinctBatchWithOptionsNoMatch(TestContext ctx) {
    mock.distinctBatch()
      .inCollection("testDistinctBatchWithOptionsNoMatch")
      .withFieldName("testDistinctBatch")
      .withResultClassname("io.vertx.core.json.JsonObject")
      .withOptions(new DistinctOptions().setCollation(new CollationOptions().setLocale("no-wy")))
      .returns(MemoryStream.of(new JsonObject().put("x", "y")));
    db.distinctBatch("testDistinctBatchWithOptionsNoMatch", "testDistinctBatch", "io.vertx.core.json.JsonObject", new DistinctOptions().setCollation(new CollationOptions().setLocale("no-way")))
      .handler(r -> ctx.fail("should fail"))
      .exceptionHandler(handleNoMappingFoundError(ctx));

  }

}
