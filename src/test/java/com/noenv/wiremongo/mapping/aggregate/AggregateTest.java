package com.noenv.wiremongo.mapping.aggregate;

import com.noenv.wiremongo.MemoryStream;
import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
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
}
