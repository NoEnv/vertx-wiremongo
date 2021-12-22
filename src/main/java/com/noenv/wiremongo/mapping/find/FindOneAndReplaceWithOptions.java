package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.find.FindOneAndReplaceWithOptionsCommand;
import com.noenv.wiremongo.matching.JsonMatcher;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.CreateCollectionOptions;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.mongo.UpdateOptions;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindOneAndReplaceWithOptions extends FindOneAndReplaceBase<FindOneAndReplaceWithOptionsCommand, FindOneAndReplaceWithOptions> {

  private Matcher<FindOptions> findOptions;
  private Matcher<UpdateOptions> updateOptions;

  public FindOneAndReplaceWithOptions() {
    super("findOneAndReplaceWithOptions");
  }

  public FindOneAndReplaceWithOptions(JsonObject json) {
    super(json);
    this.findOptions = Matcher.create(json.getJsonObject("findOptions"), j -> new FindOptions((JsonObject) j), FindOptions::toJson);
    this.updateOptions = Matcher.create(json.getJsonObject("updateOptions"), j -> new UpdateOptions((JsonObject) j), UpdateOptions::toJson);
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof FindOneAndReplaceWithOptionsCommand)) {
      return false;
    }
    FindOneAndReplaceWithOptionsCommand c = (FindOneAndReplaceWithOptionsCommand) cmd;
    return (findOptions == null || findOptions.matches(c.getFindOptions()))
      && (updateOptions == null || updateOptions.matches(c.getUpdateOptions()));
  }

  public FindOneAndReplaceWithOptions withFindOptions(FindOptions findOptions) {
    return withFindOptions(JsonMatcher.equalToJson(findOptions.toJson(), FindOptions::toJson));
  }

  public FindOneAndReplaceWithOptions withFindOptions(Matcher<FindOptions> findOptions) {
    this.findOptions = findOptions;
    return self();
  }

  public FindOneAndReplaceWithOptions withUpdateOptions(UpdateOptions updateOptions) {
    return withUpdateOptions(JsonMatcher.equalToJson(updateOptions.toJson(), UpdateOptions::toJson));
  }

  public FindOneAndReplaceWithOptions withUpdateOptions(Matcher<UpdateOptions> updateOptions) {
    this.updateOptions = updateOptions;
    return self();
  }
}
