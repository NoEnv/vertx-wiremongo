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
      }).exceptionHandler(ctx::fail);
  }

  @Test
  public void testDistinctBatchWithQueryFileError(TestContext ctx) {
    Async async = ctx.async();
    db.distinctBatchWithQuery("distinctBatchWithQuery", "testDistinctBatchWithQueryFileError", "io.vertx.core.json.JsonObject", new JsonObject().put("foo", "bar"))
      .handler(r -> ctx.fail("should fail"))
      .exceptionHandler(assertHandleIntentionalError(ctx, "intentional", async));
  }

  @Test
  public void testDistinctBatchWithQueryReturnedObjectNotModified(TestContext ctx) {
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

  @Test
  public void testDistinctBatchWithQueryWithOptions(TestContext ctx) {
    Async async = ctx.async();
    mock.distinctBatchWithQuery()
      .inCollection("testDistinctBatchWithQueryWithOptions")
      .withFieldName("testDistinctBatchWithQueryWithOptions")
      .withQuery(new JsonObject().put("foo", "bar"))
      .withResultClassname("io.vertx.core.json.JsonObject")
      .withOptions(new DistinctOptions().setCollation(new CollationOptions().setLocale("no-way")))
      .returns(MemoryStream.of(new JsonObject().put("x", "y")));
    db.distinctBatchWithQuery("testDistinctBatchWithQueryWithOptions", "testDistinctBatchWithQueryWithOptions",
        "io.vertx.core.json.JsonObject", new JsonObject().put("foo", "bar"), new DistinctOptions().setCollation(new CollationOptions().setLocale("no-way")))
      .handler(r -> {
        ctx.assertEquals("y", r.getString("x"));
        async.complete();
      }).exceptionHandler(ctx::fail);
  }

  @Test
  public void testDistinctBatchWithQueryWithOptionsFile(TestContext ctx) {
    Async async = ctx.async(2);
    db.distinctBatchWithQuery("testDistinctBatchWithQueryWithOptions", "testDistinctBatchWithQueryWithOptionsFile",
        "io.vertx.core.json.JsonObject", new JsonObject().put("foo", "bar"), new DistinctOptions().setCollation(new CollationOptions().setLocale("de_AT")))
      .handler(r -> {
        switch (async.count()) {
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
  public void testDistinctBatchWithQueryWithOptionsError(TestContext ctx) {
    Async async = ctx.async();
    mock.distinctBatchWithQuery()
      .inCollection("testDistinctBatchWithQueryWithOptionsError")
      .withFieldName("testDistinctBatchWithQueryWithOptionsError")
      .withQuery(new JsonObject().put("foo", "bar"))
      .withResultClassname("io.vertx.core.json.JsonObject")
      .withOptions(new DistinctOptions().setCollation(new CollationOptions().setLocale("no-way")))
      .returnsError(new Exception("intentional"));
    db.distinctBatchWithQuery("testDistinctBatchWithQueryWithOptionsError", "testDistinctBatchWithQueryWithOptionsError",
        "io.vertx.core.json.JsonObject", new JsonObject().put("foo", "bar"), new DistinctOptions().setCollation(new CollationOptions().setLocale("no-way")))
      .handler(r -> ctx.fail("should fail"))
      .exceptionHandler(assertHandleIntentionalError(ctx, "intentional", async));
  }

  @Test
  public void testDistinctBatchWithQueryWithOptionsFileError(TestContext ctx) {
    Async async = ctx.async();
    db.distinctBatchWithQuery("testDistinctBatchWithQueryWithOptionsError", "testDistinctBatchWithQueryWithOptionsFileError",
        "io.vertx.core.json.JsonObject", new JsonObject().put("foo", "bar"), new DistinctOptions().setCollation(new CollationOptions().setLocale("de_AT")))
      .handler(r -> ctx.fail("should fail"))
      .exceptionHandler(assertHandleIntentionalError(ctx, "intentional", async));
  }

  @Test
  public void testDistinctBatchWithQueryWithOptionsNoMatch(TestContext ctx) {
    mock.distinctBatchWithQuery()
      .inCollection("testDistinctBatchWithQueryWithOptionsNoMatch")
      .withFieldName("testDistinctBatchWithQuery")
      .withQuery(new JsonObject().put("foo", "bar"))
      .withResultClassname("io.vertx.core.json.JsonObject")
      .withOptions(new DistinctOptions().setCollation(new CollationOptions().setLocale("no-wy")))
      .returns(MemoryStream.of(new JsonObject().put("x", "y")));
    db.distinctBatchWithQuery("testDistinctBatchWithQueryWithOptionsNoMatch", "testDistinctBatchWithQuery",
        "io.vertx.core.json.JsonObject", new JsonObject().put("foo", "bar"), new DistinctOptions().setCollation(new CollationOptions().setLocale("no-way")))
      .handler(r -> ctx.fail("should fail"))
      .exceptionHandler(handleNoMappingFoundError(ctx));
  }

  @Test
  public void testDistinctBatchWithQueryWithOptionsAndBatchSize(TestContext ctx) {
    Async async = ctx.async();
    mock.distinctBatchWithQuery()
      .inCollection("testDistinctBatchWithQueryWithOptions")
      .withFieldName("testDistinctBatchWithQuery")
      .withQuery(new JsonObject().put("foo", "bar"))
      .withBatchSize(25)
      .withResultClassname("io.vertx.core.json.JsonObject")
      .withOptions(new DistinctOptions().setCollation(new CollationOptions().setLocale("no-way")))
      .returns(MemoryStream.of(new JsonObject().put("x", "y")));
    db.distinctBatchWithQuery("testDistinctBatchWithQueryWithOptions", "testDistinctBatchWithQuery",
        "io.vertx.core.json.JsonObject", new JsonObject().put("foo", "bar"), 25, new DistinctOptions().setCollation(new CollationOptions().setLocale("no-way")))
      .handler(r -> {
        ctx.assertEquals("y", r.getString("x"));
        async.complete();
      }).exceptionHandler(ctx::fail);
  }

  @Test
  public void testDistinctBatchWithQueryWithBatchSize(TestContext ctx) {
    Async async = ctx.async();
    mock.distinctBatchWithQuery()
      .inCollection("testDistinctBatchWithQueryWithOptions")
      .withFieldName("testDistinctBatchWithQuery")
      .withQuery(new JsonObject().put("foo", "bar"))
      .withBatchSize(25)
      .withResultClassname("io.vertx.core.json.JsonObject")
      .returns(MemoryStream.of(new JsonObject().put("x", "y")));
    db.distinctBatchWithQuery("testDistinctBatchWithQueryWithOptions", "testDistinctBatchWithQuery",
        "io.vertx.core.json.JsonObject", new JsonObject().put("foo", "bar"), 25)
      .handler(r -> {
        ctx.assertEquals("y", r.getString("x"));
        async.complete();
      }).exceptionHandler(ctx::fail);
  }

}
