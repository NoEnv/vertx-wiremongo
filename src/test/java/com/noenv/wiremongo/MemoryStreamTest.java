package com.noenv.wiremongo;

import io.vertx.core.Handler;
import io.vertx.core.streams.ReadStream;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MemoryStreamTest {

  @Test
  public void pausedBeforeHandlerSet() {
    ReadStream<String> readStream = MemoryStream.of("foo", "bar");
    readStream.pause();
    TestDataHandler<String> dataHandler = new TestDataHandler<>();
    readStream.handler(dataHandler);
    assertTrue(dataHandler.handledList.isEmpty());
  }

  @Test
  public void pausedAfterHandlerSet() {
    ReadStream<String> readStream = MemoryStream.of("foo", "bar");
    TestDataHandler<String> dataHandler = new TestDataHandler<>();
    readStream.handler(dataHandler);
    readStream.pause();
    assertEquals(2, dataHandler.handledList.size());
  }

  @Test
  public void resumeAfterPause() {
    ReadStream<String> readStream = MemoryStream.of("foo", "bar");
    readStream.pause();
    TestDataHandler<String> dataHandler = new TestDataHandler<>();
    readStream.handler(dataHandler);
    readStream.resume();
    assertEquals(2, dataHandler.handledList.size());
  }

  @Test
  public void notPaused() {
    ReadStream<String> readStream = MemoryStream.of("a", "b");
    TestDataHandler<String> dataHandler = new TestDataHandler<>();
    readStream.handler(dataHandler);
    assertEquals(2, dataHandler.handledList.size());
  }

  @Test
  public void fetch() {
    ReadStream<String> readStream = MemoryStream.of("a", "b", "c", "d", "e");
    TestDataHandler<String> dataHandler = new TestDataHandler<>();
    readStream.pause();
    readStream.handler(dataHandler);
    readStream.fetch(3);
    assertEquals(3, dataHandler.handledList.size());
  }

  @Test
  public void fetchMore() {
    ReadStream<String> readStream = MemoryStream.of("a", "b", "c", "d", "e");
    TestDataHandler<String> dataHandler = new TestDataHandler<>();
    readStream.pause();
    readStream.handler(dataHandler);
    readStream.fetch(42);
    assertEquals(5, dataHandler.handledList.size());
  }

  @Test
  public void fetchZero() {
    ReadStream<String> readStream = MemoryStream.of("a", "b", "c", "d", "e");
    TestDataHandler<String> dataHandler = new TestDataHandler<>();
    readStream.pause();
    readStream.handler(dataHandler);
    readStream.fetch(0);
    assertEquals(0, dataHandler.handledList.size());
  }

  @Test
  public void fetchNegative() {
    ReadStream<String> readStream = MemoryStream.of("a", "b", "c", "d", "e");
    TestDataHandler<String> dataHandler = new TestDataHandler<>();
    readStream.pause();
    readStream.handler(dataHandler);
    readStream.fetch(-42);
    assertEquals(0, dataHandler.handledList.size());
  }

  private static class TestDataHandler<T> implements Handler<T> {
    List<T> handledList = new ArrayList<>();

    @Override
    public void handle(T o) {
      handledList.add(o);
    }
  }

}
