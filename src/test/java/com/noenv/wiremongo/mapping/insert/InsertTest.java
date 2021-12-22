package com.noenv.wiremongo.mapping.insert;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.rxjava3.MaybeHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Instant;

import static com.noenv.wiremongo.matching.JsonMatcher.equalToJson;

@RunWith(VertxUnitRunner.class)
public class InsertTest extends TestBase {

  @Test
  public void testInsert(TestContext ctx) {
    mock.insert()
      .inCollection("insert")
      .withDocument(equalToJson(new JsonObject().put("test", "testInsert"), true))
      .returns("5c45f450c29de454289c5705");

    db.rxInsert("insert", new JsonObject()
        .put("test", "testInsert")
        .put("createdAt", Instant.now()))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r -> ctx.assertEquals("5c45f450c29de454289c5705", r))));
  }

  @Test
  public void testInsertFile(TestContext ctx) {
    db.rxInsert("insert", new JsonObject()
        .put("test", "testInsertFile")
        .put("createdAt", Instant.now()))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r -> ctx.assertEquals("5c45f450c29de454289c5706", r))));
  }

  @Test
  public void testInsertFileError(TestContext ctx) {
    db.rxInsert("insert", new JsonObject()
        .put("test", "testInsertFileError")
        .put("createdAt", Instant.now()))
      .doOnError(assertIntentionalError(ctx, "intentional"))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertFailure()));
  }
}
