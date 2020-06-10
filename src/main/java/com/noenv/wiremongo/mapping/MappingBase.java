package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.Stub;
import io.vertx.core.json.JsonObject;

import java.util.LinkedList;
import java.util.Objects;

public abstract class MappingBase<T, C extends MappingBase<T, C>> extends CommandBase implements Mapping<T, C> {

  private static final Stub DUMMY_STUB = () -> null;
  private LinkedList<Stub<T>> stubs = new LinkedList<>();
  private int priority;
  private int times;
  private int matchCount;

  public MappingBase(String method) {
    super(method);
    stubs.add(DUMMY_STUB);
  }

  public MappingBase(JsonObject json) {
    super(json.getString("method"));
    priority = json.getInteger("priority", 0);
    parseStub(json);
  }

  @Override
  public int priority() {
    return priority;
  }

  @Override
  public C priority(int priority) {
    this.priority = priority;
    return self();
  }

  @Override
  public C validFor(int times) {
    this.times = times;
    return self();
  }

  @Override
  public Stub<T> stub() {
    if (stubs.size() > 1) {
      return stubs.pop();
    }
    return stubs.peek();
  }

  @Override
  public C stub(Stub<T> stub) {
    if (stubs.contains(DUMMY_STUB)) {
      stubs.remove(DUMMY_STUB);
    }
    stubs.add(stub);
    return self();
  }

  @Override
  public boolean matches(Command c) {
    boolean matchesResult = Objects.equals(method(), c.method());
    return (times <= 0 || !matchesResult || ++matchCount <= times) && matchesResult;
  }

  private void parseStub(JsonObject json) {
    if (json.containsKey("response")) {
      returns(parseResponse(json.getValue("response")));
    } else if (json.containsKey("error")) {
      returnsError(new Exception(json.getJsonObject("error").getString("message")));
    } else {
      throw new IllegalArgumentException("either response or error must be set!");
    }
  }

  protected abstract T parseResponse(Object jsonValue);

  @SuppressWarnings("unchecked")
  protected C self() {
    return (C) this;
  }
}
