package com.noenv.wiremongo.mapping.index;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.reactivex.CompletableHelper;
import io.vertx.reactivex.MaybeHelper;
import io.vertx.reactivex.SingleHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class ListIndexesTest extends TestBase {

  @Test
  public void testListIndexes(TestContext ctx) {
    mock.listIndexes()
      .inCollection("testListIndexes")
      .returns(new JsonArray().add(18).add(27));
    db.rxListIndexes("testListIndexes")
      .doOnSuccess(r -> {
        ctx.assertEquals(2, r.size());
        ctx.assertEquals(18, r.getInteger(0));
        ctx.assertEquals(27, r.getInteger(1));
      })
      .subscribe(SingleHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testListIndexesFile(TestContext ctx) {
    db.rxListIndexes("testListIndexesFile")
      .doOnSuccess(r -> {
        ctx.assertEquals(2, r.size());
        ctx.assertEquals(87, r.getInteger(0));
        ctx.assertEquals(92, r.getInteger(1));
      })
      .subscribe(SingleHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void testListIndexesFileError(TestContext ctx) {
    db.rxListIndexes("testListIndexesFileError")
      .doOnError(e -> ctx.assertEquals("intentional", e.getMessage()))
      .subscribe(SingleHelper.toObserver(ctx.asyncAssertFailure()));
  }
}
