package com.noenv.wiremongo.mapping.index;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.IndexModel;
import io.vertx.ext.mongo.IndexOptions;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.rxjava3.CompletableHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;

@RunWith(VertxUnitRunner.class)
public class CreateIndexesTest extends TestBase {

  @Test
  public void testCreateIndexes(TestContext ctx) {
    mock.createIndexes()
      .inCollection("createindexes")
      .withIndexModels(Collections.singletonList(new IndexModel(new JsonObject().put("test", "testCreateIndex"))))
      .returns(null);

    db.rxCreateIndexes("createindexes", Collections.singletonList(new IndexModel(new JsonObject().put("test", "testCreateIndex"))))
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testCreateIndexesFile(TestContext ctx) {
    db.rxCreateIndexes("createindexes", Collections.singletonList(new IndexModel(new JsonObject().put("foo", 1), new IndexOptions().name("testCreateIndexesFile"))))
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testCreateIndexesFileError(TestContext ctx) {
    db.rxCreateIndexes("createindexes", Collections.singletonList(new IndexModel(new JsonObject().put("foo", 1), new IndexOptions().name("testCreateIndexesFileError"))))
      .doOnError(assertIntentionalError(ctx, "intentional"))
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertFailure()));
  }
}
