package com.noenv.wiremongo.matching;

import io.vertx.ext.mongo.BulkWriteOptions;
import io.vertx.ext.mongo.IndexOptions;

import java.util.Objects;

public class EqualsMatcher<T> implements Matcher<T> {

  private final T value;

  private EqualsMatcher(T value) {
    this.value = value;
  }

  @Override
  public boolean matches(T other) {

    //TODO: remove with vertx 4.0 (https://github.com/vert-x3/vertx-mongo-client/pull/168)
    if (other instanceof IndexOptions && value instanceof IndexOptions) {
      return Objects.equals(((IndexOptions)value).toJson(), ((IndexOptions)other).toJson());
    }

    //TODO: remove with vertx 4.0 (https://github.com/vert-x3/vertx-mongo-client/pull/169)
    if (other instanceof BulkWriteOptions && value instanceof BulkWriteOptions) {
      return Objects.equals(((BulkWriteOptions)value).toJson(), ((BulkWriteOptions)other).toJson());
    }

    return Objects.equals(value, other);
  }

  public static <T> EqualsMatcher<T> equalTo(T value) {
    return new EqualsMatcher<>(value);
  }

  public static <T> Matcher<T> notEqualTo(T value) {
    return o -> !equalTo(value).matches(o);
  }
}
