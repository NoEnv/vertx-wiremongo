package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.StubBase;
import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.command.CommandBase;
import com.noenv.wiremongo.verification.Verification;
import io.vertx.core.json.JsonObject;

import java.util.LinkedList;
import java.util.Objects;

public abstract class MappingBase<T, U extends Command, C extends MappingBase<T, U, C>> extends CommandBase implements Mapping<T, U, C> {

  private static final StubBase DUMMY_STUB = x -> null;
  private final LinkedList<StubBase<T, U>> stubs = new LinkedList<>();
  private int priority;
  private int times;
  private int matchCount;
  private Verification verification;

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
  public T invoke(U command) throws Throwable {
    StubBase<T, U> s = stubs.size() > 1 ? stubs.pop() : stubs.peek();
    if (verification != null) {
      verification.runCheck();
    }
    return s.invoke(command);
  }

  @Override
  public C stub(StubBase<T, U> stub) {
    stubs.remove(DUMMY_STUB);
    stubs.add(stub);
    return self();
  }

  @Override
  public boolean matches(Command c) {
    boolean matchesResult = Objects.equals(method(), c.method());
    return (times <= 0 || !matchesResult || ++matchCount <= times) && matchesResult;
  }

  @Override
  public C verify(Verification verification) {
    this.verification = verification;
    return self();
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
