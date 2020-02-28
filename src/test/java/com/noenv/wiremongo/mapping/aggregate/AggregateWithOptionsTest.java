package com.noenv.wiremongo.mapping.aggregate;

import com.noenv.wiremongo.MemoryStream;
import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.AggregateOptions;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class AggregateWithOptionsTest extends TestBase {

  @Test
  public void testAggregateWithOptions(TestContext ctx) {
    Async async = ctx.async();

    mock.aggregateWithOptions()
      .inCollection("aggregateWithOptions")
      .withPipeline(new JsonArray().add(new JsonObject().put("$match",new JsonObject().put("id","myId"))))
      .withOptions(new AggregateOptions().setAllowDiskUse(true).setMaxTime(2345))
      .returns(MemoryStream.of(new JsonObject().put("x", "y")));

    db.aggregateWithOptions("aggregateWithOptions",
      new JsonArray().add(new JsonObject().put("$match",new JsonObject().put("id","myId"))),
      new AggregateOptions().setAllowDiskUse(true).setMaxTime(2345))
      .handler(r -> {
        ctx.assertEquals("y", r.getString("x"));
        async.complete();
      }).exceptionHandler(ctx::fail);
  }

  @Test
  public void testAggregateWithOptionsFile(TestContext ctx) {
    Async async = ctx.async(3);
    db.aggregateWithOptions("aggregateWithOptions",
      new JsonArray().add(new JsonObject().put("test", "testAggregateWithOptionsFile")),
      new AggregateOptions().setAllowDiskUse(false).setMaxTime(345))
      .handler(r -> {
        ctx.assertEquals("value1", r.getString("field1"));
        async.countDown();
      });
  }

  @Test
  public void testAggregateWithOptionsFileError(TestContext ctx) {
    Async async = ctx.async();
    db.aggregateWithOptions("aggregateWithOptions",
      new JsonArray().add(new JsonObject().put("test", "testAggregateWithOptionsFileError")),
      new AggregateOptions().setAllowDiskUse(false).setMaxTime(234))
      .exceptionHandler(ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }
}
