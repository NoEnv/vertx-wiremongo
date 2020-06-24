package com.noenv.wiremongo.mapping.collection;

import com.noenv.wiremongo.mapping.CommandBase;
import com.noenv.wiremongo.mapping.MappingBase;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.stream.Collectors;

public class GetCollections extends MappingBase<List<String>, GetCollections> {

  public static class GetCollectionsCommand extends CommandBase {
    public GetCollectionsCommand() {
      super("getCollections");
    }
  }

  public GetCollections() {
    super("getCollections");
  }

  public GetCollections(JsonObject json) {
    super(json);
  }

  @Override
  protected List<String> parseResponse(Object jsonValue) {
    return ((JsonArray) jsonValue).stream()
      .map(o -> (String) o)
      .collect(Collectors.toList());
  }
}
