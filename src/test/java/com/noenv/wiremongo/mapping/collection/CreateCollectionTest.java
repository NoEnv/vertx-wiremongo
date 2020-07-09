package com.noenv.wiremongo.mapping.collection;

import com.noenv.wiremongo.TestBase;
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
  public void testCreateCollectionFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxCreateCollection("createcollectionfile")
      .subscribe(async::complete, ctx::fail);
  }

  @Test
  public void testCreateCollectionFileError(TestContext ctx) {
    Async async = ctx.async();
    db.rxCreateCollection("createcollectionfileerror")
      .subscribe(() -> ctx.fail(), ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }
}
