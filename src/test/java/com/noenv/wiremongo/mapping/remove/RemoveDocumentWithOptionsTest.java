package com.noenv.wiremongo.mapping.remove;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientDeleteResult;
import io.vertx.ext.mongo.WriteOption;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Instant;

import static com.noenv.wiremongo.matching.JsonMatcher.equalToJson;

@RunWith(VertxUnitRunner.class)
public class RemoveDocumentWithOptionsTest extends TestBase {

  @Test
  public void testRemoveDocumentWithOptions(TestContext ctx) {
    Async async = ctx.async();

    mock.removeDocumentWithOptions()
      .inCollection("removeDocumentWithOptions")
      .withQuery(new JsonObject().put("test", "testRemoveDocumentWithOptions"))
      .withOptions(WriteOption.MAJORITY)
      .returns(new MongoClientDeleteResult(1));

    db.rxRemoveDocumentWithOptions("removeDocumentWithOptions", new JsonObject()
      .put("test", "testRemoveDocumentWithOptions"), WriteOption.MAJORITY)
      .subscribe(r -> {
        ctx.assertEquals(1L, r.getRemovedCount());
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testRemoveDocumentWithOptionsFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxRemoveDocumentWithOptions("removeDocumentWithOptions", new JsonObject()
      .put("test", "testRemoveDocumentWithOptionsFile"), WriteOption.REPLICA_ACKNOWLEDGED)
      .subscribe(r -> {
        ctx.assertEquals(2L, r.getRemovedCount());
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testRemoveDocumentWithOptionsFileError(TestContext ctx) {
    Async async = ctx.async();
    db.rxRemoveDocumentWithOptions("removeDocumentWithOptions", new JsonObject()
      .put("test", "testRemoveDocumentWithOptionsFileError"), WriteOption.UNACKNOWLEDGED)
      .subscribe(r -> ctx.fail(), ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }
}
