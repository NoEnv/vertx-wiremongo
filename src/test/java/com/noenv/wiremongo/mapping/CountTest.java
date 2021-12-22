package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.CollationOptions;
import io.vertx.ext.mongo.CountOptions;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.rxjava3.SingleHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class CountTest extends TestBase {

  @Test
  public void testCount(TestContext ctx) {
    mock.count()
      .inCollection("count")
      .withQuery(new JsonObject().put("test", "testCount"))
      .returns(41L);

    db.count("count", new JsonObject().put("test", "testCount"))
      .subscribe(SingleHelper.toObserver(ctx.asyncAssertSuccess(s ->
        ctx.assertEquals(41L, s)
      )));
  }

  @Test
  public void testCountWithOptions(TestContext ctx) {
    mock.countWithOptions()
      .inCollection("countwithoptions")
      .withOptions(new CountOptions().setCollation(new CollationOptions().setLocale("de_AT").setStrength(3)))
      .withQuery(new JsonObject().put("test", "testCount"))
      .returns(41L);

    db.rxCountWithOptions("countwithoptions",
        new JsonObject().put("test", "testCount"),
        new CountOptions().setCollation(new CollationOptions().setLocale("de_AT").setStrength(3)))
      .subscribe(SingleHelper.toObserver(ctx.asyncAssertSuccess(s ->
        ctx.assertEquals(41L, s)
      )));
  }

  @Test
  public void testCountFile(TestContext ctx) {
    db.count("count", new JsonObject().put("test", "testCountFile"))
      .subscribe(SingleHelper.toObserver(ctx.asyncAssertSuccess(s ->
        ctx.assertEquals(26L, s)
      )));
  }

  @Test
  public void testCountWithOptionsFile(TestContext ctx) {
    db.countWithOptions("countwithoptions",
        new JsonObject().put("test", "testCountWithOptionsFile"),
        new CountOptions().setCollation(new CollationOptions())
      )
      .subscribe(SingleHelper.toObserver(ctx.asyncAssertSuccess(s ->
        ctx.assertEquals(26L, s)
      )));
  }

  @Test
  public void testCountError(TestContext ctx) {
    mock.count()
      .inCollection("count")
      .withQuery(new JsonObject().put("test", "testCountError"))
      .returnsError(new Exception("intentional"));

    db.count("count", new JsonObject().put("test", "testCountError"))
      .doOnError(assertIntentionalError(ctx, "intentional"))
      .subscribe(SingleHelper.toObserver(ctx.asyncAssertFailure()));
  }

  @Test
  public void testCountWithOptionsError(TestContext ctx) {
    mock.countWithOptions()
      .inCollection("countwithoptions")
      .withQuery(new JsonObject().put("test", "testCountWithOptionsError"))
      .withOptions(new CountOptions())
      .returnsError(new Exception("intentional"));

    db.countWithOptions("countwithoptions",
        new JsonObject().put("test", "testCountWithOptionsError"),
        new CountOptions()
      )
      .doOnError(err -> ctx.assertEquals("intentional", err.getMessage()))
      .subscribe(SingleHelper.toObserver(ctx.asyncAssertFailure()));
  }

  @Test
  public void testCountWithOptionsErrorFile(TestContext ctx) {
    db.countWithOptions("countwithoptions",
        new JsonObject().put("test", "testCountWithOptionsFileError"),
        new CountOptions().setCollation(new CollationOptions().setLocale("de_AT"))
      )
      .doOnError(err -> ctx.assertEquals("intentional", err.getMessage()))
      .subscribe(SingleHelper.toObserver(ctx.asyncAssertFailure()));
  }

  @Test
  public void testMultipleReturns(TestContext ctx) {
    mock.count()
      .inCollection("double")
      .withQuery(new JsonObject().put("test", "next"))
      .returns(10L)
      .returns(20L);

    db.rxCount("double", new JsonObject().put("test", "next"))
      .flatMap(first -> {
        ctx.assertEquals(10L, first);
        return db.rxCount("double", new JsonObject().put("test", "next"));
      })
      .flatMap(next -> {
        ctx.assertEquals(20L, next);
        return db.rxCount("double", new JsonObject().put("test", "next"));
      })
      .subscribe(SingleHelper.toObserver(ctx.asyncAssertSuccess(last -> ctx.assertEquals(20L, last))));
  }
}
