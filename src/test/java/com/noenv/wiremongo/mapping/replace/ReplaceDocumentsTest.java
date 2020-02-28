package com.noenv.wiremongo.mapping.replace;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientUpdateResult;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class ReplaceDocumentsTest extends TestBase {

  @Test
  public void testReplaceDocuments(TestContext ctx) {
    Async async = ctx.async();

    mock.replaceDocuments()
      .inCollection("replaceDocuments")
      .withQuery(new JsonObject().put("test", "testReplaceDocuments"))
      .withReplace(new JsonObject().put("foo", "bar"))
      .returns(new MongoClientUpdateResult(17, null, 24));

    db.rxReplaceDocuments("replaceDocuments", new JsonObject().put("test", "testReplaceDocuments"),
      new JsonObject().put("foo", "bar"))
      .subscribe(r -> {
        ctx.assertEquals(17L, r.getDocMatched());
        ctx.assertEquals(24L, r.getDocModified());
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testReplaceDocumentsFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxReplaceDocuments("replaceDocuments", new JsonObject().put("test", "testReplaceDocumentsFile"),
      new JsonObject().put("foo", "bar"))
      .subscribe(r -> {
        ctx.assertEquals(21L, r.getDocMatched());
        ctx.assertEquals(56L, r.getDocModified());
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testReplaceDocumentsFileError(TestContext ctx) {
    Async async = ctx.async();
    db.rxReplaceDocuments("replaceDocuments", new JsonObject().put("test", "testReplaceDocumentsFileError"),
      new JsonObject().put("foo", "bar"))
      .subscribe(r -> ctx.fail(), ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }
}
