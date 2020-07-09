package com.noenv.wiremongo;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.streams.ReadStream;
import io.vertx.core.streams.WriteStream;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MemoryStreamTest {

  @Test
  public void paused() {
    ReadStream<String> readStream = MemoryStream.of("foo", "bar");
    readStream.pause();
    FakeWriteStream<String> dst = new FakeWriteStream<>();
    readStream.pipeTo(dst);
    assertTrue(dst.received.isEmpty());
  }

  @Test
  public void notPaused() {
    ReadStream<String> readStream = MemoryStream.of("a", "b");
    FakeWriteStream<String> dst = new FakeWriteStream<>();
    readStream.pipeTo(dst);
    assertEquals(2, dst.received.size());
  }

  private static class FakeWriteStream<T> implements WriteStream<T> {
    int maxSize;
    List<T> received = new ArrayList<>();
    Handler<Void> drainHandler;

    public FakeWriteStream<T> setWriteQueueMaxSize(int maxSize) {
      this.maxSize = maxSize;
      return this;
    }

    public boolean writeQueueFull() {
      return received.size() >= maxSize;
    }

    public FakeWriteStream<T> drainHandler(Handler<Void> handler) {
      this.drainHandler = handler;
      return this;
    }

    public FakeWriteStream<T> write(T data) {
      received.add(data);
      return this;
    }

    @Override
    public WriteStream<T> write(T data, Handler<AsyncResult<Void>> handler) {
      throw new UnsupportedOperationException();
    }

    public FakeWriteStream<T> exceptionHandler(Handler<Throwable> handler) {
      return this;
    }

    @Override
    public void end() {
    }

    @Override
    public void end(Handler<AsyncResult<Void>> handler) {
      throw new UnsupportedOperationException();
    }

  }

}
