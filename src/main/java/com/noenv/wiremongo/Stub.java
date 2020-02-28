package com.noenv.wiremongo;

@FunctionalInterface
public interface Stub<T> {

  @SuppressWarnings("squid:S00112")
  T get() throws Throwable;
}
