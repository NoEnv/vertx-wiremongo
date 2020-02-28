package com.noenv.wiremongo.mapping.bulkwrite;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.BulkOperation;
import io.vertx.ext.mongo.MongoClientBulkWriteResult;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@RunWith(VertxUnitRunner.class)
public class BulkWriteTest extends TestBase {

  @Test
  public void testBulkWrite(TestContext ctx) {
    Async async = ctx.async();

    mock.bulkWrite()
      .inCollection("bulkwrite")
      .withOperations(Arrays.asList(BulkOperation.createInsert(new JsonObject().put("test", "testBulkWrite"))))
      .returns(new MongoClientBulkWriteResult(1, 0, 0, 0, null));

    db.rxBulkWrite("bulkwrite", Arrays.asList(BulkOperation.createInsert(new JsonObject().put("test", "testBulkWrite"))))
      .subscribe(r -> {
        ctx.assertEquals(1L, r.getInsertedCount());
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testBulkWriteFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxBulkWrite("bulkwrite", Arrays.asList(BulkOperation.createInsert(new JsonObject().put("test", "testBulkWriteFile"))))
      .subscribe(r -> {
        ctx.assertEquals(28L, r.getDeletedCount());
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testBulkWriteFileError(TestContext ctx) {
    Async async = ctx.async();
    db.rxBulkWrite("bulkwrite", Arrays.asList(BulkOperation.createInsert(new JsonObject().put("test", "testBulkWriteFileError"))))
      .subscribe(r -> ctx.fail(), ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }

  @Test
  public void testBulkWriteJsonMatcher(TestContext ctx) {
    Async async = ctx.async();
    db.rxBulkWrite("bulkwrite", Arrays.asList(BulkOperation.createInsert(new JsonObject()
        .put("test", "testBulkWriteFileJsonMatcher")
        .put("created", Instant.now()))))
      .subscribe(r -> {
        ctx.assertEquals(48L, r.getDeletedCount());
        async.complete();
      }, ctx::fail);
  }
}
