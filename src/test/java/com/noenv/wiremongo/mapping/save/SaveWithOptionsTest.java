package com.noenv.wiremongo.mapping.save;

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
public class SaveWithOptionsTest extends TestBase {

  @Test
  public void testSaveWithOptions(TestContext ctx) {
    Async async = ctx.async();

    mock.saveWithOptions()
      .inCollection("saveWithOptions")
      .withDocument(equalToJson(new JsonObject().put("test", "testSaveWithOptions"), true))
      .withOptions(WriteOption.JOURNALED)
      .returns("5c45f450c29de454289c5705");

    db.rxSaveWithOptions("saveWithOptions", new JsonObject()
      .put("test", "testSaveWithOptions")
      .put("createdAt", Instant.now()), WriteOption.JOURNALED)
      .subscribe(r -> {
        ctx.assertEquals("5c45f450c29de454289c5705", r);
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testSaveWithOptionsFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxSaveWithOptions("saveWithOptions", new JsonObject()
      .put("test", "testSaveWithOptionsFile")
      .put("createdAt", Instant.now()), WriteOption.ACKNOWLEDGED)
      .subscribe(r -> {
        ctx.assertEquals("5c45f450c29de454289c5706", r);
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testSaveWithOptionsFileError(TestContext ctx) {
    Async async = ctx.async();
    db.rxSaveWithOptions("saveWithOptions", new JsonObject()
      .put("test", "testSaveWithOptionsFileError")
      .put("createdAt", Instant.now()), WriteOption.FSYNCED)
      .subscribe(r -> ctx.fail(), ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }
}
