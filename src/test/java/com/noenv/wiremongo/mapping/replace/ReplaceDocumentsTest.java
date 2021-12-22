package com.noenv.wiremongo.mapping.replace;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientUpdateResult;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.rxjava3.CompletableHelper;
import io.vertx.rxjava3.MaybeHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class ReplaceDocumentsTest extends TestBase {

  @Test
  public void testReplaceDocuments(TestContext ctx) {
    mock.replaceDocuments()
      .inCollection("replaceDocuments")
      .withQuery(new JsonObject().put("test", "testReplaceDocuments"))
      .withReplace(new JsonObject().put("foo", "bar"))
      .returns(new MongoClientUpdateResult(17, null, 24));

    db.rxReplaceDocuments("replaceDocuments", new JsonObject().put("test", "testReplaceDocuments"),
      new JsonObject().put("foo", "bar"))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r -> {
        ctx.assertEquals(17L, r.getDocMatched());
        ctx.assertEquals(24L, r.getDocModified());
      })));
  }

  @Test
  public void testReplaceDocumentsFile(TestContext ctx) {
    db.rxReplaceDocuments("replaceDocuments", new JsonObject().put("test", "testReplaceDocumentsFile"),
      new JsonObject().put("foo", "bar"))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r -> {
        ctx.assertEquals(21L, r.getDocMatched());
        ctx.assertEquals(56L, r.getDocModified());
      })));
  }

  @Test
  public void testReplaceDocumentsFileError(TestContext ctx) {
    db.rxReplaceDocuments("replaceDocuments", new JsonObject().put("test", "testReplaceDocumentsFileError"),
      new JsonObject().put("foo", "bar"))
      .doOnError(assertIntentionalError(ctx, "intentional"))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertFailure()));
  }

  @Test
  public void testReplaceDocumentsReturnedObjectNotModified(TestContext ctx) {
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

    mock.replaceDocuments()
      .inCollection("replaceDocuments")
      .withQuery(new JsonObject().put("test", "testReplaceDocuments"))
      .withReplace(new JsonObject().put("foo", "bar"))
      .returns(given);

    db.rxReplaceDocuments("replaceDocuments", new JsonObject().put("test", "testReplaceDocuments"),
      new JsonObject().put("foo", "bar"))
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
  public void testReplaceDocumentsFileReturnedObjectNotModified(TestContext ctx) {
    final MongoClientUpdateResult expected = new MongoClientUpdateResult(21, new JsonObject().put("field1", "value1"), 56);

    db.rxReplaceDocuments("replaceDocuments", new JsonObject().put("test", "testReplaceDocumentsFile"), new JsonObject().put("foo", "bar"))
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
