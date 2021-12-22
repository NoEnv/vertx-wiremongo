package com.noenv.wiremongo.mapping.remove;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientDeleteResult;
import io.vertx.ext.mongo.WriteOption;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.rxjava3.MaybeHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class RemoveDocumentsWithOptionsTest extends TestBase {

  @Test
  public void testRemoveDocumentsWithOptions(TestContext ctx) {
    mock.removeDocumentsWithOptions()
      .inCollection("removeDocumentsWithOptions")
      .withQuery(new JsonObject().put("test", "testRemoveDocumentsWithOptions"))
      .withOptions(WriteOption.FSYNCED)
      .returns(new MongoClientDeleteResult(1));

    db.rxRemoveDocumentsWithOptions("removeDocumentsWithOptions", new JsonObject()
      .put("test", "testRemoveDocumentsWithOptions"), WriteOption.FSYNCED)
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r -> ctx.assertEquals(1L, r.getRemovedCount()))));
  }

  @Test
  public void testRemoveDocumentsWithOptionsFile(TestContext ctx) {
    db.rxRemoveDocumentsWithOptions("removeDocumentsWithOptions", new JsonObject()
      .put("test", "testRemoveDocumentsWithOptionsFile"), WriteOption.ACKNOWLEDGED)
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r -> ctx.assertEquals(2L, r.getRemovedCount()))));
  }

  @Test
  public void testRemoveDocumentsWithOptionsError(TestContext ctx) {
    mock.removeDocumentsWithOptions()
      .inCollection("removeDocumentsWithOptions")
      .withQuery(new JsonObject().put("test", "testRemoveDocumentsWithOptionsError"))
      .withOptions(WriteOption.FSYNCED)
      .returnsError(new Exception("intentional"));

    db.rxRemoveDocumentsWithOptions("removeDocumentsWithOptions", new JsonObject()
        .put("test", "testRemoveDocumentsWithOptionsError"), WriteOption.FSYNCED)
      .doOnError(assertIntentionalError(ctx, "intentional"))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertFailure()));
  }

  @Test
  public void testRemoveDocumentsWithOptionsFileError(TestContext ctx) {
    db.rxRemoveDocumentsWithOptions("removeDocumentsWithOptions", new JsonObject()
      .put("test", "testRemoveDocumentsWithOptionsFileError"), WriteOption.MAJORITY)
      .doOnError(assertIntentionalError(ctx, "intentional"))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertFailure()));
  }
}
