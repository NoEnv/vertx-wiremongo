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
public class RemoveDocumentsTest extends TestBase {

  @Test
  public void testRemoveDocuments(TestContext ctx) {
    mock.removeDocuments()
      .inCollection("removeDocuments")
      .withQuery(new JsonObject().put("test", "testRemoveDocuments"))
      .returns(new MongoClientDeleteResult(1));

    db.rxRemoveDocuments("removeDocuments", new JsonObject().put("test", "testRemoveDocuments"))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r ->
        ctx.assertEquals(1L, r.getRemovedCount())
      )));
  }

  @Test
  public void testRemoveDocumentsFile(TestContext ctx) {
    db.rxRemoveDocuments("removeDocuments", new JsonObject().put("test", "testRemoveDocumentsFile"))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r ->
        ctx.assertEquals(2L, r.getRemovedCount())
      )));
  }

  @Test
  public void testRemoveDocumentsFileError(TestContext ctx) {
    db.rxRemoveDocuments("removeDocuments", new JsonObject().put("test", "testRemoveDocumentsFileError"))
      .doOnError(assertIntentionalError(ctx, "intentional"))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertFailure()));
  }
}
