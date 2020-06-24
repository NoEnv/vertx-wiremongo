package com.noenv.wiremongo.mapping.bulkwrite;

import com.noenv.wiremongo.mapping.Command;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.BulkOperation;
import io.vertx.ext.mongo.BulkWriteOptions;

import java.util.List;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

public class BulkWriteWithOptions extends BulkWriteBase<BulkWriteWithOptions> {

  public static class BulkWriteWithOptionsCommand extends BulkWriteBaseCommand {

    private final BulkWriteOptions options;

    public BulkWriteWithOptionsCommand(String collection, List<BulkOperation> operations, BulkWriteOptions options) {
      super("bulkWriteWithOptions", collection, operations);
      this.options = options;
    }

    @Override
    public String toString() {
      return super.toString() + ", options: " + (options != null ? options.toJson().encode() : "null");
    }
  }

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
    return options == null || options.matches(c.options);
  }

  public BulkWriteWithOptions withOptions(BulkWriteOptions options) {
    return withOptions(equalTo(options));
  }

  public BulkWriteWithOptions withOptions(Matcher<BulkWriteOptions> options) {
    this.options = options;
    return self();
  }
}
