package com.noenv.wiremongo;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.Handler;
import io.vertx.core.streams.ReadStream;

import java.util.*;
import java.util.stream.Collectors;

// ToDo: proper support toFlowable() toObservable()
public class MemoryStream<T> implements ReadStream<T> {

  private final Queue<T> items = new LinkedList<>();
  private final Throwable error;
  private Handler<Void> endHandler;
  private Handler<T> dataHandler;
  boolean flowingMode = true;

  private MemoryStream(final Collection<T> items, final Throwable error) {
    this.items.addAll(items);
    this.error = error;
  }

  public MemoryStream<T> copy(final java.util.function.Function<T, T> copyItem) {
    return new MemoryStream<>(items.stream().map(copyItem).collect(Collectors.toList()), error);
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
    this.dataHandler = handler;
    if (dataHandler != null && flowingMode) {
      while (!items.isEmpty()) {
        dataHandler.handle(items.remove());
      }
      if (endHandler != null) {
        endHandler.handle(null);
      }
    }
    return this;
  }

  @Override
  public ReadStream<T> pause() {
    flowingMode = false;
    return this;
  }

  @Override
  public ReadStream<T> resume() {
    flowingMode = true;
    fetch(Long.MAX_VALUE);
    return this;
  }

  @Override
  public ReadStream<T> fetch(long amount) {
    long counter = 0;
    while (counter < amount && !this.items.isEmpty()) {
      dataHandler.handle(this.items.remove());
      counter++;
    }
    if (this.items.isEmpty() && endHandler != null) {
      endHandler.handle(null);
    }
    return this;
  }

  @Override
  public ReadStream<T> endHandler(@Nullable Handler<Void> endHandler) {
    this.endHandler = endHandler;
    if (this.items.isEmpty()) {
      endHandler.handle(null);
    }
    return this;
  }

  public static <T> ReadStream<T> of(T... items) {
    return new MemoryStream<>(Arrays.asList(items), null);
  }

  public static <T> ReadStream<T> fromList(List<T> list) {
    return new MemoryStream<>(list, null);
  }

  public static <T> ReadStream<T> error(Throwable error) {
    return new MemoryStream<>(Collections.emptyList(), error);
  }
}
