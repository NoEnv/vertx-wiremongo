package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.reactivex.MaybeHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class ValidForTest extends TestBase {

  @Before
  public void before(TestContext ctx) {
    mock.clear();
  }

  @Test
  public void testFindOneMultipleTimesWithoutTimes(TestContext ctx) {
    Async async = ctx.async();

    mock.findOne()
      .inCollection("findone")
      .withQuery(new JsonObject().put("test", "testFindOne"))
      .withFields(new JsonObject().put("field1", 1))
      .returns(new JsonObject().put("field1", "value1"));

    db.rxFindOne("findone", new JsonObject().put("test", "testFindOne"), new JsonObject().put("field1", 1))
      .flatMap(x -> db.rxFindOne("findone", new JsonObject().put("test", "testFindOne"), new JsonObject().put("field1", 1)))
      .flatMap(x -> db.rxFindOne("findone", new JsonObject().put("test", "testFindOne"), new JsonObject().put("field1", 1)))
      .subscribe(r -> {
        ctx.assertEquals("value1", r.getString("field1"));
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testFindOneTimes(TestContext ctx) {
    Async async = ctx.async();

    mock.findOne()
      .inCollection("findone")
      .withQuery(new JsonObject().put("test", "testFindOne"))
      .withFields(new JsonObject().put("field1", 1))
      .validFor(3)
      .returns(new JsonObject().put("field1", "value1"));

    db.rxFindOne("findone", new JsonObject().put("test", "testFindOne"), new JsonObject().put("field1", 1))
      .flatMap(x -> db.rxFindOne("findone", new JsonObject().put("test", "testFindOne"), new JsonObject().put("field1", 1)))
      .flatMap(x -> db.rxFindOne("findone", new JsonObject().put("test", "testFindOne"), new JsonObject().put("field1", 1)))
      .subscribe(r -> {
        ctx.assertEquals("value1", r.getString("field1"));
        async.complete();
      }, ctx::fail);
  }

  @Test
  public void testFindOneTooOften(TestContext ctx) {
    mock.findOne()
      .inCollection("findone")
      .withQuery(new JsonObject().put("test", "testFindOne"))
      .withFields(new JsonObject().put("field1", 1))
      .validFor(2)
      .returns(new JsonObject().put("field1", "value1"));

    db.rxFindOne("findone", new JsonObject().put("test", "testFindOne"), new JsonObject().put("field1", 1))
      .flatMap(x -> db.rxFindOne("findone", new JsonObject().put("test", "testFindOne"), new JsonObject().put("field1", 1)))
      .flatMap(x -> db.rxFindOne("findone", new JsonObject().put("test", "testFindOne"), new JsonObject().put("field1", 1)))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertFailure()));
  }

  @Test
  public void testFindOneMultipleTimesWithoutValidForRestriction(TestContext ctx) {
    mock.findOne()
      .inCollection("findone")
      .withQuery(new JsonObject().put("test", "testFindOne"))
      .withFields(new JsonObject().put("field1", 1))
      .returns(new JsonObject().put("field1", "value1"));

    db.rxFindOne("findone", new JsonObject().put("test", "testFindOne"), new JsonObject().put("field1", 1))
      .flatMap(x -> db.rxFindOne("findone", new JsonObject().put("test", "testFindOne"), new JsonObject().put("field1", 1)))
      .flatMap(x -> db.rxFindOne("findone", new JsonObject().put("test", "testFindOne"), new JsonObject().put("field1", 1)))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess()));
  }

}
