package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

public class RunCommand extends MappingBase<JsonObject> {

  public static class RunCommandCommand extends CommandBase {

    private final String commandName;
    private final JsonObject command;

    public RunCommandCommand(String commandName, JsonObject command) {
      super("runCommand");
      this.commandName = commandName;
      this.command = command;
    }

    @Override
    public String toString() {
      return super.toString() + ", commandName: " + commandName + ", command: " + command;
    }
  }

  private Matcher<String> commandName;
  private Matcher<JsonObject> command;

  public RunCommand() {
    super("runCommand");
  }

  public RunCommand(JsonObject json) {
    super(json);
    commandName = Matcher.create(json.getJsonObject("commandName"));
    command = Matcher.create(json.getJsonObject("command"));
  }

  @Override
  protected JsonObject parseResponse(Object jsonValue) {
    return (JsonObject) jsonValue;
  }

  @Override
  public boolean matches(Command cmd) {
    if (!(cmd instanceof RunCommandCommand)) {
      return false;
    }
    RunCommandCommand c = (RunCommandCommand) cmd;
    return (commandName == null || commandName.matches(c.commandName))
      && (command == null || command.matches(c.command));
  }

  public RunCommand withCommand(String commandName, JsonObject command) {
    return withCommand(equalTo(commandName), equalTo(command));
  }

  public RunCommand withCommand(Matcher<String> commandName, Matcher<JsonObject> command) {
    this.commandName = commandName;
    this.command = command;
    return this;
  }
}
