package com.noenv.wiremongo;

/***
 * @deprecated use StubBase instead
 * @param <T>
 */
@Deprecated
@FunctionalInterface
public interface Stub<T> {

  @SuppressWarnings("squid:S00112")
  T invoke() throws Throwable;
}
