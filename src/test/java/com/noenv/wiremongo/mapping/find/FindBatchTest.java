package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.MemoryStream;
import com.noenv.wiremongo.TestBase;
import io.reactivex.rxjava3.core.Flowable;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.rxjava3.CompletableHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class FindBatchTest extends TestBase {

  @Test
  public void testFindBatch(TestContext ctx) {
    Async async = ctx.async();

    mock.findBatch()
      .inCollection("findbatch")
      .withQuery(new JsonObject().put("test", "testFindBatch"))
      .returns(MemoryStream.of(new JsonObject().put("field1", "value1")));

    db.findBatch("findbatch", new JsonObject().put("test", "testFindBatch"))
      .handler(r -> {
        ctx.assertEquals("value1", r.getString("field1"));
        async.complete();
      });
  }

  @Test
  public void testFindBatchError(TestContext ctx) {
    Async async = ctx.async();

    mock.findBatch()
      .inCollection("findbatch")
      .withQuery(new JsonObject().put("test", "testFindBatchError"))
      .returnsError(new Exception("intentional"));

    db.findBatch("findbatch", new JsonObject().put("test", "testFindBatchError"))
      .exceptionHandler(ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }

  @Test
  public void testFindBatchFile(TestContext ctx) {
    Async async = ctx.async(3);
    db.findBatch("findbatch", new JsonObject().put("test", "testFindBatchFile"))
      .handler(r -> {
        ctx.assertEquals("value1", r.getString("field1"));
        async.countDown();
      });
  }

  @Test
  public void testFindBatchFileError(TestContext ctx) {
    Async async = ctx.async();
    db.findBatch("findbatch", new JsonObject().put("test", "testFindBatchFileError"))
      .exceptionHandler(ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testFindBatchReturnedObjectNotModified(TestContext ctx) {
    final Async async = ctx.async(1 * 2);
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

    mock.findBatch()
      .inCollection("findbatch")
      .withQuery(new JsonObject().put("test", "testFindBatch"))
      .returns(MemoryStream.of(given));

    Flowable.mergeArray(
      db.findBatch("findbatch", new JsonObject().put("test", "testFindBatch")).toFlowable(),
      db.findBatch("findbatch", new JsonObject().put("test", "testFindBatch")).toFlowable()
    )
      .doOnNext(actual -> async.countDown())
      .doOnNext(actual -> ctx.assertEquals(expected, actual))
      .doOnNext(actual -> {
        actual.put("field1", "replace");
        actual.remove("field2");
        actual.put("add", "add");
        actual.getJsonObject("field3").put("field4", "replace");
        actual.getJsonObject("field3").remove("field5");
        actual.getJsonObject("field3").put("add", "add");
        actual.getJsonObject("field3").getJsonArray("field6").remove(0);
        actual.getJsonObject("field3").getJsonArray("field6").add("add");
      })
      .ignoreElements()
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testFindBatchFileReturnedObjectNotModified(TestContext ctx) {
    final Async async = ctx.async(3 * 2);
    final JsonObject expected = new JsonObject().put("field1", "value1");

    Flowable.mergeArray(
      db.findBatch("findbatch", new JsonObject().put("test", "testFindBatchFile")).toFlowable(),
      db.findBatch("findbatch", new JsonObject().put("test", "testFindBatchFile")).toFlowable()
    )
      .doOnNext(actual -> async.countDown())
      .doOnNext(actual -> ctx.assertEquals(expected, actual))
      .doOnNext(actual -> {
        actual.put("field1", "replace");
        actual.put("add", "add");
      })
      .ignoreElements()
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }
}
