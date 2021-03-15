package com.noenv.wiremongo.mapping.index;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.IndexModel;
import io.vertx.ext.mongo.IndexOptions;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

@RunWith(VertxUnitRunner.class)
public class CreateIndexesTest extends TestBase {

  @Test
  public void testCreateIndexes(TestContext ctx) {
    Async async = ctx.async();

    mock.createIndexes()
      .inCollection("createindexes")
      .withIndexModels(Arrays.asList(new IndexModel(new JsonObject().put("test", "testCreateIndex"))))
      .returns(null);

    db.rxCreateIndexes("createindexes", Arrays.asList(new IndexModel(new JsonObject().put("test", "testCreateIndex"))))
      .subscribe(async::complete, ctx::fail);
  }

  @Test
  public void testCreateIndexesFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxCreateIndexes("createindexes", Arrays.asList(new IndexModel(new JsonObject().put("foo", 1), new IndexOptions().name("testCreateIndexesFile"))))
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
