package com.noenv.wiremongo.mapping.collection;

import com.noenv.wiremongo.TestBase;
import io.vertx.ext.mongo.RenameCollectionOptions;
import io.vertx.ext.unit.TestContext;
import io.vertx.rxjava3.CompletableHelper;
import org.junit.Test;

public class RenameCollectionTest extends TestBase {

  @Test
  public void testRenameCollection(TestContext ctx) {
    mock.renameCollection()
      .inCollection("testRenameCollection")
      .returns(null);
    db.rxRenameCollection("testRenameCollection1", "testRenameCollection2")
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testRenameCollectionError(TestContext ctx) {
    db.rxRenameCollection("testRenameCollectionError1", "testRenameCollectionError2")
      .doOnError(e -> ctx.assertEquals("intentional", e.getMessage()))
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertFailure()));
  }

  @Test
  public void testRenameCollectionWithOptions(TestContext ctx) {
    mock.renameCollectionWithOptions()
      .inCollection("testRenameCollectionWithOptions")
      .returns(null);
    db.rxRenameCollectionWithOptions("testRenameCollectionWithOptions1", "testRenameCollectionWithOptions2", new RenameCollectionOptions())
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testRenameCollectionWithOptionsError(TestContext ctx) {
    db.rxRenameCollectionWithOptions("testRenameCollectionWithOptionsError1", "testRenameCollectionWithOptionsError2", new RenameCollectionOptions())
      .doOnError(e -> ctx.assertEquals("intentional", e.getMessage()))
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertFailure()));
  }
}
