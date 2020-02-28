package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.mongo.UpdateOptions;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class FindOneAndUpdateWithOptionsTest extends TestBase {

  @Test
  public void testFindOneAndUpdate(TestContext ctx) {
    Async async = ctx.async();

    mock.findOneAndUpdateWithOptions()
      .inCollection("findoneandupdatewithoptions")
      .withQuery(new JsonObject().put("test", "testFindOneAndUpdateWithOptions"))
      .withFindOptions(new FindOptions().setSkip(42))
      .withUpdateOptions(new UpdateOptions().setReturningNewDocument(true))
      .withUpdate(new JsonObject().put("foo", "bar"))
      .returns(new JsonObject().put("field1", "value1"));

    db.rxFindOneAndUpdateWithOptions("findoneandupdatewithoptions",
        new JsonObject().put("test", "testFindOneAndUpdateWithOptions"),
        new JsonObject().put("foo", "bar"),
        new FindOptions().setSkip(42),
        new UpdateOptions().setReturningNewDocument(true))
      .subscribe(r -> {
        ctx.assertEquals("value1", r.getString("field1"));
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testFindOneAndUpdateWithOptionsFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxFindOneAndUpdateWithOptions("findoneandupdatewithoptions",
        new JsonObject().put("test", "testFindOneAndUpdateWithOptionsFile"),
        new JsonObject().put("foo", "bar"),
        new FindOptions().setSkip(42),
        new UpdateOptions().setMulti(true))
      .subscribe(r -> {
        ctx.assertEquals("value1", r.getString("field1"));
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testFindOneAndUpdateFileError(TestContext ctx) {
    Async async = ctx.async();
    db.rxFindOneAndUpdateWithOptions("findoneandupdatewithoptions",
        new JsonObject().put("test", "testFindOneAndUpdateWithOptionsError"),
        new JsonObject().put("foo", "bar"),
        new FindOptions().setSkip(42),
        new UpdateOptions().setMulti(true))
      .subscribe(r -> ctx.fail(), ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }
}
