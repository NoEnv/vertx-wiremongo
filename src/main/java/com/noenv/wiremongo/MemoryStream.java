package com.noenv.wiremongo;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.Handler;
import io.vertx.core.streams.ReadStream;

import java.util.*;

public class MemoryStream<T> implements ReadStream<T> {

  private final Queue<T> items = new LinkedList<>();
  private Throwable error;
  private Handler<Void> endHandler;
  boolean done;

  private MemoryStream(Collection<T> items) {
    this.items.addAll(items);
  }

  private MemoryStream(Throwable error) {
    this.error = error;
  }

  @Override
  public ReadStream<T> exceptionHandler(Handler<Throwable> handler) {
    if (error != null) {
      handler.handle(error);
    }
    return this;
  }

  @Override
  public ReadStream<T> handler(@Nullable Handler<T> handler) {
    if (handler != null) {
      while (!items.isEmpty()) {
        handler.handle(items.remove());
      }
      done = true;
      if (endHandler != null) {
        endHandler.handle(null);
      }
    }
    return this;
  }

  @Override
  public ReadStream<T> pause() {
    return this;
  }

  @Override
  public ReadStream<T> resume() {
    return this;
  }

  @Override
  public ReadStream<T> fetch(long amount) {
    return this;
  }

  @Override
  public ReadStream<T> endHandler(@Nullable Handler<Void> endHandler) {
    this.endHandler = endHandler;
    if (done) {
      endHandler.handle(null);
    }
    return this;
  }

  public static <T> ReadStream<T> of(T... items) {
    return new MemoryStream<>(Arrays.asList(items));
  }

  public static <T> ReadStream<T> fromList(List<T> list) {
    return new MemoryStream<>(list);
  }

  public static <T> ReadStream<T> error(Throwable error) {
    return new MemoryStream<>(error);
  }
}
