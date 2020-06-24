package com.noenv.wiremongo.command.index;

import com.noenv.wiremongo.command.collection.WithCollectionCommand;

public class DropIndexCommand extends WithCollectionCommand {

  private final String name;

  public DropIndexCommand(String collection, String name) {
    super("dropIndex", collection);
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return super.toString() + ", name: " + name;
  }
}
