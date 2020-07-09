package com.noenv.wiremongo.command.collection;

public class DropCollectionCommand extends WithCollectionCommand {
  public DropCollectionCommand(String collection) {
    super("dropCollection", collection);
  }
}
