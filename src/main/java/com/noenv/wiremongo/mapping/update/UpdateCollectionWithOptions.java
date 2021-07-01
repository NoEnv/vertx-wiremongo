package com.noenv.wiremongo.mapping.update;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.update.UpdateCollectionWithOptionsCommand;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientUpdateResult;
import io.vertx.ext.mongo.UpdateOptions;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class UpdateCollectionWithOptions<T> extends UpdateCollectionBase<T, UpdateCollectionWithOptionsCommand<T>, UpdateCollectionWithOptions<T>> {

  private Matcher<UpdateOptions> options;

  public UpdateCollectionWithOptions() {
    super("updateCollectionWithOptions");
  }

  public UpdateCollectionWithOptions(JsonObject json) {
    super(json);
    options = Matcher.create(json.getJsonObject("options"), j -> new UpdateOptions((JsonObject) j), UpdateOptions::toJson);
  }

  @Override
  protected MongoClientUpdateResult parseResponse(Object jsonValue) {
    return new MongoClientUpdateResult((JsonObject) jsonValue);
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof UpdateCollectionWithOptionsCommand)) {
      return false;
    }
    UpdateCollectionWithOptionsCommand<T> c = (UpdateCollectionWithOptionsCommand<T>) cmd;
    return options == null || options.matches(c.getOptions());
  }

  public UpdateCollectionWithOptions<T> withOptions(UpdateOptions options) {
    return withOptions(equalTo(options));
  }

  public UpdateCollectionWithOptions<T> withOptions(Matcher<UpdateOptions> options) {
    this.options = options;
    return self();
  }
}
