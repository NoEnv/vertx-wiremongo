package com.noenv.wiremongo.command.collection;

import com.noenv.wiremongo.command.CommandBase;

public abstract class WithCollectionCommand extends CommandBase {

  private final String collection;

  public WithCollectionCommand(String method, String collection) {
    super(method);
    this.collection = collection;
  }

  public String getCollection() {
    return collection;
  }

  @Override
  public String toString() {
    return super.toString() + ", collection: " + collection;
  }
}
