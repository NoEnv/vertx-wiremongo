package com.noenv.wiremongo.command.stream;

import com.noenv.wiremongo.command.collection.WithCollectionCommand;

public abstract class WithStreamCommand extends WithCollectionCommand {

  public WithStreamCommand(String method, String collection) {
    super(method, collection);
  }
}
