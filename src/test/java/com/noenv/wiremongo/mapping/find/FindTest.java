package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

@RunWith(VertxUnitRunner.class)
public class FindTest extends TestBase {

  @Test
  public void testFind(TestContext ctx) {
    Async async = ctx.async();

    mock.find()
      .inCollection("find")
      .withQuery(new JsonObject().put("test", "testFind"))
      .returns(Arrays.asList(new JsonObject().put("field1", "value1")));

    db.rxFind("find", new JsonObject().put("test", "testFind"))
      .subscribe(r -> {
        ctx.assertEquals("value1", r.get(0).getString("field1"));
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testFindFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxFind("find", new JsonObject().put("test", "testFindFile"))
      .subscribe(r -> {
        ctx.assertEquals("value1", r.get(0).getString("field1"));
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testFindFileError(TestContext ctx) {
    Async async = ctx.async();
    db.rxFind("find", new JsonObject().put("test", "testFindFileError"))
      .subscribe(r -> ctx.fail(), ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }
}
