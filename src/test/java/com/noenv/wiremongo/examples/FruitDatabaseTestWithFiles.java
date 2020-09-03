package com.noenv.wiremongo.examples;

import com.noenv.wiremongo.WireMongo;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RunWith(VertxUnitRunner.class)
public class FruitDatabaseTestWithFiles {

  private FruitDatabase db;

  @Before
  public void setUp(TestContext ctx) {
    WireMongo wiremongo = new WireMongo(Vertx.vertx());
    db = new FruitDatabase(wiremongo.getClient());
    wiremongo.readFileMappings("example-mocks").onComplete(ctx.asyncAssertSuccess());
  }

  @Test
  public void testAddApple(TestContext ctx) {
    db.addApple(7533, Instant.now())
      .onComplete(ctx.asyncAssertSuccess());
  }

  @Test
  public void testAddBanana(TestContext ctx) {
    db.addBanana(2468, null)
      .onComplete(ctx.asyncAssertSuccess());
  }

  @Test
  public void testCountFruitByType(TestContext ctx) {
    db.countFruitByType("banana")
      .onSuccess(c -> ctx.assertEquals(71L, c))
      .onComplete(ctx.asyncAssertSuccess());
  }

  @Test
  public void testRemoveExpiredFruit(TestContext ctx) {
    db.removeExpiredFruit(Instant.parse("1984-05-27T00:01:02.241Z"))
      .onSuccess(c -> ctx.assertEquals(32L, c))
      .onComplete(ctx.asyncAssertSuccess());
  }

  @Test
  public void testInsertError(TestContext ctx) {
    db.addApple(8392, Instant.now().plus(3, ChronoUnit.DAYS))
      .onFailure(ex -> ctx.assertEquals("intentional", ex.getMessage()))
      .onComplete(ctx.asyncAssertFailure());
  }
}
