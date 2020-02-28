package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class RunCommandTest extends TestBase {

  @Test
  public void testRunCommand(TestContext ctx) {
    Async async = ctx.async();

    mock.runCommand()
      .withCommand("ping", new JsonObject().put("ping", 1))
      .returns(new JsonObject().put("ok", 1));

    db.rxRunCommand("ping", new JsonObject().put("ping", 1))
      .subscribe(r -> {
        ctx.assertEquals(1, r.getInteger("ok"));
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testRunCommandFile(TestContext ctx) {
    Async async = ctx.async();
    db.rxRunCommand("ping", new JsonObject().put("ping", 1))
      .subscribe(r -> {
        ctx.assertEquals(1, r.getInteger("ok"));
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testRunCommandFileError(TestContext ctx) {
    Async async = ctx.async();
    db.rxRunCommand("ping", new JsonObject().put("ping", 1).put("fail", true))
      .subscribe(r -> ctx.fail(), ex -> {
        ctx.assertEquals("intentional", ex.getMessage());
        async.complete();
      });
  }
}
