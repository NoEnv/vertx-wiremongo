package com.noenv.wiremongo.mapping.distinct;

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
      });
  }

  @Test
  public void testDistinctBatchFileError(TestContext ctx) {
    Async async = ctx.async();
    db.distinctBatch("distinctBatch", "testDistinctBatchFileError", "io.vertx.core.json.JsonObject")
      .exceptionHandler(ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }
}
