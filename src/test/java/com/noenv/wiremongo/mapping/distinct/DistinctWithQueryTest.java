package com.noenv.wiremongo.mapping.distinct;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.CollationOptions;
import io.vertx.ext.mongo.DistinctOptions;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.rxjava3.CompletableHelper;
import io.vertx.rxjava3.SingleHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class DistinctWithQueryTest extends TestBase {

  @Test
  public void testDistinctWithQuery(TestContext ctx) {
    mock.distinctWithQuery()
      .inCollection("distinctWithQuery")
      .withFieldName("testDistinctWithQuery")
      .withQuery(new JsonObject().put("foo", "bar"))
      .returns(new JsonArray().add("A").add("B"));

    db.rxDistinctWithQuery(
      "distinctWithQuery",
        "testDistinctWithQuery",
        null,
        new JsonObject().put("foo", "bar")
      )
      .subscribe(SingleHelper.toObserver(ctx.asyncAssertSuccess(r -> {
        ctx.assertEquals(2, r.size());
        ctx.assertEquals("A", r.getString(0));
        ctx.assertEquals("B", r.getString(1));
      })));
  }

  @Test
  public void testDistinctWithQueryFile(TestContext ctx) {
    db.distinctWithQuery("distinctWithQuery", "testDistinctWithQueryFile",
        "java.lang.String", new JsonObject().put("foo", "bar"))
      .subscribe(SingleHelper.toObserver(ctx.asyncAssertSuccess(r -> {
        ctx.assertEquals(3, r.size());
        ctx.assertEquals("A", r.getString(0));
        ctx.assertEquals("B", r.getString(1));
      })));
  }

  @Test
  public void testDistinctWithQueryFileError(TestContext ctx) {
    db.distinctWithQuery("distinctWithQuery", "testDistinctWithQueryFileError",
        "java.lang.Integer", new JsonObject().put("foo", "bar"))
      .doOnError(assertIntentionalError(ctx, "intentional"))
      .subscribe(SingleHelper.toObserver(ctx.asyncAssertFailure()));
  }

  @Test
  public void testDistinctWithQueryReturnedObjectNotModified(TestContext ctx) {
    final JsonArray given = new JsonArray().add("value1").add("value2");
    final JsonArray expected = given.copy();

    mock.distinctWithQuery()
      .inCollection("distinctWithQuery")
      .withFieldName("testDistinctWithQuery")
      .withQuery(new JsonObject().put("foo", "bar"))
      .returns(given);

    db.rxDistinctWithQuery("distinctWithQuery", "testDistinctWithQuery", null, new JsonObject().put("foo", "bar"))
      .doOnSuccess(actual -> ctx.assertEquals(expected, actual))
      .doOnSuccess(actual -> {
        actual.remove(0);
        actual.add("add");
      })
      .repeat(2)
      .ignoreElements()
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testDistinctWithQueryFileReturnedObjectNotModified(TestContext ctx) {
    final JsonArray expected = new JsonArray().add("A").add("B").add("C");

    db.rxDistinctWithQuery("distinctWithQuery", "testDistinctWithQueryFile", "java.lang.String", new JsonObject().put("foo", "bar"))
      .doOnSuccess(actual -> ctx.assertEquals(expected, actual))
      .doOnSuccess(actual -> {
        actual.remove(0);
        actual.add("add");
      })
      .repeat(2)
      .ignoreElements()
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testDistinctWithQueryWithOptions(TestContext ctx) {

    mock.distinctWithQuery()
      .inCollection("distinctWithQueryWithOptions")
      .withFieldName("testDistinctWithQueryWithOptions")
      .withQuery(new JsonObject().put("foo", "bar"))
      .withOptions(new DistinctOptions().setCollation(new CollationOptions().setLocale("no-way")))
      .returns(new JsonArray().add("A").add("B"));

    db.distinctWithQuery("distinctWithQueryWithOptions",
      "testDistinctWithQueryWithOptions",
      null,
      new JsonObject().put("foo", "bar"),
      new DistinctOptions().setCollation(new CollationOptions().setLocale("no-way"))
    ).subscribe(SingleHelper.toObserver(ctx.asyncAssertSuccess(r -> {
          ctx.assertEquals(2, r.size());
          ctx.assertEquals("A", r.getString(0));
          ctx.assertEquals("B", r.getString(1));
      }))
    );
  }

  @Test
  public void testDistinctWithQueryWithOptionsFile(TestContext ctx) {

    db.distinctWithQuery("distinctWithQueryWithOptions",
      "testDistinctWithQueryWithOptionsFile",
      "java.lang.String",
      new JsonObject().put("foo", "bar"),
      new DistinctOptions().setCollation(new CollationOptions().setLocale("no-way"))
    ).subscribe(SingleHelper.toObserver(ctx.asyncAssertSuccess(r -> {
        ctx.assertEquals(3, r.size());
        ctx.assertEquals("A", r.getString(0));
        ctx.assertEquals("B", r.getString(1));
        ctx.assertEquals("C", r.getString(2));
      }))
    );
  }

  @Test
  public void testDistinctWithQueryWithOptionsFileError(TestContext ctx) {
    db.distinctWithQuery("distinctWithQueryWithOptions",
      "testDistinctWithQueryWithOptionsFileError",
      "java.lang.String",
      new JsonObject().put("foo", "bar"),
      new DistinctOptions().setCollation(new CollationOptions().setLocale("no-way"))
    )
      .doOnError(assertIntentionalError(ctx, "intentional"))
      .subscribe(SingleHelper.toObserver(ctx.asyncAssertFailure())
    );
  }

  @Test
  public void testDistinctWithQueryWithOptionsNoMatch(TestContext ctx) {
    mock.distinctWithQuery()
      .inCollection("distinctWithQueryNoMatch")
      .withFieldName("testDistinctWithQueryNoMatch")
      .withQuery(new JsonObject().put("foo", "bar"))
      .withOptions(new DistinctOptions().setCollation(new CollationOptions().setLocale("DIFFERENT")))
      .returns(new JsonArray());

    db.distinctWithQuery("distinctWithQueryNoMatch", "testDistinctWithQueryNoMatch",
      null, new JsonObject().put("foo", "bar"), new DistinctOptions().setCollation(new CollationOptions().setLocale("no-way")))
      .doOnError(assertNoMappingFoundError(ctx))
      .subscribe(SingleHelper.toObserver(ctx.asyncAssertFailure()));
  }
}
