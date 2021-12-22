package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.rxjava3.SingleHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class MatchAllTest extends TestBase {

  @Test
  public void testMatchAll(TestContext ctx) {
    mock.matchAll()
      .stub(c -> {
        ctx.assertEquals("count", c.method());
        return 41L;
      });

    db.rxCount("count", new JsonObject().put("test", "testCount"))
      .subscribe(SingleHelper.toObserver(ctx.asyncAssertSuccess(s -> ctx.assertEquals(41L, s))));
  }
}
