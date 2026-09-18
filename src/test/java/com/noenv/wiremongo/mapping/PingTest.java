package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.rxjava3.CompletableHelper;
import io.vertx.rxjava3.MaybeHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class PingTest extends TestBase {

  @Test
  public void testPing(TestContext ctx) {
    Mapping<?, ?, ?> m = mock.ping()
      .returns(new JsonObject().put("ok", 1));

    db.rxPing()
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r -> {
        ctx.assertEquals(1, r.getInteger("ok"));
        mock.removeMapping(m);
      })));
  }

  @Test
  public void testPingError(TestContext ctx) {
    Mapping<?, ?, ?> m = mock.ping()
      .returnsError(new Exception("intentional"));

    db.rxPing()
      .doOnError(assertIntentionalError(ctx, "intentional"))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertFailure(ex -> mock.removeMapping(m))));
  }

  @Test
  public void testPingReturnedObjectNotModified(TestContext ctx) {
    final JsonObject given = new JsonObject().put("ok", 1).put("field", "value");
    final JsonObject expected = given.copy();

    Mapping<?, ?, ?> m = mock.ping().returns(given);

    db.rxPing()
      .doOnSuccess(actual -> ctx.assertEquals(expected, actual))
      .doOnSuccess(actual -> {
        actual.put("ok", "replace");
        actual.remove("field");
        actual.put("add", "add");
      })
      .repeat(2)
      .ignoreElements()
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess(v -> mock.removeMapping(m))));
  }

  @Test
  public void testPingMappingFromJson(TestContext ctx) {
    Mapping<?, ?, ?> m = Mapping.create(new JsonObject()
      .put("method", "ping")
      .put("response", new JsonObject().put("ok", 1)));

    ctx.assertTrue(m instanceof Ping);
    ctx.assertEquals("ping", ((Ping) m).method());
  }
}
