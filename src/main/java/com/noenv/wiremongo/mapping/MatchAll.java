package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.command.Command;

public class MatchAll extends MappingBase<Object, Command, MatchAll> {

  public MatchAll() {
    super("matchAll");
  }

  @Override
  protected Object parseResponse(Object jsonValue) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean matches(Command c) {
    return true;
  }
}
