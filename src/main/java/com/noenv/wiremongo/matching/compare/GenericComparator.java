package com.noenv.wiremongo.matching.compare;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.Comparator;

public class GenericComparator implements Comparator<Object> {

  private final boolean ignoreArrayOrder;

  public GenericComparator() {
    this(false);
  }

  public GenericComparator(boolean ignoreArrayOrder) {
    this.ignoreArrayOrder = ignoreArrayOrder;
  }

  @Override
  public int compare(Object o1, Object o2) {
    if (o1 == null && o2 == null) {
      return 0;
    } else if(o1 == null) {
      return -1;
    }else if (o2 == null) {
      return 1;
    }
    if(!o1.getClass().equals(o2.getClass())) {
      return Integer.signum(o1.getClass().getName().compareTo(o2.getClass().getName()));
    } else if (o1 instanceof Comparable) {
      return ((Comparable) o1).compareTo(o2);
    } else if (o1 instanceof JsonObject) {
      return new JsonObjectComparator(this.ignoreArrayOrder).compare(((JsonObject) o1), ((JsonObject) o2));
    } else if (o1 instanceof JsonArray) {
      return new JsonArrayComparator(this.ignoreArrayOrder).compare(((JsonArray) o1), ((JsonArray) o2));
    } else {
      throw new IllegalArgumentException("Unable to compare");
    }
  }
}
