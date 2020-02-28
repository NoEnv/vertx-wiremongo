package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.MemoryStream;
import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class FindBatchTest extends TestBase {

  @Test
  public void testFindBatch(TestContext ctx) {
    Async async = ctx.async();

    mock.findBatch()
      .inCollection("findbatch")
      .withQuery(new JsonObject().put("test", "testFindBatch"))
      .returns(MemoryStream.of(new JsonObject().put("field1", "value1")));

    db.findBatch("findbatch", new JsonObject().put("test", "testFindBatch"))
      .handler(r -> {
        ctx.assertEquals("value1", r.getString("field1"));
        async.complete();
      });
  }

  @Test
  public void testFindBatchError(TestContext ctx) {
    Async async = ctx.async();

    mock.findBatch()
      .inCollection("findbatch")
      .withQuery(new JsonObject().put("test", "testFindBatchError"))
      .returnsError(new Exception("intentional"));

    db.findBatch("findbatch", new JsonObject().put("test", "testFindBatchError"))
      .exceptionHandler(ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }

  @Test
  public void testFindBatchFile(TestContext ctx) {
    Async async = ctx.async(3);
    db.findBatch("findbatch", new JsonObject().put("test", "testFindBatchFile"))
      .handler(r -> {
        ctx.assertEquals("value1", r.getString("field1"));
        async.countDown();
      });
  }

  @Test
  public void testFindBatchFileError(TestContext ctx) {
    Async async = ctx.async();
    db.findBatch("findbatch", new JsonObject().put("test", "testFindBatchFileError"))
      .exceptionHandler(ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }
}
