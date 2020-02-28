package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class FindOneTest extends TestBase {

  @Test
  public void testFindOne(TestContext ctx) {
    Async async = ctx.async();

    mock.findOne()
      .inCollection("findone")
      .withQuery(new JsonObject().put("test", "testFindOne"))
      .withFields(new JsonObject().put("field1", 1))
      .returns(new JsonObject().put("field1", "value1"));

    db.rxFindOne("findone", new JsonObject().put("test", "testFindOne"), new JsonObject().put("field1", 1))
      .subscribe(r -> {
        ctx.assertEquals("value1", r.getString("field1"));
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testFindOneFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxFindOne("findone", new JsonObject().put("test", "testFindOneFile"), new JsonObject().put("field1", 1))
      .subscribe(r -> {
        ctx.assertEquals("value1", r.getString("field1"));
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testFindOneFileError(TestContext ctx) {
    Async async = ctx.async();
    db.rxFindOne("findone", new JsonObject().put("test", "testFindOneFileError"), null)
      .subscribe(r -> ctx.fail(), ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }
}
