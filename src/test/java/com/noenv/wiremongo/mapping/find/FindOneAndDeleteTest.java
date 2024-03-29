package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.rxjava3.CompletableHelper;
import io.vertx.rxjava3.MaybeHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class FindOneAndDeleteTest extends TestBase {

  @Test
  public void testFindOneAndDelete(TestContext ctx) {
    mock.findOneAndDelete()
      .inCollection("findOneAndDelete")
      .withQuery(new JsonObject().put("test", "testFindOneAndDelete"))
      .returns(new JsonObject().put("field1", "value1"));

    db.rxFindOneAndDelete("findOneAndDelete", new JsonObject().put("test", "testFindOneAndDelete"))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r ->
        ctx.assertEquals("value1", r.getString("field1"))
      )));
  }

  @Test
  public void testFindOneAndDeleteFile(TestContext ctx) {
    db.rxFindOneAndDelete("findOneAndDelete", new JsonObject().put("test", "testFindOneAndDeleteFile"))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r ->
        ctx.assertEquals("value1", r.getString("field1"))
      )));
  }

  @Test
  public void testFindOneAndDeleteFileError(TestContext ctx) {
    db.rxFindOneAndDelete("findOneAndDelete", new JsonObject().put("test", "testFindOneAndDeleteFileError"))
      .doOnError(assertIntentionalError(ctx, "intentional"))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertFailure()));
  }

  @Test
  public void testFindOneAndDeleteReturnedObjectNotModified(TestContext ctx) {
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

    mock.findOneAndDelete()
      .inCollection("findOneAndDelete")
      .withQuery(new JsonObject().put("test", "testFindOneAndDelete"))
      .returns(given);

    db.rxFindOneAndDelete("findOneAndDelete", new JsonObject().put("test", "testFindOneAndDelete"))
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
  public void testFindOneAndDeleteFileReturnedObjectNotModified(TestContext ctx) {
    final JsonObject expected = new JsonObject().put("field1", "value1");

    db.rxFindOneAndDelete("findOneAndDelete", new JsonObject().put("test", "testFindOneAndDeleteFile"))
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
