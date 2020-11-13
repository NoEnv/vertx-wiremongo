package com.noenv.wiremongo.matching;

import java.util.Objects;

public class EqualsMatcher<T> implements Matcher<T> {

  private final T value;

  private EqualsMatcher(T value) {
    this.value = value;
  }

  @Override
  public boolean matches(T other) {
    return Objects.equals(value, other);
  }

  public static <T> EqualsMatcher<T> equalTo(T value) {
    return new EqualsMatcher<>(value);
  }

  public static <T> Matcher<T> notEqualTo(T value) {
    return o -> !equalTo(value).matches(o);
  }
}
