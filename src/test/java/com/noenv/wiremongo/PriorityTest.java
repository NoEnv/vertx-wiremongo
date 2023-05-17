package com.noenv.wiremongo;

import com.noenv.wiremongo.mapping.Mapping;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class PriorityTest {

  private WireMongo wiremongo;

  @Before
  public void setUp() {
    wiremongo = new WireMongo();
  }

  @Test
  public void testDefaultPriority(TestContext ctx) {
    Mapping<?, ?, ?> a = wiremongo.count().returns(42L);
    Mapping<?, ?, ?> b = wiremongo.count().returns(21L);

    ctx.assertTrue(b.priority() > a.priority()); // more recently added should have higher priority

    wiremongo.getClient().count("foo", new JsonObject())
      .onComplete(ctx.asyncAssertSuccess(r -> ctx.assertEquals(21L, r)));
  }

  @Test
  public void testPriority(TestContext ctx) {
    wiremongo.count().priority(20).returns(42L);
    wiremongo.count().priority(10).returns(21L);
    wiremongo.getClient().count("foo", new JsonObject())
      .onComplete(ctx.asyncAssertSuccess(r -> ctx.assertEquals(42L, r)));
  }

  @Test
  public void testPriorityFiles(TestContext ctx) {
    wiremongo = new WireMongo(Vertx.vertx());
    wiremongo.readFileMappings("wiremongo-mocks")
      .flatMap(x -> wiremongo.getClient().count("priority", new JsonObject().put("test", "testPriorityFile")))
      .onComplete(ctx.asyncAssertSuccess(r -> ctx.assertEquals(333L, r)));
  }
}
