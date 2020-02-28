package com.noenv.wiremongo.mapping.insert;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.WriteOption;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Instant;

import static com.noenv.wiremongo.matching.JsonMatcher.equalToJson;

@RunWith(VertxUnitRunner.class)
public class InsertWithOptionsTest extends TestBase {

  @Test
  public void testInsertWithOptions(TestContext ctx) {
    Async async = ctx.async();

    mock.insertWithOptions()
      .inCollection("insertWithOptions")
      .withDocument(equalToJson(new JsonObject().put("test", "testInsertWithOptions"), true))
      .withOptions(WriteOption.JOURNALED)
      .returns("5c45f450c29de454289c5705");

    db.rxInsertWithOptions("insertWithOptions", new JsonObject()
        .put("test", "testInsertWithOptions")
        .put("createdAt", Instant.now()), WriteOption.JOURNALED)
      .subscribe(r -> {
        ctx.assertEquals("5c45f450c29de454289c5705", r);
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testInsertWithOptionsFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxInsertWithOptions("insertWithOptions", new JsonObject()
        .put("test", "testInsertWithOptionsFile")
        .put("createdAt", Instant.now()), WriteOption.ACKNOWLEDGED)
      .subscribe(r -> {
        ctx.assertEquals("5c45f450c29de454289c5706", r);
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testInsertWithOptionsFileError(TestContext ctx) {
    Async async = ctx.async();
    db.rxInsertWithOptions("insertWithOptions", new JsonObject()
        .put("test", "testInsertWithOptionsFileError")
        .put("createdAt", Instant.now()), WriteOption.FSYNCED)
      .subscribe(r -> ctx.fail(), ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }
}
