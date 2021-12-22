package com.noenv.wiremongo.mapping.collection;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.CollationOptions;
import io.vertx.ext.mongo.CreateCollectionOptions;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class CreateCollectionTest extends TestBase {

  @Test
  public void testCreateCollection(TestContext ctx) {
    Async async = ctx.async();

    mock.createCollection()
      .inCollection("createcollection")
      .returns(null);

    db.rxCreateCollection("createcollection")
      .subscribe(async::complete, ctx::fail);
  }

  @Test
  public void testCreateCollectionWithOptions(TestContext ctx) {
    Async async = ctx.async();

    // TODO: remove setAlternate after vertx 4.3.0 release
    mock.createCollectionWithOptions()
      .inCollection("createcollectionwithoptions")
      .withOptions(new CreateCollectionOptions().setCollation(new CollationOptions().setAlternate(null)))
      .returns(null);

    db.rxCreateCollectionWithOptions("createcollectionwithoptions",
      new CreateCollectionOptions().setCollation(new CollationOptions().setAlternate(null))
    )
    .subscribe(async::complete, ctx::fail);
  }

  @Test
  public void testCreateCollectionFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxCreateCollection("createcollectionfile")
      .subscribe(async::complete, ctx::fail);
  }

  @Test
  public void testCreateCollectionWithOptionsFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxCreateCollectionWithOptions("createcollectionwithoptionsfile", new CreateCollectionOptions())
    .subscribe(async::complete, ctx::fail);
  }

  @Test
  public void testCreateCollectionFileError(TestContext ctx) {
    Async async = ctx.async();
    db.rxCreateCollection("createcollectionfileerror")
      .subscribe(ctx::fail, ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }

  @Test
  public void testCreateCollectionWithOptionsFileError(TestContext ctx) {
    Async async = ctx.async();
    db.rxCreateCollectionWithOptions("createcollectionwithoptionsfileerror", new CreateCollectionOptions())
    .subscribe(ctx::fail, ex -> {
      ctx.assertEquals("intentional", ex.getMessage());
      async.complete();
    });
  }
}
