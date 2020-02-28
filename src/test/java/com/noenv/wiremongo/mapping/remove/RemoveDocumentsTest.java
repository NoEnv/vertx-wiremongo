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
public class RemoveDocumentsTest extends TestBase {

  @Test
  public void testRemoveDocuments(TestContext ctx) {
    Async async = ctx.async();

    mock.removeDocuments()
      .inCollection("removeDocuments")
      .withQuery(new JsonObject().put("test", "testRemoveDocuments"))
      .returns(new MongoClientDeleteResult(1));

    db.rxRemoveDocuments("removeDocuments", new JsonObject().put("test", "testRemoveDocuments"))
      .subscribe(r -> {
        ctx.assertEquals(1L, r.getRemovedCount());
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testRemoveDocumentsFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxRemoveDocuments("removeDocuments", new JsonObject().put("test", "testRemoveDocumentsFile"))
      .subscribe(r -> {
        ctx.assertEquals(2L, r.getRemovedCount());
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testRemoveDocumentsFileError(TestContext ctx) {
    Async async = ctx.async();
    db.rxRemoveDocuments("removeDocuments", new JsonObject().put("test", "testRemoveDocumentsFileError"))
      .subscribe(r -> ctx.fail(), ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }
}
