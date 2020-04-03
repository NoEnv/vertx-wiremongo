package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.Stub;
import io.vertx.core.json.JsonObject;

import java.util.LinkedList;
import java.util.Objects;

public abstract class MappingBase<T> extends CommandBase implements Mapping<T> {

  private static final Stub DUMMY_STUB = () -> null;
  private LinkedList<Stub<T>> stubs = new LinkedList<>();
  private int priority;

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
  public Mapping<T> priority(int priority) {
    this.priority = priority;
    return this;
  }

  @Override
  public Stub<T> stub() {
    if (stubs.size() > 1) {
      return stubs.pop();
    }
    return stubs.peek();
  }

  @Override
  public MappingBase<T> stub(Stub<T> stub) {
    if (stubs.contains(DUMMY_STUB)) {
      stubs.remove(DUMMY_STUB);
    }
    stubs.add(stub);
    return this;
  }

  @Override
  public boolean matches(Command c) {
    return Objects.equals(method(), c.method());
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
}
