package com.noenv.wiremongo.mapping.index;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.rxjava3.CompletableHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class CreateIndexTest extends TestBase {

  @Test
  public void testCreateIndex(TestContext ctx) {
    mock.createIndex()
      .inCollection("createindex")
      .withKey(new JsonObject().put("test", "testCreateIndex"))
      .returns(null);

    db.rxCreateIndex("createindex", new JsonObject().put("test", "testCreateIndex"))
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testCreateIndexFile(TestContext ctx) {
    db.rxCreateIndex("createindex", new JsonObject().put("test", "testCreateIndexFile"))
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testCreateIndexFileError(TestContext ctx) {
    db.rxCreateIndex("createindex", new JsonObject().put("test", "testCreateIndexFileError"))
      .doOnError(assertIntentionalError(ctx, "intentional"))
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertFailure()));
  }
}
