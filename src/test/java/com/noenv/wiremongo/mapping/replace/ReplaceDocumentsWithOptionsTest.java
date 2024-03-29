package com.noenv.wiremongo.mapping.replace;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientUpdateResult;
import io.vertx.ext.mongo.UpdateOptions;
import io.vertx.ext.mongo.WriteOption;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.rxjava3.CompletableHelper;
import io.vertx.rxjava3.MaybeHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class ReplaceDocumentsWithOptionsTest extends TestBase {

  @Test
  public void testReplaceDocumentsWithOptions(TestContext ctx) {
    mock.replaceDocumentsWithOptions()
      .inCollection("replaceDocumentsWithOptions")
      .withQuery(new JsonObject().put("test", "testReplaceDocumentsWithOptions"))
      .withReplace(new JsonObject().put("foo", "bar"))
      .withOptions(new UpdateOptions(true, false))
      .returns(new MongoClientUpdateResult(17, null, 24));

    db.rxReplaceDocumentsWithOptions("replaceDocumentsWithOptions",
        new JsonObject().put("test", "testReplaceDocumentsWithOptions"),
        new JsonObject().put("foo", "bar"), new UpdateOptions(true, false))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r -> {
        ctx.assertEquals(17L, r.getDocMatched());
        ctx.assertEquals(24L, r.getDocModified());
      })));
  }

  @Test
  public void testReplaceDocumentsWithOptionsFile(TestContext ctx) {
    db.rxReplaceDocumentsWithOptions("replaceDocumentsWithOptions",
        new JsonObject().put("test", "testReplaceDocumentsWithOptionsFile"),
        new JsonObject().put("foo", "bar"), new UpdateOptions(false, true))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r -> {
        ctx.assertEquals(21L, r.getDocMatched());
        ctx.assertEquals(56L, r.getDocModified());
      })));
  }

  @Test
  public void testReplaceDocumentsWithOptionsError(TestContext ctx) {
    mock.replaceDocumentsWithOptions()
      .inCollection("replaceDocumentsWithOptionsError")
      .withQuery(new JsonObject().put("test", "testReplaceDocumentsWithOptionsError"))
      .withReplace(new JsonObject().put("foo", "bar"))
      .withOptions(new UpdateOptions(true, false))
      .returnsError(new Exception("intentional"));

    db.rxReplaceDocumentsWithOptions("replaceDocumentsWithOptionsError",
        new JsonObject().put("test", "testReplaceDocumentsWithOptionsError"),
        new JsonObject().put("foo", "bar"), new UpdateOptions(true, false))
      .doOnError(assertIntentionalError(ctx, "intentional"))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertFailure()));
  }


  @Test
  public void testReplaceDocumentsWithOptionsFileError(TestContext ctx) {
    db.rxReplaceDocumentsWithOptions("replaceDocumentsWithOptions",
        new JsonObject().put("test", "testReplaceDocumentsWithOptionsFileError"),
        new JsonObject().put("foo", "bar"), new UpdateOptions().setWriteOption(WriteOption.MAJORITY))
      .doOnError(assertIntentionalError(ctx, "intentional"))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertFailure()));
  }

  @Test
  public void testReplaceDocumentsWithOptionsNoMatch(TestContext ctx) {
    Async async = ctx.async();
    db.rxReplaceDocumentsWithOptions("replaceDocumentsWithOptionsNoMatch",
        new JsonObject().put("test", "testReplaceDocumentsWithOptionsNoMatch"),
        new JsonObject().put("foo", "bar"), new UpdateOptions().setWriteOption(WriteOption.MAJORITY))
      .doOnError(assertNoMappingFoundError(ctx, async))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertFailure()));
  }

  @Test
  public void testReplaceDocumentsWithOptionsReturnedObjectNotModified(TestContext ctx) {
    final MongoClientUpdateResult given = new MongoClientUpdateResult(17, new JsonObject()
      .put("field1", "value1")
      .put("field2", "value2")
      .put("field3", new JsonObject()
        .put("field4", "value3")
        .put("field5", "value4")
        .put("field6", new JsonArray()
          .add("value5")
          .add("value6")
        )
      ), 24);
    final MongoClientUpdateResult expected = new MongoClientUpdateResult(given.toJson().copy());

    mock.replaceDocumentsWithOptions()
      .inCollection("replaceDocumentsWithOptions")
      .withQuery(new JsonObject().put("test", "testReplaceDocumentsWithOptions"))
      .withReplace(new JsonObject().put("foo", "bar"))
      .withOptions(new UpdateOptions(true, false))
      .returns(given);

    db.rxReplaceDocumentsWithOptions("replaceDocumentsWithOptions", new JsonObject().put("test", "testReplaceDocumentsWithOptions"), new JsonObject().put("foo", "bar"), new UpdateOptions(true, false))
      .doOnSuccess(actual -> ctx.assertEquals(expected.toJson(), actual.toJson()))
      .doOnSuccess(actual -> {
        actual.getDocUpsertedId().put("field1", "replace");
        actual.getDocUpsertedId().remove("field2");
        actual.getDocUpsertedId().put("add", "add");
        actual.getDocUpsertedId().getJsonObject("field3").put("field4", "replace");
        actual.getDocUpsertedId().getJsonObject("field3").remove("field5");
        actual.getDocUpsertedId().getJsonObject("field3").put("add", "add");
        actual.getDocUpsertedId().getJsonObject("field3").getJsonArray("field6").remove(0);
        actual.getDocUpsertedId().getJsonObject("field3").getJsonArray("field6").add("add");
      })
      .repeat(2)
      .ignoreElements()
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testReplaceDocumentsWithOptionsFileReturnedObjectNotModified(TestContext ctx) {
    final MongoClientUpdateResult expected = new MongoClientUpdateResult(21, new JsonObject().put("field1", "value1"), 56);

    db.rxReplaceDocumentsWithOptions("replaceDocumentsWithOptions", new JsonObject().put("test", "testReplaceDocumentsWithOptionsFile"), new JsonObject().put("foo", "bar"), new UpdateOptions(false, true))
      .doOnSuccess(actual -> ctx.assertEquals(expected.toJson(), actual.toJson()))
      .doOnSuccess(actual -> {
        actual.getDocUpsertedId().put("field1", "replace");
        actual.getDocUpsertedId().put("add", "add");
      })
      .repeat(2)
      .ignoreElements()
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }
}
