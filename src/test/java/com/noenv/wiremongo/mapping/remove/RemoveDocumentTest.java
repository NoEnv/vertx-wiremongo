package com.noenv.wiremongo.mapping.remove;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientDeleteResult;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class RemoveDocumentTest extends TestBase {

  @Test
  public void testRemoveDocument(TestContext ctx) {
    Async async = ctx.async();

    mock.removeDocument()
      .inCollection("removeDocument")
      .withQuery(new JsonObject().put("test", "testRemoveDocument"))
      .returns(new MongoClientDeleteResult(1));

    db.rxRemoveDocument("removeDocument", new JsonObject().put("test", "testRemoveDocument"))
      .subscribe(r -> {
        ctx.assertEquals(1L, r.getRemovedCount());
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testRemoveDocumentFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxRemoveDocument("removeDocument", new JsonObject().put("test", "testRemoveDocumentFile"))
      .subscribe(r -> {
        ctx.assertEquals(2L, r.getRemovedCount());
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testRemoveDocumentFileError(TestContext ctx) {
    Async async = ctx.async();
    db.rxRemoveDocument("removeDocument", new JsonObject().put("test", "testRemoveDocumentFileError"))
      .subscribe(r -> ctx.fail(), ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }
}
