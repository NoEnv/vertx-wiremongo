package com.noenv.wiremongo.mapping.update;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientUpdateResult;
import io.vertx.ext.mongo.UpdateOptions;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.noenv.wiremongo.matching.JsonMatcher.equalToJson;

@RunWith(VertxUnitRunner.class)
public class UpdateCollectionWithOptionsTest extends TestBase {

  @Test
  public void testUpdateCollectionWithOptions(TestContext ctx) {
    Async async = ctx.async();

    mock.updateCollectionWithOptions()
      .inCollection("updatecollectionwithoptions")
      .withQuery(new JsonObject().put("test", "testUpdateCollectionWithOptions"))
      .withUpdate(equalToJson(new JsonObject().put("foo", "bar")))
      .withOptions(equalToJson(new JsonObject().put("upsert", true), UpdateOptions::toJson))
      .returns(new MongoClientUpdateResult(22, null, 24));

    db.rxUpdateCollectionWithOptions("updatecollectionwithoptions",
      new JsonObject().put("test", "testUpdateCollectionWithOptions"),
      new JsonObject().put("foo", "bar"),
      new UpdateOptions().setUpsert(true))
      .subscribe(r -> {
        ctx.assertEquals(22L, r.getDocMatched());
        ctx.assertEquals(24L, r.getDocModified());
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testUpdateCollectionWithOptionsFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxUpdateCollectionWithOptions("updatecollectionwithoptions",
      new JsonObject().put("test", "testUpdateCollectionWithOptionsFile"),
      new JsonObject().put("foo", "bar"),
      new UpdateOptions().setMulti(true))
      .subscribe(r -> {
        ctx.assertEquals(46L, r.getDocMatched());
        ctx.assertEquals(17L, r.getDocModified());
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testUpdateCollectionWithOptionsFileError(TestContext ctx) {
    Async async = ctx.async();
    db.rxUpdateCollectionWithOptions("updatecollectionwithoptions",
      new JsonObject().put("test", "testUpdateCollectionWithOptionsFileError"),
      new JsonObject().put("foo", "bar"),
      new UpdateOptions().setMulti(true))
      .subscribe(r -> ctx.fail(), ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }
}
