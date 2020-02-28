package com.noenv.wiremongo.mapping.createindex;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.IndexOptions;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class CreateIndexWithOptionsTest extends TestBase {

  @Test
  public void testCreateIndex(TestContext ctx) {
    Async async = ctx.async();

    mock.createIndexWithOptions()
      .inCollection("createindexwithoptions")
      .withOptions(new IndexOptions().background(false).unique(true).sparse(false))
      .withKey(new JsonObject().put("test", "testCreateIndexWithOptions"))
      .returns(null);

    db.rxCreateIndexWithOptions("createindexwithoptions",
      new JsonObject().put("test", "testCreateIndexWithOptions"),
      new IndexOptions().background(false).unique(true).sparse(false))
      .subscribe(async::complete, ctx::fail);
  }

  @Test
  public void testCreateIndexFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxCreateIndexWithOptions("createindexwithoptions", new JsonObject().put("test", "testCreateIndexWithOptionsFile"),
      new IndexOptions().name("testIndex"))
      .subscribe(async::complete, ctx::fail);
  }

  @Test
  public void testCreateIndexFileError(TestContext ctx) {
    Async async = ctx.async();
    db.rxCreateIndexWithOptions("createindexwithoptions", new JsonObject().put("test", "testCreateIndexWithOptionsFileError"),
      new IndexOptions().background(false).unique(false).sparse(false).name("testIndexError"))
      .subscribe(ctx::fail, ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }
}
