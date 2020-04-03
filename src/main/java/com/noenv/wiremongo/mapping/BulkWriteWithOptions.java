package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.BulkOperation;
import io.vertx.ext.mongo.BulkWriteOptions;

import java.util.List;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

public class BulkWriteWithOptions extends BulkWrite {

  public static class BulkWriteWithOptionsCommand extends BulkWriteCommand {

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
    return this;
  }

  // fluent

  @Override
  public BulkWriteWithOptions priority(int priority) {
    return (BulkWriteWithOptions) super.priority(priority);
  }

  @Override
  public BulkWriteWithOptions inCollection(String collection) {
    return (BulkWriteWithOptions) super.inCollection(collection);
  }

  @Override
  public BulkWriteWithOptions inCollection(Matcher<String> collection) {
    return (BulkWriteWithOptions) super.inCollection(collection);
  }

  @Override
  public BulkWriteWithOptions withOperations(List<BulkOperation> operations) {
    return (BulkWriteWithOptions) super.withOperations(operations);
  }

  @Override
  public BulkWriteWithOptions withOperations(Matcher<List<BulkOperation>> operations) {
    return (BulkWriteWithOptions) super.withOperations(operations);
  }
}
