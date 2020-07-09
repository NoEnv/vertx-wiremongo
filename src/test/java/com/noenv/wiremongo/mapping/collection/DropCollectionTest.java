package com.noenv.wiremongo.mapping.collection;

import com.noenv.wiremongo.TestBase;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.reactivex.CompletableHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class DropCollectionTest extends TestBase {

  @Test
  public void testDropCollection(TestContext ctx) {
    mock.dropCollection()
      .inCollection("testDropCollection")
      .returns(null);
    db.rxDropCollection("testDropCollection")
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testDropCollectionFile(TestContext ctx) {
    db.rxDropCollection("testDropCollectionFile")
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testDropCollectionFileError(TestContext ctx) {
    db.rxDropCollection("testDropCollectionFileError")
      .doOnError(e -> ctx.assertEquals("intentional", e.getMessage()))
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertFailure()));
  }
}
