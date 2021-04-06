package com.noenv.wiremongo.matching.compare;

import io.vertx.core.json.JsonArray;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class JsonArrayComparator implements Comparator<JsonArray> {

  private final boolean ignoreArrayOrder;

  public JsonArrayComparator() {
    this(false);
  }

  public JsonArrayComparator(boolean ignoreArrayOrder) {
    this.ignoreArrayOrder = ignoreArrayOrder;
  }

  @Override
  public int compare(JsonArray array1, JsonArray array2) {
    List<Object> a1;
    List<Object> a2;
    if(array1.size() != array2.size()) {
      return Integer.signum(array1.size() - array2.size());
    }
    if(ignoreArrayOrder) {
      a1 = array1.stream().sorted(new GenericComparator(true)).collect(Collectors.toList());
      a2 = array2.stream().sorted(new GenericComparator(true)).collect(Collectors.toList());
    } else {
      a1 = array1.getList();
      a2 = array2.getList();
    }

    for(int i = 0; i < a1.size(); i++) {
      Object e1 = a1.get(i);
      Object e2 = a2.get(i);
      if (e1 == null && e2 != null) {
        return -1;
      } else if (e1 != null && e2 == null) {
        return 1;
      } else if (e1 == null) {
        continue;
      }
      if (e1.getClass().equals(e2.getClass())) {
        int compareResult;
        if(e1 instanceof Comparable) {
          compareResult = ((Comparable) e1).compareTo(e2);
        } else {
          compareResult = new GenericComparator(ignoreArrayOrder).compare(e1, e2);
        }
        if (compareResult != 0) {
          return compareResult;
        }
      } else {
        return e1.getClass().getName().compareTo(e2.getClass().getName());
      }
    }
    return 0;
  }
}
