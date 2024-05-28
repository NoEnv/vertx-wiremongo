package com.noenv.wiremongo.command.collection;

public class RenameCollectionCommand extends WithCollectionCommand {
  private final String oldCollectionName;

  public RenameCollectionCommand(String oldCollectionName, String newCollectionName) {
    this("renameCollection", oldCollectionName, newCollectionName);
  }

  public RenameCollectionCommand(String method, String oldCollectionName, String newCollectionName) {
    super(method, newCollectionName);
    this.oldCollectionName = oldCollectionName;
  }

  public String getOldCollectionName() {
    return oldCollectionName;
  }

  @Override
  public String toString() {
    return super.toString() + ", oldCollectionName: " + oldCollectionName;
  }
}
