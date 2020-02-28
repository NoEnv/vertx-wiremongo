package com.noenv.wiremongo.matching;

import io.vertx.core.json.JsonObject;

import java.util.function.Function;

@FunctionalInterface
public interface Matcher<T> {

  boolean matches(T o);

  static <T> Matcher<T> create(JsonObject match) {
    return create(match, i -> (T)i, i -> i);
  }

  static <T> Matcher<T> create(JsonObject match, Function<Object, T> fromJson, Function<T, Object> toJson) {
    if (match == null) {
      return null;
    }
    for(String k : match.getMap().keySet()) {
      if ("equalTo".equals(k)) {
        return EqualsMatcher.equalTo(fromJson.apply(match.getValue(k)));
      } else if ("equalToJson".equals(k)) {
        return new JsonMatcher<>(match, toJson);
      }
    }
    throw new IllegalArgumentException("no matcher found!");
  }
}
