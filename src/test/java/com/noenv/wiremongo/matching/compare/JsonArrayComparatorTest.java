package com.noenv.wiremongo.matching.compare;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Comparator;

@RunWith(VertxUnitRunner.class)
public class JsonArrayComparatorTest {


  Comparator<JsonArray> comparator;
  Comparator<JsonArray> ignoreOrderComperator;
  JsonObject primitiveJson2;
  JsonObject primitiveJson3;
  JsonObject primitiveJson1;
  JsonObject nestedJson1;
  JsonObject nestedJson2;
  JsonArray array1;
  JsonArray array2;
  JsonArray array3;

  public JsonArrayComparatorTest() {
    comparator = new JsonArrayComparator();
    ignoreOrderComperator = new JsonArrayComparator(true);
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
    array3 = new JsonArray().add(primitiveJson3.copy()).add(primitiveJson2.copy()).add(primitiveJson1.copy()).add("blub").add(1).add(primitiveJson1.copy());
  }

  @Test
  public void shouldAssertJsonArrayEquality(TestContext ctx) {
    ctx.assertEquals(0, comparator.compare(array1, array1));
    ctx.assertEquals(0, comparator.compare(array2, array2));
  }

  @Test
  public void shouldAssertJsonArrayEqualityIgnoreOrder(TestContext ctx) {
    ctx.assertEquals(0, ignoreOrderComperator.compare(array2, array1));
    ctx.assertEquals(0, ignoreOrderComperator.compare(array1, array2));
  }

  @Test
  public void shouldFailOnJsonArrayInequality(TestContext ctx) {
    // array1 < array2
    ctx.assertEquals(-1, comparator.compare(array1, array2));
    ctx.assertEquals(1, comparator.compare(array2, array1));
  }

  @Test
  public void shouldFailOnJsonArrayInequalityIgnoreOrder(TestContext ctx) {
    ctx.assertEquals(-1, comparator.compare(array1, array3));
    ctx.assertEquals(1, comparator.compare(array3, array1));

    ctx.assertEquals(-1, comparator.compare(array2, array3));
    ctx.assertEquals(1, comparator.compare(array3, array2));
  }
}
