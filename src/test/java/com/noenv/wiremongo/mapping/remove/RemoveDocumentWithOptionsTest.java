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
public class RemoveDocumentWithOptionsTest extends TestBase {

  @Test
  public void testRemoveDocumentWithOptions(TestContext ctx) {
    mock.removeDocumentWithOptions()
      .inCollection("removeDocumentWithOptions")
      .withQuery(new JsonObject().put("test", "testRemoveDocumentWithOptions"))
      .withOptions(WriteOption.MAJORITY)
      .returns(new MongoClientDeleteResult(1));

    db.rxRemoveDocumentWithOptions("removeDocumentWithOptions", new JsonObject()
      .put("test", "testRemoveDocumentWithOptions"), WriteOption.MAJORITY)
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r -> ctx.assertEquals(1L, r.getRemovedCount()))));
  }

  @Test
  public void testRemoveDocumentWithOptionsFile(TestContext ctx) {
    db.rxRemoveDocumentWithOptions("removeDocumentWithOptions", new JsonObject()
      .put("test", "testRemoveDocumentWithOptionsFile"), WriteOption.REPLICA_ACKNOWLEDGED)
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r -> ctx.assertEquals(2L, r.getRemovedCount()))));
  }

  @Test
  public void testRemoveDocumentWithOptionsError(TestContext ctx) {
    mock.removeDocumentWithOptions()
      .inCollection("removeDocumentWithOptions")
      .withQuery(new JsonObject().put("test", "testRemoveDocumentWithOptionsError"))
      .withOptions(WriteOption.FSYNCED)
      .returnsError(new Exception("intentional"));

    db.rxRemoveDocumentWithOptions("removeDocumentWithOptions", new JsonObject()
        .put("test", "testRemoveDocumentWithOptionsError"), WriteOption.FSYNCED)
      .doOnError(assertIntentionalError(ctx, "intentional"))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertFailure()));
  }

  @Test
  public void testRemoveDocumentWithOptionsFileError(TestContext ctx) {
    db.rxRemoveDocumentWithOptions("removeDocumentWithOptions", new JsonObject()
      .put("test", "testRemoveDocumentWithOptionsFileError"), WriteOption.UNACKNOWLEDGED)
      .doOnError(assertIntentionalError(ctx, "intentional"))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertFailure()));
  }
}
