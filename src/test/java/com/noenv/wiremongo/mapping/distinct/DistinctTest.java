package com.noenv.wiremongo.mapping.distinct;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.reactivex.CompletableHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class DistinctTest extends TestBase {

  @Test
  public void testDistinct(TestContext ctx) {
    Async async = ctx.async();

    mock.distinct()
      .inCollection("distinct")
      .withFieldName("testDistinct")
      .returns(new JsonArray().add("A").add("B"));

    db.rxDistinct("distinct", "testDistinct", null)
      .subscribe(r -> {
        ctx.assertEquals(2, r.size());
        ctx.assertEquals("A", r.getString(0));
        ctx.assertEquals("B", r.getString(1));
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testDistinctFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxDistinct("distinct", "testDistinctFile", "java.lang.String")
      .subscribe(r -> {
        ctx.assertEquals(3, r.size());
        ctx.assertEquals("A", r.getString(0));
        ctx.assertEquals("B", r.getString(1));
        ctx.assertEquals("C", r.getString(2));
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testDistinctFileError(TestContext ctx) {
    Async async = ctx.async();
    db.rxDistinct("distinct", "testDistinctFileError", "java.lang.Integer")
      .subscribe(r -> ctx.fail(), ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }
}
