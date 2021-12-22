package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.CollationOptions;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.mongo.UpdateOptions;
import io.vertx.ext.mongo.WriteOption;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.rxjava3.CompletableHelper;
import io.vertx.rxjava3.MaybeHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class FindOneAndReplaceWithOptionsTest extends TestBase {

  @Test
  public void testFindOneAndReplaceWithOptions(TestContext ctx) {
    mock.findOneAndReplaceWithOptions()
      .inCollection("findOneAndReplaceWithOptions")
      .withQuery(new JsonObject().put("test", "testFindOneAndReplaceWithOptions"))
      .withReplace(new JsonObject().put("foo", "bar"))
      .withFindOptions(new FindOptions().setLimit(4))
      .withUpdateOptions(new UpdateOptions().setWriteOption(WriteOption.FSYNCED).setCollation(new CollationOptions().setLocale("de_AT").setStrength(1)))
      .returns(new JsonObject().put("field1", "value1"));

    db.rxFindOneAndReplaceWithOptions("findOneAndReplaceWithOptions",
        new JsonObject().put("test", "testFindOneAndReplaceWithOptions"),
        new JsonObject().put("foo", "bar"),
        new FindOptions().setLimit(4),
        new UpdateOptions().setWriteOption(WriteOption.FSYNCED).setCollation(new CollationOptions().setLocale("de_AT").setStrength(1))
      )
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r ->
        ctx.assertEquals("value1", r.getString("field1"))
      )));
  }

  @Test
  public void testFindOneAndReplaceWithOptionsFile(TestContext ctx) {
    db.rxFindOneAndReplaceWithOptions("findOneAndReplaceWithOptions",
        new JsonObject().put("test", "testFindOneAndReplaceWithOptionsFile"),
        new JsonObject().put("foo", "bar"),
        new FindOptions().setFields(new JsonObject().put("field1", -1)),
        new UpdateOptions().setReturningNewDocument(false)
      )
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r ->
        ctx.assertEquals("value1", r.getString("field1"))
      )));
  }

  @Test
  public void testFindOneAndReplaceWithOptionsFileError(TestContext ctx) {
    db.rxFindOneAndReplaceWithOptions("findOneAndReplaceWithOptions",
        new JsonObject().put("test", "testFindOneAndReplaceWithOptionsFileError"),
        new JsonObject().put("foo", "bar"),
        new FindOptions().setLimit(42),
        new UpdateOptions().setUpsert(true))
      .doOnError(assertIntentionalError(ctx, "intentional"))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertFailure()));
  }

  @Test
  public void testFindOneAndReplaceWithOptionsReturnedObjectNotModified(TestContext ctx) {
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

    mock.findOneAndReplaceWithOptions()
      .inCollection("findOneAndReplaceWithOptions")
      .withQuery(new JsonObject().put("test", "testFindOneAndReplaceWithOptions"))
      .withReplace(new JsonObject().put("foo", "bar"))
      .withFindOptions(new FindOptions().setLimit(4))
      .withUpdateOptions(new UpdateOptions().setWriteOption(WriteOption.FSYNCED))
      .returns(given);

    db.rxFindOneAndReplaceWithOptions("findOneAndReplaceWithOptions",
        new JsonObject().put("test", "testFindOneAndReplaceWithOptions"),
        new JsonObject().put("foo", "bar"),
        new FindOptions().setLimit(4),
        new UpdateOptions().setWriteOption(WriteOption.FSYNCED))
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
  public void testFindOneAndReplaceWithOptionsFileReturnedObjectNotModified(TestContext ctx) {
    final JsonObject expected = new JsonObject().put("field1", "value1");

    db.rxFindOneAndReplaceWithOptions("findOneAndReplaceWithOptions",
        new JsonObject().put("test", "testFindOneAndReplaceWithOptionsFile"),
        new JsonObject().put("foo", "bar"),
        new FindOptions().setFields(new JsonObject().put("field1", -1)),
        new UpdateOptions().setReturningNewDocument(false))
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
