package com.noenv.wiremongo.mapping.count;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

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
  public void testCountFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxCount("count", new JsonObject().put("test", "testCountFile"))
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
