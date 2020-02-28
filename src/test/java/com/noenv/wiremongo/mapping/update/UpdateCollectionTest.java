package com.noenv.wiremongo.mapping.update;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientUpdateResult;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class UpdateCollectionTest extends TestBase {

  @Test
  public void testUpdateCollection(TestContext ctx) {
    Async async = ctx.async();

    mock.updateCollection()
      .inCollection("updatecollection")
      .withQuery(new JsonObject().put("test", "testUpdateCollection"))
      .withUpdate(new JsonObject().put("foo", "bar"))
      .returns(new MongoClientUpdateResult(37, null, 21));

    db.rxUpdateCollection("updatecollection", new JsonObject().put("test", "testUpdateCollection"), new JsonObject().put("foo", "bar"))
      .subscribe(r -> {
        ctx.assertEquals(37L, r.getDocMatched());
        ctx.assertEquals(21L, r.getDocModified());
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testUpdateCollectionFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxUpdateCollection("updatecollection", new JsonObject().put("test", "testUpdateCollectionFile"), new JsonObject().put("foo", "bar"))
      .subscribe(r -> {
        ctx.assertEquals(42L, r.getDocMatched());
        ctx.assertEquals(11L, r.getDocModified());
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testUpdateCollectionFileError(TestContext ctx) {
    Async async = ctx.async();
    db.rxUpdateCollection("updatecollection", new JsonObject().put("test", "testUpdateCollectionFileError"), new JsonObject().put("foo", "bar"))
      .subscribe(r -> ctx.fail(), ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }
}
