package com.noenv.wiremongo.mapping.find;

import com.mongodb.client.model.CollationStrength;
import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.CollationOptions;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.mongo.UpdateOptions;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.rxjava3.CompletableHelper;
import io.vertx.rxjava3.MaybeHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class FindOneAndUpdateWithOptionsTest extends TestBase {

  @Test
  public void testFindOneAndUpdateWithOptions(TestContext ctx) {
    mock.findOneAndUpdateWithOptions()
      .inCollection("findoneandupdatewithoptions")
      .withQuery(new JsonObject().put("test", "testFindOneAndUpdateWithOptions"))
      .withFindOptions(new FindOptions().setSkip(42))
      .withUpdateOptions(new UpdateOptions().setReturningNewDocument(true).setCollation(new CollationOptions().setLocale("de_AT").setStrength(CollationStrength.PRIMARY)))
      .withUpdate(new JsonObject().put("foo", "bar"))
      .returns(new JsonObject().put("field1", "value1"));

    db.rxFindOneAndUpdateWithOptions("findoneandupdatewithoptions",
        new JsonObject().put("test", "testFindOneAndUpdateWithOptions"),
        new JsonObject().put("foo", "bar"),
        new FindOptions().setSkip(42),
        new UpdateOptions().setReturningNewDocument(true).setCollation(new CollationOptions().setLocale("de_AT").setStrength(CollationStrength.PRIMARY)))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r ->
        ctx.assertEquals("value1", r.getString("field1"))
      )));
  }

  @Test
  public void testFindOneAndUpdateWithOptionsFile(TestContext ctx) {
    db.rxFindOneAndUpdateWithOptions("findoneandupdatewithoptions",
        new JsonObject().put("test", "testFindOneAndUpdateWithOptionsFile"),
        new JsonObject().put("foo", "bar"),
        new FindOptions().setSkip(42),
        new UpdateOptions().setMulti(true)
      )
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r ->
        ctx.assertEquals("value1", r.getString("field1"))
      )));
  }

  @Test
  public void testFindOneAndUpdateWithOptionsFileError(TestContext ctx) {
    db.rxFindOneAndUpdateWithOptions("findoneandupdatewithoptions",
        new JsonObject().put("test", "testFindOneAndUpdateWithOptionsError"),
        new JsonObject().put("foo", "bar"),
        new FindOptions().setSkip(42),
        new UpdateOptions().setMulti(true))
      .doOnError(assertIntentionalError(ctx, "intentional"))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertFailure()));
  }

  @Test
  public void testFindOneAndUpdateWithOptionsReturnedObjectNotModified(TestContext ctx) {
    final JsonObject given = new JsonObject()
      .put("field1", "value1")
      .put("field2", "value2")
      .put("field3", new JsonObject()
        .put("field4", "value3")
        .put("field5", "value4")
        .put("field6", new JsonArray()
          .add("value5")
          .add("value6")
        )
      );
    final JsonObject expected = given.copy();

    mock.findOneAndUpdateWithOptions()
      .inCollection("findoneandupdatewithoptions")
      .withQuery(new JsonObject().put("test", "testFindOneAndUpdateWithOptions"))
      .withFindOptions(new FindOptions().setSkip(42))
      .withUpdateOptions(new UpdateOptions().setReturningNewDocument(true))
      .withUpdate(new JsonObject().put("foo", "bar"))
      .returns(given);

    db.rxFindOneAndUpdateWithOptions("findoneandupdatewithoptions",
        new JsonObject().put("test", "testFindOneAndUpdateWithOptions"),
        new JsonObject().put("foo", "bar"),
        new FindOptions().setSkip(42),
        new UpdateOptions().setReturningNewDocument(true))
      .doOnSuccess(actual -> ctx.assertEquals(expected, actual))
      .doOnSuccess(actual -> {
        actual.put("field1", "replace");
        actual.remove("field2");
        actual.put("add", "add");
        actual.getJsonObject("field3").put("field4", "replace");
        actual.getJsonObject("field3").remove("field5");
        actual.getJsonObject("field3").put("add", "add");
        actual.getJsonObject("field3").getJsonArray("field6").remove(0);
        actual.getJsonObject("field3").getJsonArray("field6").add("add");
      })
      .repeat(2)
      .ignoreElements()
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testFindOneAndUpdateWithOptionsFileReturnedObjectNotModified(TestContext ctx) {
    final JsonObject expected = new JsonObject().put("field1", "value1");

    db.rxFindOneAndUpdateWithOptions("findoneandupdatewithoptions",
        new JsonObject().put("test", "testFindOneAndUpdateWithOptionsFile"),
        new JsonObject().put("foo", "bar"),
        new FindOptions().setSkip(42),
        new UpdateOptions().setMulti(true))
      .doOnSuccess(actual -> ctx.assertEquals(expected, actual))
      .doOnSuccess(actual -> {
        actual.put("field1", "replace");
        actual.put("add", "add");
      })
      .repeat(2)
      .ignoreElements()
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }
}
