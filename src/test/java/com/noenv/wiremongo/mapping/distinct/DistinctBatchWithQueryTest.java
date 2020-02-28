package com.noenv.wiremongo.mapping.distinct;

import com.noenv.wiremongo.MemoryStream;
import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
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
      });
  }

  @Test
  public void testDistinctBatchWithQueryFileError(TestContext ctx) {
    Async async = ctx.async();
    db.distinctBatchWithQuery("distinctBatchWithQuery", "testDistinctBatchWithQueryFileError", "io.vertx.core.json.JsonObject", new JsonObject().put("foo", "bar"))
      .exceptionHandler(ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }
}
