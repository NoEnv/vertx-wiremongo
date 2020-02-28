package com.noenv.wiremongo.mapping.replace;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientUpdateResult;
import io.vertx.ext.mongo.UpdateOptions;
import io.vertx.ext.mongo.WriteOption;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class ReplaceDocumentsWithOptionsTest extends TestBase {

  @Test
  public void testReplaceDocumentsWithOptions(TestContext ctx) {
    Async async = ctx.async();

    mock.replaceDocumentsWithOptions()
      .inCollection("replaceDocumentsWithOptions")
      .withQuery(new JsonObject().put("test", "testReplaceDocumentsWithOptions"))
      .withReplace(new JsonObject().put("foo", "bar"))
      .withOptions(new UpdateOptions(true, false))
      .returns(new MongoClientUpdateResult(17, null, 24));

    db.rxReplaceDocumentsWithOptions("replaceDocumentsWithOptions",
      new JsonObject().put("test", "testReplaceDocumentsWithOptions"),
      new JsonObject().put("foo", "bar"), new UpdateOptions(true, false))
      .subscribe(r -> {
        ctx.assertEquals(17L, r.getDocMatched());
        ctx.assertEquals(24L, r.getDocModified());
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testReplaceDocumentsWithOptionsFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxReplaceDocumentsWithOptions("replaceDocumentsWithOptions",
      new JsonObject().put("test", "testReplaceDocumentsWithOptionsFile"),
      new JsonObject().put("foo", "bar"), new UpdateOptions(false, true))
      .subscribe(r -> {
        ctx.assertEquals(21L, r.getDocMatched());
        ctx.assertEquals(56L, r.getDocModified());
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testReplaceDocumentsWithOptionsFileError(TestContext ctx) {
    Async async = ctx.async();
    db.rxReplaceDocumentsWithOptions("replaceDocumentsWithOptions",
      new JsonObject().put("test", "testReplaceDocumentsWithOptionsFileError"),
      new JsonObject().put("foo", "bar"), new UpdateOptions().setWriteOption(WriteOption.MAJORITY))
      .subscribe(r -> ctx.fail(), ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }
}
