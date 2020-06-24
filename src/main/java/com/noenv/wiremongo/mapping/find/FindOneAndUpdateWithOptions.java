package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.find.FindOneAndUpdateWithOptionsCommand;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.mongo.UpdateOptions;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindOneAndUpdateWithOptions extends FindOneAndUpdateBase<FindOneAndUpdateWithOptionsCommand, FindOneAndUpdateWithOptions> {

  private Matcher<FindOptions> findOptions;
  private Matcher<UpdateOptions> updateOptions;

  public FindOneAndUpdateWithOptions() {
    super("findOneAndUpdateWithOptions");
  }

  public FindOneAndUpdateWithOptions(JsonObject json) {
    super(json);
    findOptions = Matcher.create(json.getJsonObject("findOptions"), j -> new FindOptions((JsonObject) j), FindOptions::toJson);
    updateOptions = Matcher.create(json.getJsonObject("updateOptions"), j -> new UpdateOptions((JsonObject) j), UpdateOptions::toJson);
  }

  @Override
  protected JsonObject parseResponse(Object jsonValue) {
    return (JsonObject) jsonValue;
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof FindOneAndUpdateWithOptionsCommand)) {
      return false;
    }
    FindOneAndUpdateWithOptionsCommand c = (FindOneAndUpdateWithOptionsCommand) cmd;
    return (findOptions == null || findOptions.matches(c.getFindOptions()))
      && (updateOptions == null || updateOptions.matches(c.getUpdateOptions()));
  }

  public FindOneAndUpdateWithOptions withFindOptions(FindOptions options) {
    return withFindOptions(equalTo(options));
  }

  public FindOneAndUpdateWithOptions withFindOptions(Matcher<FindOptions> options) {
    this.findOptions = options;
    return self();
  }

  public FindOneAndUpdateWithOptions withUpdateOptions(UpdateOptions options) {
    return withUpdateOptions(equalTo(options));
  }

  public FindOneAndUpdateWithOptions withUpdateOptions(Matcher<UpdateOptions> options) {
    this.updateOptions = options;
    return self();
  }
}
