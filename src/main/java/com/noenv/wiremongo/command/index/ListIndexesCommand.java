package com.noenv.wiremongo.command.index;

import com.noenv.wiremongo.command.collection.WithCollectionCommand;

public class ListIndexesCommand extends WithCollectionCommand {

  public ListIndexesCommand(String collection) {
    super("listIndexes", collection);
  }
}
