package com.noenv.wiremongo.matching.compare;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Comparator;

@RunWith(VertxUnitRunner.class)
public class JsonObjectComparatorTest {

  Comparator<JsonObject> comparator;
  JsonObject primitiveJson2;
  JsonObject primitiveJson3;
  JsonObject primitiveJson1;
  JsonObject nestedJson1;
  JsonObject nestedJson2;
  JsonArray array1;
  JsonArray array2;

  public JsonObjectComparatorTest() {
    comparator = new JsonObjectComparator();
    primitiveJson1 = new JsonObject()
      .put("fnord", 1)
      .put("narf", "zort")
      .put("poit", 1.2);
    primitiveJson2 = new JsonObject()
      .put("number2", 1.2)
      .put("number1", 1)
      .put("string", "x");
    primitiveJson3 = new JsonObject()
      .put("number1", 5)
      .put("string", "y")
      .put("number2", 2.3);

    array1 = new JsonArray().add(primitiveJson1.copy()).add(primitiveJson2.copy()).add(primitiveJson3.copy());
    array2 = new JsonArray().add(primitiveJson3.copy()).add(primitiveJson2.copy()).add(primitiveJson1.copy());

    nestedJson1 = primitiveJson2.copy()
      .put("object", primitiveJson1.copy())
      .put("array", array1.copy());

    nestedJson2 = primitiveJson2.copy()
      .put("object", primitiveJson3.copy())
      .put("array", array2.copy());

  }

  @Test
  public void shouldAssertJsonObjectSize(TestContext ctx) {
    ctx.assertEquals(1, comparator.compare(primitiveJson1.copy().put("another", "one"), primitiveJson1));
    ctx.assertEquals(-1, comparator.compare(primitiveJson1, primitiveJson1.copy().put("another", "one")));
  }


  @Test
  public void shouldAssertJsonObjectsWithDifferentKeys(TestContext ctx) {
    JsonObject x = new JsonObject().put("a", 1).put("b", 2).put("c", 3);
    JsonObject y = new JsonObject().put("a", 1).put("b", 2).put("d", 3);
    ctx.assertEquals(-1, comparator.compare(x, y));
  }

  @Test
  public void shouldAssertJsonObjectsAlthoughValueIsNull(TestContext ctx) {
    JsonObject x = new JsonObject().put("a", 1).put("b", 2).put("c", 3);
    JsonObject y = new JsonObject().put("a", 1).put("b", 2).put("c", null);
    ctx.assertEquals(1, comparator.compare(x, y));
  }

  @Test
  public void shouldAssertJsonObjectsDifferentClasses(TestContext ctx) {
    JsonObject x = new JsonObject().put("a", 1).put("b", 2).put("c", 3);
    JsonObject y = new JsonObject().put("a", 1).put("b", 2).put("c", "3");
    ctx.assertEquals(-1, comparator.compare(x, y));
  }

  @Test
  public void shouldAssertPrimitiveJsonObjectEquality(TestContext ctx) {
    ctx.assertEquals(0, comparator.compare(primitiveJson1, primitiveJson1));
    ctx.assertEquals(0, comparator.compare(primitiveJson2, primitiveJson2));
    ctx.assertEquals(0, comparator.compare(primitiveJson3, primitiveJson3));
  }

  @Test
  public void shouldFailOnPrimitiveJsonObjectInequality(TestContext ctx) {
    // json1 < json2 < json3
    ctx.assertEquals(-1, comparator.compare(primitiveJson1, primitiveJson3));
    ctx.assertEquals(1, comparator.compare(primitiveJson3, primitiveJson1));

    ctx.assertEquals(-1, comparator.compare(primitiveJson2, primitiveJson3));
    ctx.assertEquals(1, comparator.compare(primitiveJson3, primitiveJson2));

    ctx.assertEquals(-1, comparator.compare(primitiveJson1, primitiveJson2));
    ctx.assertEquals(1, comparator.compare(primitiveJson2, primitiveJson1));
  }

  @Test
  public void shouldAssertNestedJsonObjectEquality(TestContext ctx) {
    ctx.assertEquals(0, comparator.compare(nestedJson1, nestedJson1));
    ctx.assertEquals(0, comparator.compare(nestedJson2, nestedJson2));
  }

  @Test
  public void shouldFailOnNestedJsonObjectInequality(TestContext ctx) {
    // json1 < json2
    ctx.assertEquals(-1, comparator.compare(nestedJson1, nestedJson2));
    ctx.assertEquals(1, comparator.compare(nestedJson2, nestedJson1));
  }
}
