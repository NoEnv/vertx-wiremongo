package com.noenv.wiremongo.mapping.remove;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientDeleteResult;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.rxjava3.MaybeHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class RemoveDocumentTest extends TestBase {

  @Test
  public void testRemoveDocument(TestContext ctx) {
    mock.removeDocument()
      .inCollection("removeDocument")
      .withQuery(new JsonObject().put("test", "testRemoveDocument"))
      .returns(new MongoClientDeleteResult(1));

    db.rxRemoveDocument("removeDocument", new JsonObject().put("test", "testRemoveDocument"))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r ->
        ctx.assertEquals(1L, r.getRemovedCount())
      )));
  }

  @Test
  public void testRemoveDocumentFile(TestContext ctx) {
    db.rxRemoveDocument("removeDocument", new JsonObject().put("test", "testRemoveDocumentFile"))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r ->
        ctx.assertEquals(2L, r.getRemovedCount())
      )));
  }

  @Test
  public void testRemoveDocumentFileError(TestContext ctx) {
    db.rxRemoveDocument("removeDocument", new JsonObject().put("test", "testRemoveDocumentFileError"))
      .doOnError(assertIntentionalError(ctx, "intentional"))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertFailure()));
  }
}
