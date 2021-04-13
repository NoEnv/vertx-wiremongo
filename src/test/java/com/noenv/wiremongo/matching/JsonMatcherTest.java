package com.noenv.wiremongo.matching;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.noenv.wiremongo.matching.JsonMatcher.equalToJson;
import static com.noenv.wiremongo.matching.JsonMatcher.notEqualToJson;

@RunWith(VertxUnitRunner.class)
public class JsonMatcherTest {

  private static void assrt(TestContext ctx, boolean expected, Object a, Object b) {
    assrt(ctx, expected, a, b, false);
  }

  private static void assrt(TestContext ctx, boolean expected, Object a, Object b, boolean ignoreExtraElements) {
    assrt(ctx, expected, a, b, ignoreExtraElements, false);
  }

  private static void assrt(TestContext ctx, boolean expected, Object a, Object b, boolean ignoreExtraElements, boolean ignoreArrayOrder) {
    if (a instanceof JsonObject) {
      ctx.assertTrue(expected == equalToJson((JsonObject) a, ignoreExtraElements, ignoreArrayOrder).matches((JsonObject) b));
    } else {
      ctx.assertTrue(expected == equalToJson((JsonArray) a, ignoreExtraElements, ignoreArrayOrder).matches((JsonArray) b));
    }
  }

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

  @Test
  public void testIgnoreArrayOrderOnJsonArrayWithNestedJsonObject(TestContext ctx) {
    JsonArray a = new JsonArray().add(new JsonObject().put("foo", "bar")).add(new JsonObject().put("bar", "foo"));
    JsonArray b = new JsonArray().add(new JsonObject().put("bar", "foo")).add(new JsonObject().put("foo", "bar"));

    assrt(ctx, true, a, b, false, true);
    assrt(ctx, false, a, b);
  }

  @Test
  public void testIgnoreArrayOrderOnJsonArrayWithNestedJsonObjectAndNestedJsonArray(TestContext ctx) {
    JsonArray a = new JsonArray()
      .add(
        new JsonObject()
          .put("foo", "bar")
          .put("some-list", new JsonArray("[1,2,3,4]"))
      ).add(new JsonObject().put("bar", "foo"));
    JsonArray b = new JsonArray()
      .add(new JsonObject().put("bar", "foo"))
      .add(
        new JsonObject()
          .put("some-list", new JsonArray("[4,1,3,2]"))
          .put("foo", "bar")
      );

    assrt(ctx, true, a, b, false, true);
    assrt(ctx, false, a, b);
  }

  @Test
  public void testIgnoreArrayOrderOnJsonObjectWithNestedJsonArray(TestContext ctx) {
    JsonObject a = new JsonObject()
      .put("foo", "bar")
      .put("some-list", new JsonArray("[1,2,3,4]"));
    JsonObject b = new JsonObject()
      .put("foo", "bar")
      .put("some-list", new JsonArray("[4,1,3,2]"));

    assrt(ctx, true, a, b, false, true);
    assrt(ctx, false, a, b);
  }

  @Test
  public void testIgnoreArrayOrderOnJsonObjectWithNestedJsonArrayAndNestedJsonObject(TestContext ctx) {
    JsonObject a = new JsonObject()
      .put("foo", "bar")
      .put("some-list",
        new JsonArray()
          .add(new JsonObject().put("fnord", "dronf"))
          .add(new JsonObject().put("jibber", "jabber"))
      );
    JsonObject b = new JsonObject()
      .put("some-list",
        new JsonArray()
          .add(new JsonObject().put("jibber", "jabber"))
          .add(new JsonObject().put("fnord", "dronf"))
      ).put("foo", "bar");

    assrt(ctx, true, a, b, false, true);
    assrt(ctx, false, a, b);
  }

  @Test
  public void testIgnoreExtraElementsOnJsonArray(TestContext ctx) {
    JsonArray a = new JsonArray("[1,2,3,4,5]");
    JsonArray aShuffled = new JsonArray("[5,3,1,4,2]");
    JsonArray b = new JsonArray("[1,2,3,4,5,6]");
    JsonArray bShuffled = new JsonArray("[3,1,4,6,2,5]");

    // prerequisite: containment (not equality)
    ctx.assertTrue(equalToJson(a, true, true).matches(b)); // a ⊆ b SET
    ctx.assertFalse(equalToJson(b, true, true).matches(a)); // b ⊈ a SET
    ctx.assertTrue(equalToJson(a, true, false).matches(b)); // a ⊆ b LIST
    ctx.assertFalse(equalToJson(b, true, false).matches(a)); // b ⊈ a LIST

    ctx.assertTrue(equalToJson(a, true, true).matches(bShuffled)); // a ⊆ bShuffled SET
    ctx.assertFalse(equalToJson(bShuffled, true, true).matches(a)); // bShuffled ⊈ a SET
    ctx.assertFalse(equalToJson(a, true, false).matches(bShuffled)); // a ⊈ bShuffled LIST
    ctx.assertFalse(equalToJson(bShuffled, true, false).matches(a)); // bShuffled ⊈ a LIST

    ctx.assertTrue(equalToJson(aShuffled, true, true).matches(b)); // aShuffled ⊆ b SET
    ctx.assertFalse(equalToJson(b, true, true).matches(aShuffled)); // b ⊈ aShuffled SET
    ctx.assertFalse(equalToJson(aShuffled, true, false).matches(b)); // aShuffled ⊈ b LIST
    ctx.assertFalse(equalToJson(b, true, false).matches(aShuffled)); // b ⊈ aShuffled LIST

    ctx.assertTrue(equalToJson(aShuffled, true, true).matches(bShuffled)); // aShuffled ⊆ bShuffled SET
    ctx.assertFalse(equalToJson(bShuffled, true, true).matches(aShuffled)); // bShuffled ⊈ aShuffled SET
    ctx.assertFalse(equalToJson(aShuffled, true, false).matches(bShuffled)); // aShuffled ⊆ bShuffled LIST
    ctx.assertFalse(equalToJson(bShuffled, true, false).matches(aShuffled)); // bShuffled ⊈ aShuffled LIST
  }

