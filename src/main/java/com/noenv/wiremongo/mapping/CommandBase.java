package com.noenv.wiremongo.mapping;

public abstract class CommandBase implements Command {

  private final String method;

  public CommandBase(String method) {
    this.method = method;
  }

  @Override
  public String method() {
    return method;
  }

  @Override
  public String toString() {
    return "method: " + method;
  }
}
