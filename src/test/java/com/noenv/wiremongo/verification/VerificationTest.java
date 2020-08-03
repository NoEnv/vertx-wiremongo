package com.noenv.wiremongo.verification;

import com.noenv.wiremongo.TestBase;
import io.reactivex.Completable;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.reactivex.CompletableHelper;
import io.vertx.reactivex.MaybeHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class VerificationTest extends TestBase {

  private Verifier verifier;

  @Rule
  public ExpectedException thrown= ExpectedException.none();

  @Before
  public void setUpTest(TestContext ctx) {
    mock.clear();
    verifier = new Verifier();
  }

  @After
  public void tearDownTest(TestContext ctx) {
    verifier.assertAllSucceeded();
  }

  @Test
  public void verify_RunExactlyOnce_shall_succeed_ifRunOnce(TestContext ctx) {
    mock
      .findOneAndUpdate()
      .inCollection("some-collection")
      .verify(
        verifier
          .checkIf("find one and update in some-collection")
          .isRunExactlyOnce()
      )
      .returns(null);


    db
      .rxFindOneAndUpdate(
        "some-collection",
        new JsonObject(),
        new JsonObject()
      )
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void verify_RunExactlyOnce_shall_fail_ifRunTwice(TestContext ctx) {

    thrown.expect(AssertionError.class);
    thrown.expectMessage("expected 'find one and update in some-collection' to run exactly once but ran more often");

    mock
      .findOneAndUpdate()
      .inCollection("some-collection")
      .verify(
        verifier
          .checkIf("find one and update in some-collection")
          .isRunExactlyOnce()
      )
      .returns(null);


    Completable
      .mergeArray(
        db
          .rxFindOneAndUpdate(
            "some-collection",
            new JsonObject(),
            new JsonObject()
          )
          .ignoreElement(),
        db
          .rxFindOneAndUpdate(
            "some-collection",
            new JsonObject(),
            new JsonObject()
          )
          .ignoreElement()
      )
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void verify_RunAtLeastOnce_shall_succeed_ifRunOnce(TestContext ctx) {
    mock
      .updateCollection()
      .verify(
        verifier
          .checkIf("update in any collection")
          .isRunAtLeastOnce()
      )
      .returns(null);

    db
      .rxUpdateCollection(
        "any-collection",
        new JsonObject(),
        new JsonObject()
      )
      .subscribe(MaybeHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void verify_RunAtLeastOnce_shall_succeed_ifRunTwice(TestContext ctx) {
    mock
      .updateCollection()
      .verify(
        verifier
          .checkIf("update in any collection")
          .isRunAtLeastOnce()
      )
      .returns(null);

    Completable
      .concatArray(
        db
          .rxUpdateCollection(
            "any-collection-1",
            new JsonObject(),
            new JsonObject()
          )
          .ignoreElement(),
        db
          .rxUpdateCollection(
            "any-collection-2",
            new JsonObject(),
            new JsonObject()
          )
          .ignoreElement()
      )
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  // TODO: 03.08.20 add tests for order checks

}
