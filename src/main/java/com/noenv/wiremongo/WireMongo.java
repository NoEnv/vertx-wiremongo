package com.noenv.wiremongo;

import com.noenv.wiremongo.command.Command;
import com.noenv.wiremongo.mapping.Mapping;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.streams.ReadStream;

import java.util.*;

public class WireMongo implements WireMongoCommands {

  private static final Logger logger = LoggerFactory.getLogger(WireMongo.class);

  private Vertx vertx;
  private final Map<Mapping<?, ? extends Command, ?>, Integer> mappings = Collections.synchronizedMap(new LinkedHashMap<>());
  private final WireMongoClient client;
  private int priorityCounter = 1;

  public WireMongo() {
    this(null);
  }

  public WireMongo(Vertx vertx) {
    this.vertx = vertx;
    this.client = WireMongoClient.createShared(vertx, null, "").setWireMongo(this);
  }

  public WireMongoClient getClient() {
    return client;
  }

  public Future<Void> readFileMappings() {
    return readFileMappings("wiremongo");
  }

  public Future<Void> readFileMappings(String path) {
    Promise<List<String>> promise = Promise.promise();
    if (vertx == null) {
      return Future.failedFuture("to read file mappings, initialize WireMongo with a vertx instance");
    }
    vertx.fileSystem().readDir(path, promise);
    return promise.future()
      .compose(l -> l.stream()
        .map(p -> p.toLowerCase().endsWith(".json") ? readMappingFromFile(p) : readFileMappings(p))
        .reduce(Future.succeededFuture(), (a, b) -> a.compose(x -> b)))
      .mapEmpty();
  }

  private Future<Void> readMappingFromFile(String file) {
    Promise<Buffer> promise = Promise.promise();
    vertx.fileSystem().readFile(file, promise);
    return promise.future()
      .map(Buffer::toJsonObject)
      .map(Mapping::create)
      .map(this::addMapping)
      .mapEmpty();
  }

  @Override
  public <T extends Mapping<?, ?, ?>> T addMapping(T mapping) {
    if (mapping.priority() == 0) {
      mapping.priority(priorityCounter++);
    }
    mappings.put(mapping, 0);
    return mapping;
  }

  @Override
  public <T extends Mapping<?, ?, ?>> boolean removeMapping(T mapping) {
    return mappings.remove(mapping) != null;
  }

  <T, U extends Command> Future<T> call(U request) {
    logger.debug("wiremongo received request: " + request.toString());
    Mapping<T, U, ?> mapping = this.findMapping(request);
    if (mapping == null) {
      return Future.failedFuture("no mapping found: " + request);
    }
    try {
      return Future.succeededFuture(mapping.invoke(request));
    } catch (Throwable ex) {
      return Future.failedFuture(ex);
    }
  }

  <U extends Command> ReadStream<JsonObject> callStream(U request) {
    Mapping<ReadStream<JsonObject>, U, ?> mapping = this.findMapping(request);
    if (mapping == null) {
      return MemoryStream.error(new IllegalArgumentException("no mapping found: " + request));
    }
    try {
      return mapping.invoke(request);
    } catch (Throwable ex) {
      return MemoryStream.error(ex);
    }
  }

  private <T, U extends Command> Mapping<T, U, ?> findMapping(U request) {
    synchronized (mappings) {
      //noinspection unchecked
      return (Mapping<T, U, ?>) mappings.keySet().stream()
        .filter(m -> {
          try {
            if (m.matches(request)) {
              int matchCount = mappings.get(m);
              mappings.put(m, ++matchCount);
              return m.validFor() <= 0 || matchCount <= m.validFor();
            }
            return false;
          } catch (Throwable ex) {
            logger.error("error evaluating mapping", ex);
            return false;
          }
        })
        .max(Comparator.comparingInt(Mapping::priority))
        .orElseGet(() -> {
          logger.info("no mapping found (" + request.toString() + ")");
          return null;
        });
    }
  }

  public WireMongo clear() {
    mappings.clear();
    return this;
  }
}