  @Test
  public void testIgnoreArrayOrderOnJsonArray(TestContext ctx) {
    JsonArray a = new JsonArray("[1,2,3,4,5]");
    JsonArray aShuffled = new JsonArray("[5,3,1,4,2]");
    JsonArray b = new JsonArray("[1,2,3,4,5,6]");
    JsonArray bShuffled = new JsonArray("[3,1,4,6,2,5]");

    // prerequisite: set-equality (order irrelevant)

    ctx.assertTrue(equalToJson(a, false, true).matches(a)); // a = a
    ctx.assertTrue(equalToJson(b, false, true).matches(b)); // b = b
    ctx.assertTrue(equalToJson(aShuffled, false, true).matches(aShuffled)); // aShuffled = aShuffled
    ctx.assertTrue(equalToJson(bShuffled, false, true).matches(bShuffled)); // bShuffled = bShuffled


    ctx.assertFalse(equalToJson(a, false, true).matches(b)); // a ≠ b
    ctx.assertFalse(equalToJson(b, false, true).matches(a)); // b ≠ a

    ctx.assertFalse(equalToJson(a, false, true).matches(bShuffled)); // a ≠ bShuffled
    ctx.assertFalse(equalToJson(bShuffled, false, true).matches(a)); // bShuffled ≠ a

    ctx.assertFalse(equalToJson(aShuffled, false, true).matches(b)); // aShuffled ≠ b
    ctx.assertFalse(equalToJson(b, false, true).matches(aShuffled)); // b ≠ aShuffled

    ctx.assertFalse(equalToJson(aShuffled, false, true).matches(bShuffled)); // aShuffled ≠ bShuffled
    ctx.assertFalse(equalToJson(bShuffled, false, true).matches(aShuffled)); // bShuffled ≠ aShuffled
  }

  @Test
  public void shouldMatchJsonArrayWithDefaultParametersCorrectly(TestContext ctx) {
    JsonObject a = new JsonObject().put("a", new JsonObject().put("b", 1));
    JsonObject b = new JsonObject().put("a", new JsonObject().put("b", 1).put("c", 2));
    JsonArray x = new JsonArray().add(a).add(b);
    JsonArray y = new JsonArray().add(b).add(a);

    ctx.assertTrue(notEqualToJson(x).matches(y));
    ctx.assertTrue(notEqualToJson(y).matches(x));
    ctx.assertTrue(equalToJson(x).matches(x));
    ctx.assertTrue(equalToJson(y).matches(y));
  }

  @Test
  public void shouldMatchArbitraryObjectWithDefaultParametersCorrectly(TestContext ctx) {
    class ArbitraryClass {
      final int x;
      final int y;

      public ArbitraryClass(int x, int y) {
        this.x = x;
        this.y = y;
      }
    }

    Function<ArbitraryClass, Object> toJson = (ArbitraryClass a) -> new JsonObject().put("x", a.x).put("y", a.y);
    Function<List<ArbitraryClass>, Object> toJsonArray = (List<ArbitraryClass> l) ->
      l.stream().map(a -> new JsonObject().put("x", a.x).put("y", a.y)).reduce(new JsonArray(), JsonArray::add, (e1, e2) -> new JsonArray().add(e1).add(e2));


    ArbitraryClass a = new ArbitraryClass(1, 2);
    ArbitraryClass b = new ArbitraryClass(2, 1);

    ArrayList<ArbitraryClass> aList = new ArrayList<>();
    aList.add(a);
    aList.add(b);
    ArrayList<ArbitraryClass> bList = new ArrayList<>();
    bList.add(b);
    bList.add(a);

    JsonObject aJson = new JsonObject().put("x", 1).put("y", 2);
    JsonObject bJson = new JsonObject().put("x", 2).put("y", 1);

    JsonArray aJsonArray = new JsonArray().add(aJson).add(bJson);
    JsonArray bJsonArray = new JsonArray().add(bJson).add(aJson);

    ctx.assertTrue(equalToJson(aJson, toJson).matches(a));
    ctx.assertTrue(equalToJson(bJson, toJson).matches(b));

    ctx.assertTrue(equalToJson(bJsonArray, toJsonArray).matches(bList));
    ctx.assertTrue(equalToJson(aJsonArray, toJsonArray).matches(aList));

  }

}
