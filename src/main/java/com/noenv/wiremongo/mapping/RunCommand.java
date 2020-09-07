package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.RunCommandCommand;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

public class RunCommand extends MappingBase<JsonObject, RunCommandCommand, RunCommand> {

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
  public RunCommand returns(final JsonObject response) {
    return stub(c -> null == response ? null : response.copy());
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
    return (commandName == null || commandName.matches(c.getCommandName()))
      && (command == null || command.matches(c.getCommand()));
  }

  public RunCommand withCommand(String commandName, JsonObject command) {
    return withCommand(equalTo(commandName), equalTo(command));
  }

  public RunCommand withCommand(Matcher<String> commandName, Matcher<JsonObject> command) {
    this.commandName = commandName;
    this.command = command;
    return self();
  }
}
