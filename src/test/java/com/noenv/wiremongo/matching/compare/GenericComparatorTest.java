package com.noenv.wiremongo.matching.compare;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Comparator;

@RunWith(VertxUnitRunner.class)
public class GenericComparatorTest {
  Comparator<Object> comparator;
  JsonObject primitiveJson2;
  JsonObject primitiveJson3;
  JsonObject primitiveJson1;
  JsonObject nestedJson1;
  JsonObject nestedJson2;
  JsonArray array1;
  JsonArray array2;

  public GenericComparatorTest() {
    comparator = new GenericComparator();
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
  public void shouldAssertSameTypes(TestContext ctx) {
    ctx.assertEquals(0, comparator.compare(primitiveJson1, primitiveJson1));
    ctx.assertEquals(0, comparator.compare(primitiveJson2, primitiveJson2));

    ctx.assertEquals(0, comparator.compare(array1, array1));
    ctx.assertEquals(0, comparator.compare(array2, array2));

    ctx.assertEquals(0, comparator.compare("abc", "abc"));
    ctx.assertEquals(0, comparator.compare("xyz", "xyz"));

    ctx.assertEquals(0, comparator.compare(1, 1));
    ctx.assertEquals(0, comparator.compare(2, 2));
  }

  @Test
  public void shouldAssertDifferentTypes(TestContext ctx) {
    ctx.assertEquals(-1, comparator.compare(primitiveJson1, 1));
    ctx.assertEquals(1, comparator.compare(1, primitiveJson2));

    ctx.assertEquals(-1, comparator.compare(primitiveJson1, "abc"));
    ctx.assertEquals(1, comparator.compare("abc", primitiveJson2));

    ctx.assertEquals(-1, comparator.compare(array1, 1));
    ctx.assertEquals(1, comparator.compare(1, array2));

    ctx.assertEquals(-1, comparator.compare(array1, "abc"));
    ctx.assertEquals(1, comparator.compare("abc", array2));

    ctx.assertEquals(-1, comparator.compare(array1, primitiveJson1));
    ctx.assertEquals(1, comparator.compare(primitiveJson1, array1));
  }

  @Test
  public void shouldAssertNull(TestContext ctx) {
    ctx.assertEquals(-1, comparator.compare(null, 1));
    ctx.assertEquals(1, comparator.compare(1, null));
    ctx.assertEquals(0, comparator.compare(null, null));
  }
}
