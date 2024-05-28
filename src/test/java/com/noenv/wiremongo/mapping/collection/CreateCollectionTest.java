package com.noenv.wiremongo.mapping.collection;

import com.noenv.wiremongo.TestBase;
import io.vertx.ext.mongo.CollationOptions;
import io.vertx.ext.mongo.CreateCollectionOptions;
import io.vertx.ext.unit.TestContext;
import io.vertx.rxjava3.CompletableHelper;
import org.junit.Test;

public class CreateCollectionTest extends TestBase {

  @Test
  public void testCreateCollection(TestContext ctx) {
    mock.createCollection()
      .inCollection("createcollection")
      .returns(null);

    db.rxCreateCollection("createcollection")
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testCreateCollectionWithOptions(TestContext ctx) {
    mock.createCollectionWithOptions()
      .inCollection("createcollectionwithoptions")
      .withOptions(new CreateCollectionOptions().setCollation(new CollationOptions()))
      .returns(null);

    db.rxCreateCollectionWithOptions("createcollectionwithoptions",
      new CreateCollectionOptions().setCollation(new CollationOptions())
    ).subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testCreateCollectionFile(TestContext ctx) {
    db.rxCreateCollection("createcollectionfile")
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testCreateCollectionWithOptionsFile(TestContext ctx) {
    db.rxCreateCollectionWithOptions("createcollectionwithoptionsfile", new CreateCollectionOptions())
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testCreateCollectionFileError(TestContext ctx) {
    db.rxCreateCollection("createcollectionfileerror")
      .doOnError(assertIntentionalError(ctx, "intentional"))
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertFailure()));
  }

  @Test
  public void testCreateCollectionWithOptionsError(TestContext ctx) {
    mock.createCollectionWithOptions()
      .inCollection("createcollectionwithoptionserror")
      .withOptions(new CreateCollectionOptions().setCollation(new CollationOptions()))
      .returnsError(new Exception("intentional"));
    db.rxCreateCollectionWithOptions("createcollectionwithoptionserror", new CreateCollectionOptions().setCollation(new CollationOptions()))
      .doOnError(assertIntentionalError(ctx, "intentional"))
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertFailure()));
  }

  @Test
  public void testCreateCollectionWithOptionsFileError(TestContext ctx) {
    db.rxCreateCollectionWithOptions("createcollectionwithoptionsfileerror", new CreateCollectionOptions())
      .doOnError(assertIntentionalError(ctx, "intentional"))
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertFailure()));
  }

  @Test
  public void testCreateCollectionWithOptionsMatch(TestContext ctx) {
    mock.createCollectionWithOptions()
      .inCollection("testCreateCollectionWithOptionsMatch")
      .withOptions(new CreateCollectionOptions().setCollation(new CollationOptions().setLocale("no-way")))
      .returns(null);

    db.rxCreateCollectionWithOptions("testCreateCollectionWithOptionsMatch", new CreateCollectionOptions().setCollation(new CollationOptions().setLocale("no-way")))
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testCreateCollectionWithOptionsNoMatch(TestContext ctx) {
    mock.createCollectionWithOptions()
      .inCollection("testCreateCollectionWithOptionsNoMatch")
      .withOptions(new CreateCollectionOptions().setCollation(new CollationOptions().setLocale("no-wy")))
      .returns(null);

    db.rxCreateCollectionWithOptions("testCreateCollectionWithOptionsNoMatch", new CreateCollectionOptions().setCollation(new CollationOptions().setLocale("no-way")))
      .doOnError(assertNoMappingFoundError(ctx))
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertFailure()));
  }
}
