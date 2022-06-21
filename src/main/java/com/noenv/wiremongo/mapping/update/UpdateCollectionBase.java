package com.noenv.wiremongo.mapping.update;

import com.noenv.wiremongo.command.update.UpdateCollectionBaseCommand;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClientUpdateResult;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public abstract class UpdateCollectionBase<V, U extends UpdateCollectionBaseCommand<V>, C extends UpdateCollectionBase<V, U, C>> extends WithUpdate<V, MongoClientUpdateResult, U, C> {

  public UpdateCollectionBase() {
    this("updateCollection");
  }

  public UpdateCollectionBase(String method) {
    super(method);
  }

  public UpdateCollectionBase(JsonObject json) {
    super(json);
  }

  @Override
  public C returns(final MongoClientUpdateResult response) {
    return stub(c -> null == response ? null : new MongoClientUpdateResult(response.toJson().copy()));
  }

  @Override
  protected MongoClientUpdateResult parseResponse(Object jsonValue) {
    return new MongoClientUpdateResult((JsonObject) jsonValue);
  }

  public C returns(long docMatched, JsonObject docUpsertedId, long docModified) {
    return returns(new MongoClientUpdateResult(docMatched, docUpsertedId, docModified));
  }
}
