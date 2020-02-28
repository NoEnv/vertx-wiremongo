package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.MemoryStream;
import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class FindBatchWithOptionsTest extends TestBase {

  @Test
  public void testFindBatchWithOptions(TestContext ctx) {
    Async async = ctx.async();

    mock.findBatchWithOptions()
      .inCollection("findbatchwithoptions")
      .withQuery(new JsonObject().put("test", "testFindBatchWithOptions"))
      .withOptions(new FindOptions().setLimit(17))
      .returns(MemoryStream.of(new JsonObject().put("field1", "value1")));

    db.findBatchWithOptions("findbatchwithoptions", new JsonObject().put("test", "testFindBatchWithOptions"), new FindOptions().setLimit(17))
      .handler(r -> {
        ctx.assertEquals("value1", r.getString("field1"));
        async.complete();
      });
  }

  @Test
  public void testFindBatchWithOptionsError(TestContext ctx) {
    Async async = ctx.async();

    mock.findBatchWithOptions()
      .inCollection("findbatchwithoptions")
      .withQuery(new JsonObject().put("test", "testFindBatchWithOptionsError"))
      .withOptions(new FindOptions().setLimit(19))
      .returnsError(new Exception("intentional"));

    db.findBatchWithOptions("findbatchwithoptions", new JsonObject().put("test", "testFindBatchWithOptionsError"), new FindOptions().setLimit(19))
      .exceptionHandler(ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }

  @Test
  public void testFindBatchWithOptionsFile(TestContext ctx) {
    Async async = ctx.async(3);
    db.findBatchWithOptions("findbatchwithoptions", new JsonObject().put("test", "testFindBatchWithOptionsFile"), new FindOptions().setSkip(21))
      .handler(r -> {
        ctx.assertEquals("value1", r.getString("field1"));
        async.countDown();
      });
  }

  @Test
  public void testFindBatchWithOptionsFileError(TestContext ctx) {
    Async async = ctx.async();
    db.findBatchWithOptions("findbatchwithoptions", new JsonObject().put("test", "testFindBatchWithOptionsFileError"), new FindOptions().setSkip(27))
      .exceptionHandler(ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }
}
