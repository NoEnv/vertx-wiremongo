package com.noenv.wiremongo.command;

import io.vertx.core.json.JsonObject;

public class RunCommandCommand extends CommandBase {

  private final String commandName;
  private final JsonObject command;

  public RunCommandCommand(String commandName, JsonObject command) {
    super("runCommand");
    this.commandName = commandName;
    this.command = command;
  }

  public String getCommandName() {
    return commandName;
  }

  public JsonObject getCommand() {
    return command;
  }

  @Override
  public String toString() {
    return super.toString() + ", commandName: " + commandName + ", command: " + command;
  }
}
