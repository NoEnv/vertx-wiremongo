package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.mongo.UpdateOptions;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindOneAndUpdateWithOptions extends FindOneAndUpdateBase<FindOneAndUpdateWithOptions> {

  public static class FindOneAndUpdateWithOptionsCommand extends FindOneAndUpdateBaseCommand {

    private final FindOptions findOptions;
    private final UpdateOptions updateOptions;

    public FindOneAndUpdateWithOptionsCommand(String collection, JsonObject query, JsonObject update, FindOptions findOptions, UpdateOptions updateOptions) {
      super("findOneAndUpdateWithOptions", collection, query, update);
      this.findOptions = findOptions;
      this.updateOptions = updateOptions;
    }

    @Override
    public String toString() {
      return super.toString()
        + ", findOptions: " + (findOptions != null ? findOptions.toJson().encode() : "null")
        + ", updateOptions: " + (updateOptions != null ? updateOptions.toJson().encode() : "null");
    }
  }

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
    return (findOptions == null || findOptions.matches(c.findOptions))
      && (updateOptions == null || updateOptions.matches(c.updateOptions));
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
