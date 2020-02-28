package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class FindOneAndUpdateTest extends TestBase {

  @Test
  public void testFindOneAndUpdate(TestContext ctx) {
    Async async = ctx.async();

    mock.findOneAndUpdate()
      .inCollection("findoneandupdate")
      .withQuery(new JsonObject().put("test", "testFindOneAndUpdate"))
      .withUpdate(new JsonObject().put("foo", "bar"))
      .returns(new JsonObject().put("field1", "value1"));

    db.rxFindOneAndUpdate("findoneandupdate", new JsonObject().put("test", "testFindOneAndUpdate"), new JsonObject().put("foo", "bar"))
      .subscribe(r -> {
        ctx.assertEquals("value1", r.getString("field1"));
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testFindOneAndUpdateFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxFindOneAndUpdate("findoneandupdate", new JsonObject().put("test", "testFindOneAndUpdateFile"), new JsonObject().put("foo", "bar"))
      .subscribe(r -> {
        ctx.assertEquals("value1", r.getString("field1"));
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testFindOneAndUpdateFileError(TestContext ctx) {
    Async async = ctx.async();
    db.rxFindOneAndUpdate("findoneandupdate", new JsonObject().put("test", "testFindOneAndUpdateFileError"), new JsonObject().put("foo", "bar"))
      .subscribe(r -> ctx.fail(), ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }
}
