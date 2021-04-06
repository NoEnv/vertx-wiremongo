package com.noenv.wiremongo.matching.compare;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JsonObjectComparator implements Comparator<JsonObject> {

  private final boolean ignoreArrayOrder;

  public JsonObjectComparator() {
    this(false);
  }

  public JsonObjectComparator(boolean ignoreArrayOrder) {
    this.ignoreArrayOrder = ignoreArrayOrder;
  }

  @Override
  public int compare(JsonObject json1, JsonObject json2) {
    if (json1.size() != json2.size()) {
      return Integer.signum(json1.size() - json2.size());
    }
    List<String> json1Keys = json1.getMap().keySet().stream().sorted().collect(Collectors.toList());
    List<String> json2Keys = json2.getMap().keySet().stream().sorted().collect(Collectors.toList());
    int comparedKeys = IntStream.range(0, Math.min(json1Keys.size(), json2Keys.size()))
      .mapToObj(i -> json1Keys.get(i).compareTo(json2Keys.get(i)))
      .reduce(0, (last, current) -> last == 0 ? current : last);
    if (comparedKeys != 0) {
      return Integer.signum(comparedKeys);
    }
    for (int i = 0; i < json1Keys.size(); i++) {
      Object json1Value = json1.getValue(json1Keys.get(i));
      Object json2Value = json2.getValue(json2Keys.get(i));
      int comparedValue = -1;
      if (json2Value == null) {
        return 1;
      }
      else if (!json1Value.getClass().equals(json2Value.getClass())) {
        return Integer.signum(json1Value.getClass().getName().compareTo(json2Value.getClass().getName()));
      } else if (json1Value instanceof JsonObject) {
        comparedValue = compare((JsonObject) json1Value, (JsonObject) json2Value);
      } else if (json1Value instanceof JsonArray) {
        JsonArrayComparator arrayComparator = new JsonArrayComparator(ignoreArrayOrder);
        comparedValue = arrayComparator.compare((JsonArray) json1Value, (JsonArray) json2Value);
      } else if (json1Value instanceof Comparable) {
        comparedValue = ((Comparable) json1Value).compareTo(json2Value);
      }
      if (comparedValue != 0) {
        return comparedValue;
      }
    }
    return 0;
  }
}
