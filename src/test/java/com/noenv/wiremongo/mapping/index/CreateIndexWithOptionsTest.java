package com.noenv.wiremongo.mapping.index;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.CollationOptions;
import io.vertx.ext.mongo.IndexOptions;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.rxjava3.CompletableHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class CreateIndexWithOptionsTest extends TestBase {

  @Test
  public void testCreateIndexWithOptions(TestContext ctx) {
    mock.createIndexWithOptions()
      .inCollection("createindexwithoptions")
      .withOptions(new IndexOptions().background(false).unique(true).sparse(false))
      .withKey(new JsonObject().put("test", "testCreateIndexWithOptions"))
      .returns(null);

    db.rxCreateIndexWithOptions("createindexwithoptions",
        new JsonObject().put("test", "testCreateIndexWithOptions"),
        new IndexOptions().background(false).unique(true).sparse(false))
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testCreateIndexWithOptionsAndCollationFile(TestContext ctx) {
    db.rxCreateIndexWithOptions(
        "createindexwithoptions",
        new JsonObject().put("test", "testCreateIndexWithOptionsAndCollationFile"),
        new IndexOptions().name("testIndex").setCollation(new CollationOptions())
      )
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testCreateIndexWithOptionsFile(TestContext ctx) {
    db.rxCreateIndexWithOptions(
        "createindexwithoptions",
        new JsonObject().put("test", "testCreateIndexWithOptionsFile"),
        new IndexOptions().name("testIndex")
      )
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testCreateIndexWithOptionsFileError(TestContext ctx) {
    db.rxCreateIndexWithOptions("createindexwithoptions", new JsonObject().put("test", "testCreateIndexWithOptionsFileError"),
        new IndexOptions().background(false).unique(false).sparse(false).name("testIndexError").setCollation(new CollationOptions().setLocale("de_AT"))
      )
      .doOnError(assertIntentionalError(ctx, "intentional"))
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertFailure()));
  }


  @Test
  public void testCreateIndexWithOptionsWithCollation(TestContext ctx) {
    mock.createIndexWithOptions()
      .inCollection("createindexwithoptions")
      .withOptions(new IndexOptions().background(false).unique(true).sparse(false).setCollation(new CollationOptions().setLocale("no-way")))
      .withKey(new JsonObject().put("test", "testCreateIndexWithOptions"))
      .returns(null);

    db.rxCreateIndexWithOptions("createindexwithoptions",
        new JsonObject().put("test", "testCreateIndexWithOptions"),
        new IndexOptions().background(false).unique(true).sparse(false).setCollation(new CollationOptions().setLocale("no-way")))
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testCreateIndexWithOptionsWithCollationNoMatch(TestContext ctx) {
    mock.createIndexWithOptions()
      .inCollection("createindexwithoptions")
      .withOptions(new IndexOptions().background(false).unique(true).sparse(false).setCollation(new CollationOptions().setLocale("DIFFERENT")))
      .withKey(new JsonObject().put("test", "testCreateIndexWithOptions"))
      .returns(null);

    db.rxCreateIndexWithOptions("createindexwithoptions",
        new JsonObject().put("test", "testCreateIndexWithOptions"),
        new IndexOptions().background(false).unique(true).sparse(false).setCollation(new CollationOptions().setLocale("no-way")))
      .doOnError(assertNoMappingFoundError(ctx))
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertFailure()));
  }

}
