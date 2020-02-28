package com.noenv.wiremongo.matching;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;
import static com.noenv.wiremongo.matching.EqualsMatcher.notEqualTo;

@RunWith(VertxUnitRunner.class)
public class EqualsMatcherTest {

  @Test
  public void testEquals(TestContext ctx) {
    Matcher<String> m1 = equalTo("foobar");
    ctx.assertTrue(m1.matches("foobar"));
    ctx.assertFalse(m1.matches("foo"));

    Matcher<JsonObject> m2 = equalTo(new JsonObject().put("foo", "bar"));
    ctx.assertTrue(m2.matches(new JsonObject().put("foo", "bar")));
    ctx.assertFalse(m2.matches(new JsonObject().put("bar", "foo")));
  }

  @Test
  public void testNotEquals(TestContext ctx) {
    Matcher<String> m1 = notEqualTo("foobar");
    ctx.assertFalse(m1.matches("foobar"));
    ctx.assertTrue(m1.matches("foo"));

    Matcher<JsonObject> m2 = notEqualTo(new JsonObject().put("foo", "bar"));
    ctx.assertFalse(m2.matches(new JsonObject().put("foo", "bar")));
    ctx.assertTrue(m2.matches(new JsonObject().put("bar", "foo")));
  }
}
