package com.noenv.wiremongo;

import com.noenv.wiremongo.command.Command;

@FunctionalInterface
public interface StubBase<T, U extends Command> {

  @SuppressWarnings("squid:S00112")
  T invoke(U command) throws Throwable;
}
