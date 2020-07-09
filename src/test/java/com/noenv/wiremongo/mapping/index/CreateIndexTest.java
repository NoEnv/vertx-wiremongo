package com.noenv.wiremongo.mapping.index;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class CreateIndexTest extends TestBase {

  @Test
  public void testCreateIndex(TestContext ctx) {
    Async async = ctx.async();

    mock.createIndex()
      .inCollection("createindex")
      .withKey(new JsonObject().put("test", "testCreateIndex"))
      .returns(null);

    db.rxCreateIndex("createindex", new JsonObject().put("test", "testCreateIndex"))
      .subscribe(async::complete, ctx::fail);
  }

  @Test
  public void testCreateIndexFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxCreateIndex("createindex", new JsonObject().put("test", "testCreateIndexFile"))
      .subscribe(async::complete, ctx::fail);
  }

  @Test
  public void testCreateIndexFileError(TestContext ctx) {
    Async async = ctx.async();
    db.rxCreateIndex("createindex", new JsonObject().put("test", "testCreateIndexFileError"))
      .subscribe(ctx::fail, ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }
}
