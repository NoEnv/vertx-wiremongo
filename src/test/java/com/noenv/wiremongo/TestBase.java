package com.noenv.wiremongo;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.rxjava3.core.Vertx;
import io.vertx.rxjava3.ext.mongo.MongoClient;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public abstract class TestBase {

  protected static com.noenv.rxjava3.wiremongo.WireMongo mock;
  protected MongoClient db;

  @BeforeClass
  public static void setUp(TestContext ctx) {
    Async async = ctx.async();
    mock = new com.noenv.rxjava3.wiremongo.WireMongo(Vertx.vertx());
    mock.readFileMappings("wiremongo-mocks")
      .subscribe(async::complete, ctx::fail);

  }

  @Before
  public void setUp() {
    db = mock.getClient();
  }
}
