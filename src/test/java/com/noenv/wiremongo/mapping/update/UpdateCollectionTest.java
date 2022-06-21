package com.noenv.wiremongo.mapping.update;

import com.mongodb.MongoCommandException;
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
public class UpdateCollectionTest extends TestBase {

  @Test
  public void testUpdateCollection(TestContext ctx) {
    mock.updateCollection()
      .inCollection("updatecollection")
      .withQuery(new JsonObject().put("test", "testUpdateCollection"))
      .withUpdate(new JsonObject().put("foo", "bar"))
      .returns(37, null, 21);

    db.rxUpdateCollection("updatecollection", new JsonObject().put("test", "testUpdateCollection"), new JsonObject().put("foo", "bar"))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r -> {
        ctx.assertEquals(37L, r.getDocMatched());
        ctx.assertEquals(21L, r.getDocModified());
      })));
  }

  @Test
  public void testUpdateCollectionFile(TestContext ctx) {
    db.rxUpdateCollection("updatecollection", new JsonObject().put("test", "testUpdateCollectionFile"), new JsonObject().put("foo", "bar"))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r -> {
        ctx.assertEquals(42L, r.getDocMatched());
        ctx.assertEquals(11L, r.getDocModified());
      })));
  }

  @Test
  public void testUpdateCollectionFileError(TestContext ctx) {
    db.rxUpdateCollection("updatecollection", new JsonObject().put("test", "testUpdateCollectionFileError"), new JsonObject().put("foo", "bar"))
      .doOnError(assertIntentionalError(ctx, "intentional"))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertFailure()));
  }

  @Test
  public void testUpdateCollectionReturnedObjectNotModified(TestContext ctx) {
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

    mock.updateCollection()
      .inCollection("updatecollection")
      .withQuery(new JsonObject().put("test", "testUpdateCollection"))
      .withUpdate(new JsonObject().put("foo", "bar"))
      .returns(given);

    db.rxUpdateCollection("updatecollection", new JsonObject().put("test", "testUpdateCollection"), new JsonObject().put("foo", "bar"))
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
  public void testUpdateCollectionFileReturnedObjectNotModified(TestContext ctx) {
    final MongoClientUpdateResult expected = new MongoClientUpdateResult(42, new JsonObject().put("field1", "value1"), 11);

    db.rxUpdateCollection("updatecollection", new JsonObject().put("test", "testUpdateCollectionFile"), new JsonObject().put("foo", "bar"))
      .doOnSuccess(actual -> ctx.assertEquals(expected.toJson(), actual.toJson()))
      .doOnSuccess(actual -> {
        actual.getDocUpsertedId().put("field1", "replace");
        actual.getDocUpsertedId().put("add", "add");
      })
      .repeat(2)
      .ignoreElements()
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testUpdateCollectionAggregationPipeline(TestContext ctx) {
    mock.updateCollectionAggregationPipeline()
      .inCollection("updatecollection")
      .withQuery(new JsonObject().put("test", "testUpdateCollection"))
      .withUpdate(new JsonArray().add(new JsonObject().put("foo", "bar")))
      .returns(37, null, 21);

    db.rxUpdateCollection("updatecollection", new JsonObject().put("test", "testUpdateCollection"), new JsonArray().add(new JsonObject().put("foo", "bar")))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r -> {
        ctx.assertEquals(37L, r.getDocMatched());
        ctx.assertEquals(21L, r.getDocModified());
      })));
  }

  @Test
  public void testUpdateCollectionAggregationPipelineFile(TestContext ctx) {
    db.rxUpdateCollection("updatecollection",
        new JsonObject().put("test", "testUpdateCollectionAggregationPipelineFile"),
        new JsonArray().add(new JsonObject().put("foo", "bar")))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r -> {
        ctx.assertEquals(42L, r.getDocMatched());
        ctx.assertEquals(11L, r.getDocModified());
      })));
  }

  @Test
  public void testUpdateCollectionDuplicateKeyError(TestContext ctx) {
    mock.updateCollection()
      .inCollection("updatecollection")
      .withQuery(new JsonObject().put("test", "testUpdateCollection"))
      .withUpdate(new JsonObject().put("foo", "bar"))
      .returnsDuplicateKeyError();

    db.rxUpdateCollection("updatecollection",
        new JsonObject().put("test", "testUpdateCollection"),
        new JsonObject().put("foo", "bar"))
      .doOnError(assertMongoException(ctx, MongoCommandException.class, mce -> {
        ctx.assertEquals("E11000 duplicate key error", mce.getErrorMessage());
        ctx.assertEquals(11000, mce.getErrorCode());
        ctx.assertEquals("DuplicateKey", mce.getErrorCodeName());
      }))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertFailure()));
  }
}
