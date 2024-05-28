package com.noenv.wiremongo.mapping.collection;

import com.noenv.wiremongo.TestBase;
import com.noenv.wiremongo.mapping.Mapping;
import io.vertx.ext.unit.TestContext;
import io.vertx.rxjava3.CompletableHelper;
import io.vertx.rxjava3.SingleHelper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetCollectionsTest extends TestBase {

  @Test
  public void testGetCollections(TestContext ctx) {
    Mapping<?, ?, ?> m = mock.getCollections()
      .returns(Arrays.asList("collection1", "collection2"));

    db.rxGetCollections()
    .subscribe(SingleHelper.toObserver(ctx.asyncAssertSuccess(s -> {
      ctx.assertEquals(Arrays.asList("collection1", "collection2"), s);
      mock.removeMapping(m);
    })));
  }

  @Test
  public void testGetCollectionError(TestContext ctx) {
    Mapping<?, ?, ?> m = mock.getCollections()
      .returnsError(new Exception("intentional"));

    db.rxGetCollections()
    .doOnError(assertIntentionalError(ctx, "intentional"))
    .subscribe(SingleHelper.toObserver(ctx.asyncAssertFailure(ex -> mock.removeMapping(m))));
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
