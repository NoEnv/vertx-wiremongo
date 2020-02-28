package com.noenv.wiremongo.mapping.insert;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Instant;

import static com.noenv.wiremongo.matching.JsonMatcher.equalToJson;

@RunWith(VertxUnitRunner.class)
public class InsertTest extends TestBase {

  @Test
  public void testInsert(TestContext ctx) {
    Async async = ctx.async();

    mock.insert()
      .inCollection("insert")
      .withDocument(equalToJson(new JsonObject().put("test", "testInsert"), true))
      .returns("5c45f450c29de454289c5705");

    db.rxInsert("insert", new JsonObject()
        .put("test", "testInsert")
        .put("createdAt", Instant.now()))
      .subscribe(r -> {
        ctx.assertEquals("5c45f450c29de454289c5705", r);
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testInsertFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxInsert("insert", new JsonObject()
        .put("test", "testInsertFile")
        .put("createdAt", Instant.now()))
      .subscribe(r -> {
        ctx.assertEquals("5c45f450c29de454289c5706", r);
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testInsertFileError(TestContext ctx) {
    Async async = ctx.async();
    db.rxInsert("insert", new JsonObject()
        .put("test", "testInsertFileError")
        .put("createdAt", Instant.now()))
      .subscribe(r -> ctx.fail(), ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }
}
