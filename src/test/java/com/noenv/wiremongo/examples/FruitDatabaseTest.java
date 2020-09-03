package com.noenv.wiremongo.examples;

import com.noenv.wiremongo.WireMongo;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientDeleteResult;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static com.noenv.wiremongo.matching.JsonMatcher.equalToJson;

@RunWith(VertxUnitRunner.class)
public class FruitDatabaseTest {

  private WireMongo wiremongo;
  private FruitDatabase db;

  @Before
  public void setUp() {
    wiremongo = new WireMongo();
    db = new FruitDatabase(wiremongo.getClient());
  }

  @Test
  public void testAddApple(TestContext ctx) {
    Instant expiration = Instant.now().plus(5, ChronoUnit.DAYS);

    wiremongo.insert()
      .inCollection("fruits")
      .withDocument(new JsonObject()
        .put("type", "apple")
        .put("mass", 161)
        .put("expiration", new JsonObject().put("$date", expiration)))
      .returnsObjectId();

    db.addApple(161, expiration)
      .onComplete(ctx.asyncAssertSuccess());
  }

  @Test
  public void testAddBanana(TestContext ctx) {
    wiremongo.insert()
      .inCollection("fruits")
      .withDocument(equalToJson(new JsonObject()
        .put("type", "banana")
        .put("mass", 721), true))
      .returnsObjectId();

    db.addBanana(721, null)
      .onComplete(ctx.asyncAssertSuccess());
  }

  @Test
  public void testCountFruitByType(TestContext ctx) {
    wiremongo.count()
      .inCollection("fruits")
      .withQuery(q -> q.getString("type").equals("banana"))
      .returns(42L);

    db.countFruitByType("banana")
      .onSuccess(c -> ctx.assertEquals(42L, c))
      .onComplete(ctx.asyncAssertSuccess());
  }

  @Test
  public void testRemoveExpiredFruit(TestContext ctx) {
    Instant cutoff = Instant.now();
    wiremongo.removeDocument()
      .inCollection("fruits")
      .withQuery(q -> q.getJsonObject("expiration").getJsonObject("$lt").getInstant("$date").equals(cutoff))
      .returns(new MongoClientDeleteResult(16));

    db.removeExpiredFruit(cutoff)
      .onSuccess(c -> ctx.assertEquals(16L, c))
      .onComplete(ctx.asyncAssertSuccess());
  }

  @Test
  public void testInsertError(TestContext ctx) {
    wiremongo.insert()
      .returnsDuplicateKeyError();

    db.addApple(123, Instant.now().plus(3, ChronoUnit.DAYS))
      .onComplete(ctx.asyncAssertFailure());
  }
}
