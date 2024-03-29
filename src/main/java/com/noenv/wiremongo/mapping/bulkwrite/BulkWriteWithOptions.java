package com.noenv.wiremongo.mapping.bulkwrite;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.bulkwrite.BulkWriteWithOptionsCommand;
import com.noenv.wiremongo.matching.JsonMatcher;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.BulkWriteOptions;

public class BulkWriteWithOptions extends BulkWriteBase<BulkWriteWithOptionsCommand, BulkWriteWithOptions> {

  private Matcher<BulkWriteOptions> options;

  public BulkWriteWithOptions() {
    super("bulkWriteWithOptions");
  }

  public BulkWriteWithOptions(JsonObject json) {
    super(json);
    options = Matcher.create(json.getJsonObject("options"), j -> new BulkWriteOptions((JsonObject) j), BulkWriteOptions::toJson);
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof BulkWriteWithOptionsCommand)) {
      return false;
    }
    BulkWriteWithOptionsCommand c = (BulkWriteWithOptionsCommand) cmd;
    return options == null || options.matches(c.getOptions());
  }

  public BulkWriteWithOptions withOptions(BulkWriteOptions options) {
    return withOptions(JsonMatcher.equalToJson(options.toJson(), BulkWriteOptions::toJson));
  }

  public BulkWriteWithOptions withOptions(Matcher<BulkWriteOptions> options) {
    this.options = options;
    return self();
  }
}
