package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.TestBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.rxjava3.MaybeHelper;
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
    mock.findOne()
      .inCollection("findone")
      .withQuery(new JsonObject().put("test", "testFindOne"))
      .withFields(new JsonObject().put("field1", 1))
      .returns(new JsonObject().put("field1", "value1"));

    db.rxFindOne("findone", new JsonObject().put("test", "testFindOne"), new JsonObject().put("field1", 1))
      .flatMap(x -> db.rxFindOne("findone", new JsonObject().put("test", "testFindOne"), new JsonObject().put("field1", 1)))
      .flatMap(x -> db.rxFindOne("findone", new JsonObject().put("test", "testFindOne"), new JsonObject().put("field1", 1)))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r -> ctx.assertEquals("value1", r.getString("field1")))));
  }

  @Test
  public void testFindOneTimes(TestContext ctx) {
    mock.findOne()
      .inCollection("findone")
      .withQuery(new JsonObject().put("test", "testFindOne"))
      .withFields(new JsonObject().put("field1", 1))
      .validFor(3)
      .returns(new JsonObject().put("field1", "value1"));

    db.rxFindOne("findone", new JsonObject().put("test", "testFindOne"), new JsonObject().put("field1", 1))
      .flatMap(x -> db.rxFindOne("findone", new JsonObject().put("test", "testFindOne"), new JsonObject().put("field1", 1)))
      .flatMap(x -> db.rxFindOne("findone", new JsonObject().put("test", "testFindOne"), new JsonObject().put("field1", 1)))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess(r -> ctx.assertEquals("value1", r.getString("field1")))));
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
      .doOnError(assertNoMappingFoundError(ctx))
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

  @Test
  public void validForShouldNotOnlyCheckMethod(TestContext ctx) {
    mock.findOne().validFor(1).inCollection("foobar").withQuery(new JsonObject().put("a", "1"))
      .stub(() -> {
        mock.findOne().validFor(1).inCollection("foobar").withQuery(new JsonObject().put("a", "1"))
          .stub(() -> {
            mock.findOne().validFor(1).inCollection("foobar").withQuery(new JsonObject().put("a", "1"))
              .stub(() -> new JsonObject().put("a", "3"));
            return new JsonObject().put("a", "2");
          });
        return new JsonObject().put("a", "1");
      });

    mock.findOne().inCollection("somethingelse").withQuery(new JsonObject().put("some", "thing")).returns(new JsonObject().put("found", "something"));

    db.rxFindOne("somethingelse", new JsonObject().put("some", "thing"), new JsonObject())
      .doOnSuccess(r -> ctx.assertEquals("something", r.getString("found")))
      .flatMap(r -> db.rxFindOne("foobar", new JsonObject().put("a", "1"), new JsonObject()))
      .doOnSuccess(r -> ctx.assertEquals("1", r.getString("a")))
      .flatMap(r -> db.rxFindOne("foobar", new JsonObject().put("a", "1"), new JsonObject()))
      .doOnSuccess(r -> ctx.assertEquals("2", r.getString("a")))
      .flatMap(r -> db.rxFindOne("foobar", new JsonObject().put("a", "1"), new JsonObject()))
      .doOnSuccess(r -> ctx.assertEquals("3", r.getString("a")))
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess()));
  }

}
