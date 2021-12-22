package com.noenv.wiremongo.mapping.index;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.index.CreateIndexesCommand;
import com.noenv.wiremongo.mapping.collection.WithCollection;
import com.noenv.wiremongo.matching.JsonMatcher;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.IndexModel;

import java.util.List;
import java.util.stream.Collectors;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

public class CreateIndexes extends WithCollection<Void, CreateIndexesCommand, CreateIndexes> {

  private Matcher<List<IndexModel>> indexModels;

  public CreateIndexes() {
    this("createIndexes");
  }

  public CreateIndexes(String method) {
    super(method);
  }

  public CreateIndexes(JsonObject json) {
    super(json);
    indexModels = Matcher.create(json.getJsonObject("indexModels"),
      j -> ((JsonArray) j).stream().map(v -> new IndexModel((JsonObject) v)).collect(Collectors.toList()),
      l -> new JsonArray(l.stream().map(IndexModel::toJson).collect(Collectors.toList())));
  }

  @Override
  protected Void parseResponse(Object jsonValue) {
    return null;
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof CreateIndexesCommand)) {
      return false;
    }
    CreateIndexesCommand c = (CreateIndexesCommand) cmd;
    return indexModels == null || indexModels.matches(c.getIndexModels());
  }

  public CreateIndexes withIndexModels(List<IndexModel> indexModels) {
    JsonArray array = new JsonArray(indexModels.stream().map(IndexModel::toJson).collect(Collectors.toList()));
    return withIndexModels(JsonMatcher.equalToJson(array,l -> new JsonArray(l.stream().map(IndexModel::toJson).collect(Collectors.toList()))));
  }

  public CreateIndexes withIndexModels(Matcher<List<IndexModel>> indexModels) {
    this.indexModels = indexModels;
    return self();
  }
}
