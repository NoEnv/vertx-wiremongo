package com.noenv.wiremongo.mapping.collection;

import com.noenv.wiremongo.TestBase;
import com.noenv.wiremongo.mapping.Mapping;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.reactivex.CompletableHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(VertxUnitRunner.class)
public class GetCollectionsTest extends TestBase {

  @Test
  public void testGetCollections(TestContext ctx) {
    Async async = ctx.async();
    Mapping<?, ?, ?> m = mock.getCollections()
      .returns(Arrays.asList("collection1", "collection2"));

    db.rxGetCollections()
      .subscribe(s -> {
        ctx.assertEquals(Arrays.asList("collection1", "collection2"), s);
        mock.removeMapping(m);
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testGetCollectionError(TestContext ctx) {
    Async async = ctx.async();
    Mapping<?, ?, ?> m = mock.getCollections()
      .returnsError(new Exception("intentional"));

    db.rxGetCollections()
      .subscribe(s -> ctx.fail(), ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        mock.removeMapping(m);
        async.complete();
      });
  }

  @Test
  public void testGetCollectionReturnedObjectNotModified(TestContext ctx) {
    final List<String> given = Arrays.asList("first", "second");
    final List<String> expected = new ArrayList<>(given);

    mock.getCollections().returns(given);

    db.rxGetCollections()
      .doOnSuccess(actual -> ctx.assertEquals(expected, actual))
      .doOnSuccess(actual -> {
        actual.remove(0);
        actual.add("add");
      })
      .repeat(2)
      .ignoreElements()
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }
}
