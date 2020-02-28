package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

@RunWith(VertxUnitRunner.class)
public class FindWithOptionsTest extends TestBase {

  @Test
  public void testFindWithOptions(TestContext ctx) {
    Async async = ctx.async();

    mock.findWithOptions()
      .inCollection("findwithoptions")
      .withQuery(new JsonObject().put("test", "testFindWithOptions"))
      .withOptions(new FindOptions().setLimit(42))
      .returns(Arrays.asList(new JsonObject().put("field1", "value1")));

    db.rxFindWithOptions("findwithoptions", new JsonObject().put("test", "testFindWithOptions"), new FindOptions().setLimit(42))
      .subscribe(r -> {
        ctx.assertEquals("value1", r.get(0).getString("field1"));
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testFindWithOptionsFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxFindWithOptions("findwithoptions", new JsonObject().put("test", "testFindWithOptionsFile"), new FindOptions().setSkip(42))
      .subscribe(r -> {
        ctx.assertEquals("value1", r.get(0).getString("field1"));
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testFindWithOptionsFileError(TestContext ctx) {
    Async async = ctx.async();
    db.rxFindWithOptions("findwithoptions", new JsonObject().put("test", "testFindWithOptionsFileError"), new FindOptions().setSkip(42))
      .subscribe(r -> ctx.fail(), ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }
}
