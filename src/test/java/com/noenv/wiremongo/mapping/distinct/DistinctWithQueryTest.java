package com.noenv.wiremongo.mapping.distinct;

import com.noenv.wiremongo.TestBase;
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
public class DistinctWithQueryTest extends TestBase {

  @Test
  public void testDistinctWithQuery(TestContext ctx) {
    Async async = ctx.async();

    mock.distinctWithQuery()
      .inCollection("distinctWithQuery")
      .withFieldName("testDistinctWithQuery")
      .withQuery(new JsonObject().put("foo", "bar"))
      .returns(new JsonArray().add("A").add("B"));

    db.rxDistinctWithQuery("distinctWithQuery", "testDistinctWithQuery",
        null, new JsonObject().put("foo", "bar"))
      .subscribe(r -> {
        ctx.assertEquals(2, r.size());
        ctx.assertEquals("A", r.getString(0));
        ctx.assertEquals("B", r.getString(1));
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testDistinctWithQueryFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxDistinctWithQuery("distinctWithQuery", "testDistinctWithQueryFile",
        "java.lang.String", new JsonObject().put("foo", "bar"))
      .subscribe(r -> {
        ctx.assertEquals(3, r.size());
        ctx.assertEquals("A", r.getString(0));
        ctx.assertEquals("B", r.getString(1));
        ctx.assertEquals("C", r.getString(2));
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testDistinctWithQueryFileError(TestContext ctx) {
    Async async = ctx.async();
    db.rxDistinctWithQuery("distinctWithQuery", "testDistinctWithQueryFileError",
        "java.lang.Integer", new JsonObject().put("foo", "bar"))
      .subscribe(r -> ctx.fail(), ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }

  @Test
  public void testDistinctWithQueryReturnedObjectNotModified(TestContext ctx) {
    final JsonArray given = new JsonArray().add("value1").add("value2");
    final JsonArray expected = given.copy();

    mock.distinctWithQuery()
      .inCollection("distinctWithQuery")
      .withFieldName("testDistinctWithQuery")
      .withQuery(new JsonObject().put("foo", "bar"))
      .returns(given);

    db.rxDistinctWithQuery("distinctWithQuery", "testDistinctWithQuery", null, new JsonObject().put("foo", "bar"))
      .doOnSuccess(actual -> ctx.assertEquals(expected, actual))
      .doOnSuccess(actual -> {
        actual.remove(0);
        actual.add("add");
      })
      .repeat(2)
      .ignoreElements()
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testDistinctWithQueryFileReturnedObjectNotModified(TestContext ctx) {
    final JsonArray expected = new JsonArray().add("A").add("B").add("C");

    db.rxDistinctWithQuery("distinctWithQuery", "testDistinctWithQueryFile", "java.lang.String", new JsonObject().put("foo", "bar"))
      .doOnSuccess(actual -> ctx.assertEquals(expected, actual))
      .doOnSuccess(actual -> {
        actual.remove(0);
        actual.add("add");
      })
      .repeat(2)
      .ignoreElements()
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testDistinctWithQueryWithOptions(TestContext ctx) {
    Async async = ctx.async();

    mock.distinctWithQuery()
      .inCollection("distinctWithQuery")
      .withFieldName("testDistinctWithQuery")
      .withQuery(new JsonObject().put("foo", "bar"))
      .withOptions(new DistinctOptions().setCollation(new CollationOptions().setLocale("no-way")))
      .returns(new JsonArray().add("A").add("B"));

    db.distinctWithQuery("distinctWithQuery", "testDistinctWithQuery",
      null, new JsonObject().put("foo", "bar"), result -> {
        if (result.failed()) {
          ctx.fail(result.cause());
        } else {
          JsonArray r = result.result();
          ctx.assertEquals(2, r.size());
          ctx.assertEquals("A", r.getString(0));
          ctx.assertEquals("B", r.getString(1));
          async.complete();
        }
      }, new DistinctOptions().setCollation(new CollationOptions().setLocale("no-way"))
    );
  }

}
