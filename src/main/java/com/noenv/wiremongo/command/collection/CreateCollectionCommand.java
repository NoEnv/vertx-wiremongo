package com.noenv.wiremongo.command.collection;

public class CreateCollectionCommand extends WithCollectionCommand {

  public CreateCollectionCommand(String collection) {
    super("createCollection", collection);
  }
}
