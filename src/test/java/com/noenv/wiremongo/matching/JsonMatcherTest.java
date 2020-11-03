package com.noenv.wiremongo.matching;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Instant;

import static com.noenv.wiremongo.matching.JsonMatcher.equalToJson;
import static com.noenv.wiremongo.matching.JsonMatcher.notEqualToJson;

@RunWith(VertxUnitRunner.class)
public class JsonMatcherTest {

  @Test
  public void testEmpty(TestContext ctx) {
    assrt(ctx, true, new JsonObject(), new JsonObject());
    assrt(ctx, true, new JsonObject(), new JsonObject().put("foo", "bar"), true);
    assrt(ctx, true, new JsonArray(), new JsonArray());
  }

  @Test
  public void testEquals(TestContext ctx) {
    Matcher<JsonObject> m1 = equalToJson(new JsonObject().put("foo", "bar"));
    ctx.assertTrue(m1.matches(new JsonObject().put("foo", "bar")));
    ctx.assertFalse(m1.matches(new JsonObject().put("bar", "foo")));

    Matcher<JsonObject> m2 = equalToJson(new JsonObject("{\"foo\":{\"bar\":{\"eek\":42}}}"));
    ctx.assertTrue(m2.matches(new JsonObject("{\"foo\":{\"bar\":{\"eek\":42}}}")));
    ctx.assertFalse(m2.matches(new JsonObject("{\"foo\":{\"bar\":{\"eek\":12}}}")));

    Matcher<JsonObject> m3 = equalToJson(new JsonObject("{\"foo\":[2,5,6,7]}"));
    ctx.assertTrue(m3.matches(new JsonObject("{\"foo\":[2,5,6,7]}")));
    ctx.assertFalse(m3.matches(new JsonObject("{\"foo\":[5,2,6,7]}")));
  }

  @Test
  public void testEqualNumber(TestContext ctx) {
    JsonMatcher<JsonObject> m1 = equalToJson(new JsonObject().put("foo", 42L), true);
    ctx.assertTrue(m1.matches(new JsonObject().put("foo", 42)));

    JsonMatcher<JsonObject> m2 = equalToJson(new JsonObject().put("foo", 42), true);
    ctx.assertTrue(m2.matches(new JsonObject().put("foo", 42L)));

    JsonMatcher<JsonObject> m3 = equalToJson(new JsonObject().put("foo", 42d), true);
    ctx.assertTrue(m3.matches(new JsonObject().put("foo", 42f)));
  }

  @Test
  public void testNotEquals(TestContext ctx) {
    Matcher<JsonObject> m1 = notEqualToJson(new JsonObject().put("foo", "bar"));
    ctx.assertFalse(m1.matches(new JsonObject().put("foo", "bar")));
    ctx.assertTrue(m1.matches(new JsonObject().put("bar", "foo")));

    Matcher<JsonObject> m2 = notEqualToJson(new JsonObject("{\"foo\":{\"bar\":{\"eek\":42}}}"));
    ctx.assertFalse(m2.matches(new JsonObject("{\"foo\":{\"bar\":{\"eek\":42}}}")));
    ctx.assertTrue(m2.matches(new JsonObject("{\"foo\":{\"bar\":{\"eek\":12}}}")));

    Matcher<JsonObject> m3 = notEqualToJson(new JsonObject("{\"foo\":[2,5,6,7]}"));
    ctx.assertFalse(m3.matches(new JsonObject("{\"foo\":[2,5,6,7]}")));
    ctx.assertTrue(m3.matches(new JsonObject("{\"foo\":[5,2,6,7]}")));
  }

  @Test
  public void testIgnoreExtraElements(TestContext ctx) {
    assrt(ctx, true,
      new JsonObject().put("foo", "bar"),
      new JsonObject().put("foo", "bar").put("eek", "bar"), true);
    assrt(ctx, false,
      new JsonObject().put("foo", "bar"),
      new JsonObject().put("foo", "bar").put("eek", "bar"), false);
  }

  @Test
  public void testIgnoreArrayOrder(TestContext ctx) {
    assrt(ctx, true,
      new JsonArray("[2,4,5,6,7,8,2,3,8]"),
      new JsonArray("[4,2,6,5,7,8,2,3,8]"), false, true);
    assrt(ctx, false,
      new JsonArray("[2,4,5,6,7,8,2,3,8]"),
      new JsonArray("[4,2,6,5,7,8,2,3,8]"));
  }

  @Test
  public void testIgnoreExtraElementsInArray(TestContext ctx) {
    assrt(ctx, true,
      new JsonArray().add(new JsonObject().put("foo", "bar")),
      new JsonArray().add(new JsonObject().put("foo", "bar").put("created", Instant.now())),
      true, true);
  }

  private static void assrt(TestContext ctx, boolean expected, Object a, Object b) {
    assrt(ctx, expected, a, b, false);
  }

  private static void assrt(TestContext ctx, boolean expected, Object a, Object b, boolean ignoreExtraElements) {
    assrt(ctx, expected, a, b, ignoreExtraElements, false);
  }

  private static void assrt(TestContext ctx, boolean expected, Object a, Object b, boolean ignoreExtraElements, boolean ignoreArrayOrder) {
    if(a instanceof JsonObject) {
      ctx.assertTrue(expected == equalToJson((JsonObject)a, ignoreExtraElements, ignoreArrayOrder).matches((JsonObject)b));
    } else {
      ctx.assertTrue(expected == equalToJson((JsonArray) a, ignoreExtraElements, ignoreArrayOrder).matches((JsonArray)b));
    }
  }
}
