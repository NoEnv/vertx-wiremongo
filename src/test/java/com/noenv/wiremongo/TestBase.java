package com.noenv.wiremongo;

import com.mongodb.MongoException;
import io.reactivex.rxjava3.functions.Consumer;
import io.vertx.core.Handler;
import io.vertx.core.impl.NoStackTraceThrowable;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.rxjava3.CompletableHelper;
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
    mock = new com.noenv.rxjava3.wiremongo.WireMongo(Vertx.vertx());
    mock.readFileMappings("wiremongo-mocks")
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));

  }

  public static Consumer<? super Throwable> assertNoMappingFoundError(TestContext ctx) {
    return assertNoMappingFoundError(ctx, null);
  }

  public static Consumer<? super Throwable> assertNoMappingFoundError(TestContext ctx, Async latch) {
    return throwable -> {
      ctx.assertTrue(throwable instanceof RuntimeException || throwable instanceof NoStackTraceThrowable, "throwable should be instance of RuntimeException or NoStackTraceThrowable");
      ctx.assertTrue(throwable.getMessage() != null && throwable.getMessage().startsWith("no mapping found: "), "throwable message should start with `no mapping found: `");
      System.err.println("Above `no mapping found` error is intentional");
      if (latch != null) {
        latch.countDown();
      }
    };
  }

  public static <T extends MongoException> Consumer<? super Throwable> assertMongoException(TestContext ctx, Class<T> mongoExceptionClass, Consumer<T> throwableConsumer) {
    return throwable -> {
      ctx.assertTrue(mongoExceptionClass.isInstance(throwable), String.format("throwable should be instance of %s", mongoExceptionClass.getName()));
      if (throwableConsumer != null) {
        throwableConsumer.accept(mongoExceptionClass.cast(throwable));
      }
    };
  }

  public static Consumer<? super Throwable> assertMongoException(TestContext ctx, Class<? extends MongoException> mongoExceptionClass) {
    return assertMongoException(ctx, mongoExceptionClass, throwable -> {
    });
  }

  public static Handler<Throwable> handleNoMappingFoundError(TestContext ctx) {
    return throwable -> {
      try {
        assertNoMappingFoundError(ctx).accept(throwable);
      } catch (Throwable e) {
        ctx.fail(e);
      }
    };
  }

  public static Consumer<Throwable> assertIntentionalError(TestContext ctx, String errMessage) {
    return throwable -> ctx.assertEquals(errMessage, throwable.getMessage());
  }

  public static Handler<Throwable> assertHandleIntentionalError(TestContext ctx, String errMessage, Async latch) {
    return throwable -> {
      try {
        assertIntentionalError(ctx, errMessage).accept(throwable);
      } catch (Throwable e) {
        ctx.fail(e);
      }
      latch.countDown();
    };
  }

  @Before
  public void setUp() {
    db = mock.getClient();
  }

}
