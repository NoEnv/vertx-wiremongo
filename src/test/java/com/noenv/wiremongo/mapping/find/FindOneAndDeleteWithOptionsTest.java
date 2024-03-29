package com.noenv.wiremongo.mapping.find;

import com.mongodb.client.model.CollationStrength;
import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.CollationOptions;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.rxjava3.CompletableHelper;
import io.vertx.rxjava3.MaybeHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class FindOneAndDeleteWithOptionsTest extends TestBase {

  @Test
  public void testFindOneAndDeleteWithOptions(TestContext ctx) {
    mock.findOneAndDeleteWithOptions()
      .inCollection("findOneAndDeleteWithOptions")
      .withQuery(new JsonObject().put("test", "testFindOneAndDeleteWithOptions"))
      .withOptions(new FindOptions().setSkip(5).setLimit(21).setCollation(new CollationOptions().setLocale("de_AT").setStrength(CollationStrength.PRIMARY)))
      .returns(new JsonObject().put("field1", "value1"));

    db.rxFindOneAndDeleteWithOptions("findOneAndDeleteWithOptions",
        new JsonObject().put("test", "testFindOneAndDeleteWithOptions"),
        new FindOptions().setSkip(5).setLimit(21).setCollation(new CollationOptions().setLocale("de_AT").setStrength(CollationStrength.PRIMARY))
      )
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r ->
        ctx.assertEquals("value1", r.getString("field1"))
      )));
  }

  @Test
  public void testFindOneAndDeleteWithOptionsFile(TestContext ctx) {
    db.rxFindOneAndDeleteWithOptions("findOneAndDeleteWithOptions",
        new JsonObject().put("test", "testFindOneAndDeleteWithOptionsFile"),
        new FindOptions().setBatchSize(13).setSort(new JsonObject().put("field1", -1))
      )
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r ->
        ctx.assertEquals("value1", r.getString("field1"))
      )));
  }

  @Test
  public void testFindOneAndDeleteWithOptionsFileError(TestContext ctx) {
    db.rxFindOneAndDeleteWithOptions("findOneAndDeleteWithOptions",
        new JsonObject().put("test", "testFindOneAndDeleteWithOptionsFileError"),
        new FindOptions().setFields(new JsonObject().put("fieldx", 1).put("fieldy", 1)).setLimit(42))
      .doOnError(assertIntentionalError(ctx, "intentional"))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertFailure()));
  }

  @Test
  public void testFindOneAndDeleteWithOptionsReturnedObjectNotModified(TestContext ctx) {
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

    mock.findOneAndDeleteWithOptions()
      .inCollection("findOneAndDeleteWithOptions")
      .withQuery(new JsonObject().put("test", "testFindOneAndDeleteWithOptions"))
      .withOptions(new FindOptions().setSkip(5).setLimit(21))
      .returns(given);

    db.rxFindOneAndDeleteWithOptions("findOneAndDeleteWithOptions",
        new JsonObject().put("test", "testFindOneAndDeleteWithOptions"),
        new FindOptions().setSkip(5).setLimit(21))
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
  public void testFindOneAndDeleteWithOptionsFileReturnedObjectNotModified(TestContext ctx) {
    final JsonObject expected = new JsonObject().put("field1", "value1");

    db.rxFindOneAndDeleteWithOptions("findOneAndDeleteWithOptions",
        new JsonObject().put("test", "testFindOneAndDeleteWithOptionsFile"),
        new FindOptions().setBatchSize(13).setSort(new JsonObject().put("field1", -1)))
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
