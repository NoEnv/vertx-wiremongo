package com.noenv.wiremongo.mapping.update;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.update.UpdateCollectionWithOptionsCommand;
import com.noenv.wiremongo.matching.JsonMatcher;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientUpdateResult;
import io.vertx.ext.mongo.UpdateOptions;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class UpdateCollectionWithOptions<T> extends UpdateCollectionBase<T, UpdateCollectionWithOptionsCommand<T>, UpdateCollectionWithOptions<T>> {

  private Matcher<UpdateOptions> options;

  public UpdateCollectionWithOptions() {
    super("updateCollectionWithOptions");
  }

  public UpdateCollectionWithOptions(String method) {
    super(method);
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
    return withOptions(JsonMatcher.equalToJson(options.toJson(), UpdateOptions::toJson));
  }

  public UpdateCollectionWithOptions<T> withOptions(Matcher<UpdateOptions> options) {
    this.options = options;
    return self();
  }
}
