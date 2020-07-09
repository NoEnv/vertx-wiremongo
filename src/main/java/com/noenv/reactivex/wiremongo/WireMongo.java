package com.noenv.reactivex.wiremongo;

import com.noenv.wiremongo.WireMongoCommands;
import com.noenv.wiremongo.mapping.Mapping;
import io.reactivex.Completable;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.impl.AsyncResultCompletable;

public class WireMongo implements WireMongoCommands {

  private final com.noenv.wiremongo.WireMongo delegate;

  public WireMongo() {
    this(new com.noenv.wiremongo.WireMongo());
  }

  public WireMongo(Vertx vertx) {
    this(new com.noenv.wiremongo.WireMongo(vertx.getDelegate()));
  }

  public WireMongo(com.noenv.wiremongo.WireMongo delegate) {
    this.delegate = delegate;
  }

  public com.noenv.wiremongo.WireMongo getDelegate() {
    return delegate;
  }

  public WireMongoClient getClient() {
    return new WireMongoClient(delegate.getClient());
  }

  public Completable readFileMappings() {
    return new AsyncResultCompletable(f -> delegate.readFileMappings().onComplete(f));
  }

  public Completable readFileMappings(String path) {
    return new AsyncResultCompletable(f -> delegate.readFileMappings(path).onComplete(f));
  }

  public WireMongo clear() {
    delegate.clear();
    return this;
  }

  @Override
  public <T extends Mapping<?, ?, ?>> T addMapping(T mapping) {
    return delegate.addMapping(mapping);
  }

  @Override
  public <T extends Mapping<?, ?, ?>> boolean removeMapping(T mapping) {
    return delegate.removeMapping(mapping);
  }
}
