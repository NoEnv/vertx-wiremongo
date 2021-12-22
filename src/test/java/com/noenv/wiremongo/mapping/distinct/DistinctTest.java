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
public class DistinctTest extends TestBase {

  @Test
  public void testDistinct(TestContext ctx) {
    Async async = ctx.async();

    mock.distinct()
      .inCollection("distinct")
      .withFieldName("testDistinct")
      .returns(new JsonArray().add("A").add("B"));

    db.rxDistinct("distinct", "testDistinct", null)
      .subscribe(r -> {
        ctx.assertEquals(2, r.size());
        ctx.assertEquals("A", r.getString(0));
        ctx.assertEquals("B", r.getString(1));
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testDistinctFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxDistinct("distinct", "testDistinctFile", "java.lang.String")
      .subscribe(r -> {
        ctx.assertEquals(3, r.size());
        ctx.assertEquals("A", r.getString(0));
        ctx.assertEquals("B", r.getString(1));
        ctx.assertEquals("C", r.getString(2));
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testDistinctFileError(TestContext ctx) {
    Async async = ctx.async();
    db.rxDistinct("distinct", "testDistinctFileError", "java.lang.Integer")
      .subscribe(r -> ctx.fail(), ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }

  @Test
  public void testDistinctReturnedObjectNotModified(TestContext ctx) {
    final JsonArray given = new JsonArray().add("value1").add("value2");
    final JsonArray expected = given.copy();

    mock.distinct()
      .inCollection("distinct")
      .withFieldName("testDistinct")
      .returns(given);

    db.rxDistinct("distinct", "testDistinct", null)
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
  public void testDistinctFileReturnedObjectNotModified(TestContext ctx) {
    final JsonArray expected = new JsonArray().add("A").add("B").add("C");

    db.rxDistinct("distinct", "testDistinctFile", "java.lang.String")
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
  public void testDistinctWithOptions(TestContext ctx) {
    Async async = ctx.async();

    mock.distinct()
      .inCollection("testDistinctWithOptions")
      .withFieldName("testDistinct")
      .withOptions(new DistinctOptions().setCollation(new CollationOptions().setLocale("no-way")))
      .returns(new JsonArray().add("A").add("B"));

    db.distinct("testDistinctWithOptions", "testDistinct", null, result -> {
      if(result.failed()) {
        ctx.fail(result.cause());
      } else {
        JsonArray r = result.result();
        ctx.assertEquals(2, r.size());
        ctx.assertEquals("A", r.getString(0));
        ctx.assertEquals("B", r.getString(1));
        async.complete();
      }
    }, new DistinctOptions().setCollation(new CollationOptions().setLocale("no-way")));
  }

  @Test
  public void testDistinctWithOptionsNoMatch(TestContext ctx) {
    Async async = ctx.async();

    mock.distinct()
      .inCollection("testDistinctWithOptionsNoMatch")
      .withFieldName("testDistinct")
      .withOptions(new DistinctOptions().setCollation(new CollationOptions().setLocale("no-wy")))
      .returns(new JsonArray().add("A").add("B"));

    db.distinct("testDistinctWithOptionsNoMatch", "testDistinct", null, result -> {
      if(result.failed()) {
        async.complete();
      } else {
        ctx.fail("should fail");
      }
    }, new DistinctOptions().setCollation(new CollationOptions().setLocale("no-way")));
  }
}
