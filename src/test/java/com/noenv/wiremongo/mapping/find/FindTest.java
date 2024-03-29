package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.rxjava3.CompletableHelper;
import io.vertx.rxjava3.SingleHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;

@RunWith(VertxUnitRunner.class)
public class FindTest extends TestBase {

  @Test
  public void testFind(TestContext ctx) {
    mock.find()
      .inCollection("find")
      .withQuery(new JsonObject().put("test", "testFind"))
      .returns(Collections.singletonList(new JsonObject().put("field1", "value1")));

    db.rxFind("find", new JsonObject().put("test", "testFind"))
      .subscribe(SingleHelper.toObserver(ctx.asyncAssertSuccess(r ->
        ctx.assertEquals("value1", r.get(0).getString("field1"))
      )));
  }

  @Test
  public void testFindFile(TestContext ctx) {
    db.rxFind("find", new JsonObject().put("test", "testFindFile"))
      .subscribe(SingleHelper.toObserver(ctx.asyncAssertSuccess(r ->
        ctx.assertEquals("value1", r.get(0).getString("field1"))
      )));
  }

  @Test
  public void testFindFileError(TestContext ctx) {
    db.rxFind("find", new JsonObject().put("test", "testFindFileError"))
      .doOnError(assertIntentionalError(ctx, "intentional"))
      .subscribe(SingleHelper.toObserver(ctx.asyncAssertFailure()));
  }

  @Test
  public void testFindReturnedObjectNotModified(TestContext ctx) {
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

    mock.find()
      .inCollection("find")
      .withQuery(new JsonObject().put("test", "testFind"))
      .returns(new ArrayList<>(Collections.singletonList(given)));

    db.rxFind("find", new JsonObject().put("test", "testFind"))
      .doOnSuccess(actual -> ctx.assertEquals(expected, actual.get(0)))
      .doOnSuccess(actual -> {
        actual.get(0).put("field1", "replace");
        actual.get(0).remove("field2");
        actual.get(0).put("add", "add");
        actual.get(0).getJsonObject("field3").put("field4", "replace");
        actual.get(0).getJsonObject("field3").remove("field5");
        actual.get(0).getJsonObject("field3").put("add", "add");
        actual.get(0).getJsonObject("field3").getJsonArray("field6").remove(0);
        actual.get(0).getJsonObject("field3").getJsonArray("field6").add("add");
        actual.add(new JsonObject().put("new object", "new object"));
      })
      .repeat(2)
      .ignoreElements()
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testFindFileReturnedObjectNotModified(TestContext ctx) {
    final JsonObject expected = new JsonObject().put("field1", "value1");

    db.rxFind("find", new JsonObject().put("test", "testFindFile"))
      .doOnSuccess(actual -> ctx.assertEquals(expected, actual.get(0)))
      .doOnSuccess(actual -> {
        actual.get(0).put("field1", "replace");
        actual.get(0).put("add", "add");
        actual.add(new JsonObject().put("new object", "new object"));
      })
      .repeat(2)
      .ignoreElements()
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }
}
