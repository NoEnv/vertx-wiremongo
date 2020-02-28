package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class FindOneAndDeleteTest extends TestBase {

  @Test
  public void testFindOneAndDelete(TestContext ctx) {
    Async async = ctx.async();

    mock.findOneAndDelete()
      .inCollection("findOneAndDelete")
      .withQuery(new JsonObject().put("test", "testFindOneAndDelete"))
      .returns(new JsonObject().put("field1", "value1"));

    db.rxFindOneAndDelete("findOneAndDelete", new JsonObject().put("test", "testFindOneAndDelete"))
      .subscribe(r -> {
        ctx.assertEquals("value1", r.getString("field1"));
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testFindOneAndDeleteFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxFindOneAndDelete("findOneAndDelete", new JsonObject().put("test", "testFindOneAndDeleteFile"))
      .subscribe(r -> {
        ctx.assertEquals("value1", r.getString("field1"));
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testFindOneFileError(TestContext ctx) {
    Async async = ctx.async();
    db.rxFindOneAndDelete("findOneAndDelete", new JsonObject().put("test", "testFindOneAndDeleteFileError"))
      .subscribe(r -> ctx.fail(), ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }
}
