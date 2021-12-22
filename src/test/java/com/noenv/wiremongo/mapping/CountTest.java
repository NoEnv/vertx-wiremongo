package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.CollationOptions;
import io.vertx.ext.mongo.CountOptions;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

@RunWith(VertxUnitRunner.class)
public class CountTest extends TestBase {

  @Test
  public void testCount(TestContext ctx) {
    Async async = ctx.async();
    mock.count()
      .inCollection("count")
      .withQuery(new JsonObject().put("test", "testCount"))
      .returns(41L);

    db.rxCount("count", new JsonObject().put("test", "testCount"))
      .subscribe(s -> {
        ctx.assertEquals(41L, s);
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testCountWithOptions(TestContext ctx) {
    Async async = ctx.async();
    mock.countWithOptions()
      .inCollection("countwithoptions")
      .withOptions(new CountOptions().setCollation(new CollationOptions().setLocale("de_AT").setStrength(3)))
      .withQuery(new JsonObject().put("test", "testCount"))
      .returns(41L);

    db.rxCountWithOptions("countwithoptions",
        new JsonObject().put("test", "testCount"),
        new CountOptions().setCollation(new CollationOptions().setLocale("de_AT").setStrength(3)))
      .subscribe(s -> {
        ctx.assertEquals(41L, s);
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testCountFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxCount("count", new JsonObject().put("test", "testCountFile"))
      .subscribe(s -> {
        ctx.assertEquals(26L, s);
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testCountWithOptionsFile(TestContext ctx) {
    Async async = ctx.async();
    // TODO: remove setAlternate after vertx 4.3.0 release
    db.rxCountWithOptions("countwithoptions",
        new JsonObject().put("test", "testCountWithOptionsFile"),
        new CountOptions().setCollation(new CollationOptions().setLocale("de_AT").setStrength(3).setAlternate(null))
      )
      .subscribe(s -> {
        ctx.assertEquals(26L, s);
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testCountError(TestContext ctx) {
    Async async = ctx.async();
    db.rxCount("count", new JsonObject().put("test", "testCountFileError"))
      .subscribe(s -> ctx.fail(), ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }

  @Test
  public void testCountWithOptionsError(TestContext ctx) {
    Async async = ctx.async();
    // TODO: remove setAlternate after vertx 4.3.0 release
    db.rxCountWithOptions("countwithoptions",
        new JsonObject().put("test", "testCountWithOptionsFileError"),
        new CountOptions().setCollation(new CollationOptions().setLocale("de_AT").setStrength(3).setAlternate(null))
      )
      .subscribe(s -> ctx.fail(), ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }

  @Test
  public void testMultipleReturns(TestContext ctx) {
    Async async = ctx.async();
    mock.count()
      .inCollection("double")
      .withQuery(new JsonObject().put("test","next"))
      .returns(10L)
      .returns(20L);

    db.rxCount("double", new JsonObject().put("test","next"))
      .flatMap(first -> {
        ctx.assertEquals(10L, first);
        return db.rxCount("double", new JsonObject().put("test","next"));
      })
      .flatMap(next -> {
        ctx.assertEquals(20L, next);
        return db.rxCount("double", new JsonObject().put("test", "next"));
      })
      .subscribe(next -> {
        ctx.assertEquals(20L, next);
        async.countDown();
    }, ctx::fail);
  }
}
