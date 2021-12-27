package com.noenv.wiremongo.matching;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.Objects;
import java.util.function.Function;
import java.util.stream.IntStream;

public class JsonMatcher<T> implements Matcher<T> {

  private final Object value;
  private final Function<T, Object> toJson;
  private final boolean ignoreExtraElements;
  private final boolean ignoreArrayOrder;

  JsonMatcher(JsonObject json, Function<T, Object> toJson) {
    this(json.getValue("equalToJson"),
      toJson,
      json.getBoolean("ignoreExtraElements", false),
      json.getBoolean("ignoreArrayOrder", false));
  }

  private JsonMatcher(Object value, Function<T, Object> toJson, boolean ignoreExtraElements, boolean ignoreArrayOrder) {
    if (!(value instanceof JsonObject || value instanceof JsonArray)) {
      throw new IllegalArgumentException("value has to be a JsonObject or JsonArray");
    }
    this.value = value;
    this.toJson = toJson;
    this.ignoreExtraElements = ignoreExtraElements;
    this.ignoreArrayOrder = ignoreArrayOrder;
  }

  public static JsonMatcher<JsonObject> equalToJson(JsonObject value) {
    return equalToJson(value, false);
  }

  public static JsonMatcher<JsonObject> equalToJson(JsonObject value, boolean ignoreExtraElements) {
    return equalToJson(value, ignoreExtraElements, false);
  }

  public static JsonMatcher<JsonObject> equalToJson(JsonObject value, boolean ignoreExtraElements, boolean ignoreArrayOrder) {
    return equalToJson(value, i -> i, ignoreExtraElements, ignoreArrayOrder);
  }

  public static <T> JsonMatcher<T> equalToJson(JsonObject value, Function<T, Object> toJson) {
    return equalToJson(value, toJson, false);
  }

  // JsonObject overloads

  public static <T> JsonMatcher<T> equalToJson(JsonObject value, Function<T, Object> toJson, boolean ignoreExtraElements) {
    return equalToJson(value, toJson, ignoreExtraElements, false);
  }

  public static <T> JsonMatcher<T> equalToJson(JsonObject value, Function<T, Object> toJson,
                                               boolean ignoreExtraElements, boolean ignoreArrayOrder) {
    return new JsonMatcher<>(value, toJson, ignoreExtraElements, ignoreArrayOrder);
  }

  public static Matcher<JsonObject> notEqualToJson(JsonObject value) {
    return notEqualToJson(value, false);
  }

  public static Matcher<JsonObject> notEqualToJson(JsonObject value, boolean ignoreExtraElements) {
    return notEqualToJson(value, ignoreExtraElements, false);
  }

  public static Matcher<JsonObject> notEqualToJson(JsonObject value, boolean ignoreExtraElements, boolean ignoreArrayOrder) {
    return o -> !equalToJson(value, ignoreExtraElements, ignoreArrayOrder).matches(o);
  }

  public static JsonMatcher<JsonArray> equalToJson(JsonArray value) {
    return equalToJson(value, false);
  }

  public static JsonMatcher<JsonArray> equalToJson(JsonArray value, boolean ignoreExtraElements) {
    return equalToJson(value, ignoreExtraElements, false);
  }

  public static JsonMatcher<JsonArray> equalToJson(JsonArray value, boolean ignoreExtraElements, boolean ignoreArrayOrder) {
    return equalToJson(value, i -> i, ignoreExtraElements, ignoreArrayOrder);
  }

  public static <T> JsonMatcher<T> equalToJson(JsonArray value, Function<T, Object> toJson) {
    return equalToJson(value, toJson, false);
  }

  // JsonArray overloads

  public static <T> JsonMatcher<T> equalToJson(JsonArray value, Function<T, Object> toJson, boolean ignoreExtraElements) {
    return equalToJson(value, toJson, ignoreExtraElements, false);
  }

  public static <T> JsonMatcher<T> equalToJson(JsonArray value, Function<T, Object> toJson,
                                               boolean ignoreExtraElements, boolean ignoreArrayOrder) {
    return new JsonMatcher<>(value, toJson, ignoreExtraElements, ignoreArrayOrder);
  }

  public static Matcher<JsonArray> notEqualToJson(JsonArray value) {
    return notEqualToJson(value, false);
  }

  public static Matcher<JsonArray> notEqualToJson(JsonArray value, boolean ignoreExtraElements) {
    return notEqualToJson(value, ignoreExtraElements, false);
  }

  public static Matcher<JsonArray> notEqualToJson(JsonArray value, boolean ignoreExtraElements, boolean ignoreArrayOrder) {
    return o -> !equalToJson(value, ignoreExtraElements, ignoreArrayOrder).matches(o);
  }

  @Override
  public boolean matches(T other) {
    if (value == null && other == null) {
      return true;
    }
    if (value == null || other == null) {
      return false;
    }
    return matchInternal(value, toJson.apply(other));
  }

  private boolean matchInternal(Object a, Object b) {
    if (a instanceof JsonObject && b instanceof JsonObject) {
      return matchInternal((JsonObject) a, (JsonObject) b);
    }
    if (a instanceof JsonArray && b instanceof JsonArray) {
      return matchInternal((JsonArray) a, (JsonArray) b);
    }
    if (a instanceof Number && b instanceof Number && a.getClass() != b.getClass()) {
      Number an = (Number) a;
      Number bn = (Number) b;
      if (a instanceof Float || a instanceof Double || b instanceof Float || b instanceof Double) {
        return an.doubleValue() == bn.doubleValue();
      } else {
        return an.longValue() == bn.longValue();
      }
    }
    return Objects.equals(a, b);
  }

  private boolean matchInternal(JsonObject a, JsonObject b) {
    if (!ignoreExtraElements && (a.size() != b.size())) {
      return false;
    }
    return a.stream().allMatch(e -> matchInternal(e.getValue(), b.getValue(e.getKey())));
  }

  private boolean matchInternal(JsonArray a, JsonArray b) {
    if ((!ignoreExtraElements && a.size() != b.size()) || (ignoreExtraElements && a.size() > b.size())) {
      return false;
    }
    if (ignoreArrayOrder) {
      return a.stream().allMatch(av -> b.stream().anyMatch(bv -> matchInternal(av, bv)));
    }
    return IntStream.range(0, a.size()).allMatch(i -> matchInternal(a.getValue(i), b.getValue(i)));
  }
}
