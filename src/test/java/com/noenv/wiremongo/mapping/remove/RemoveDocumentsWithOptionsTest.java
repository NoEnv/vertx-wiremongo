package com.noenv.wiremongo.mapping.remove;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientDeleteResult;
import io.vertx.ext.mongo.WriteOption;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class RemoveDocumentsWithOptionsTest extends TestBase {

  @Test
  public void testRemoveDocumentsWithOptions(TestContext ctx) {
    Async async = ctx.async();

    mock.removeDocumentsWithOptions()
      .inCollection("removeDocumentsWithOptions")
      .withQuery(new JsonObject().put("test", "testRemoveDocumentsWithOptions"))
      .withOptions(WriteOption.FSYNCED)
      .returns(new MongoClientDeleteResult(1));

    db.rxRemoveDocumentsWithOptions("removeDocumentsWithOptions", new JsonObject()
      .put("test", "testRemoveDocumentsWithOptions"), WriteOption.FSYNCED)
      .subscribe(r -> {
        ctx.assertEquals(1L, r.getRemovedCount());
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testRemoveDocumentsWithOptionsFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxRemoveDocumentsWithOptions("removeDocumentsWithOptions", new JsonObject()
      .put("test", "testRemoveDocumentsWithOptionsFile"), WriteOption.ACKNOWLEDGED)
      .subscribe(r -> {
        ctx.assertEquals(2L, r.getRemovedCount());
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testRemoveDocumentsWithOptionsFileError(TestContext ctx) {
    Async async = ctx.async();
    db.rxRemoveDocumentsWithOptions("removeDocumentsWithOptions", new JsonObject()
      .put("test", "testRemoveDocumentsWithOptionsFileError"), WriteOption.MAJORITY)
      .subscribe(r -> ctx.fail(), ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }
}
