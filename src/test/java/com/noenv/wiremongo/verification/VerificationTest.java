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
import org.junit.runners.model.MultipleFailureException;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(VertxUnitRunner.class)
public class VerificationTest extends TestBase {

  private Verifier verifier;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUpTest() {
    mock.clear();
    verifier = new Verifier();
  }

  @After
  public void tearDownTest() {
    verifier.assertAllSucceeded();
  }

  @Test
  public void test_shall_fail_ifDefinedVerificationIsNotRun() {

    thrown.expect(AssertionError.class);
    thrown.expectMessage("expected 'find one and update in some-collection' to be checked, but it was not");

    mock
      .findOneAndUpdate()
      .inCollection("some-collection")
      .verify(
        verifier
          .checkIf("find one and update in some-collection")
          .isRunExactlyOnce()
      )
      .returns(null);
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
  public void verify_RunExactlyTwice_shall_succeed_ifRunTwice(TestContext ctx) {
    mock
      .findOneAndUpdate()
      .inCollection("some-collection")
      .verify(
        verifier
          .checkIf("find one and update in some-collection")
          .isRunExactly(2)
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
  public void verify_RunExactlyOnce_shall_fail_ifRunTwice(TestContext ctx) {

    thrown.expect(AssertionError.class);
    thrown.expectMessage("expected 'find one and update in some-collection' to run exactly 1 time(s), but it ran more often");

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
  public void verify_RunExactly3Times_shall_fail_ifRun4Times(TestContext ctx) {

    thrown.expect(AssertionError.class);
    thrown.expectMessage("expected 'find one and update in some-collection' to run exactly 3 time(s), but it ran more often");

    mock
      .findOneAndUpdate()
      .inCollection("some-collection")
      .verify(
        verifier
          .checkIf("find one and update in some-collection")
          .isRunExactly(3)
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
          .ignoreElement(),
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
  public void verify_RunAtLeastTwice_shall_succeed_ifRun3Times(TestContext ctx) {

    mock
      .findOneAndUpdate()
      .inCollection("some-collection")
      .verify(
        verifier
          .checkIf("find one and update in some-collection")
          .isRunAtLeast(2)
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

  @Test
  public void verify_RunAtLeastTwice_shall_fail_ifRunOnce(TestContext ctx) {

    thrown.expect(AssertionError.class);
    thrown.expectMessage("expected 'find one and update in some-collection' to run at least 2 time(s), but it ran less often");

    mock
      .findOneAndUpdate()
      .inCollection("some-collection")
      .verify(
        verifier
          .checkIf("find one and update in some-collection")
          .isRunAtLeast(2)
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
          .ignoreElement()
      )
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void verifyOrder_shall_succeed_ifRunInExpectedOrder(TestContext ctx) {
    mock
      .updateCollection()
      .inCollection("collection-1")
      .verify(
        verifier
          .checkIf("update in collection-1")
          .isRunAfterPreviousVerifications()
      )
      .returns(null);

    mock
      .updateCollection()
      .inCollection("collection-2")
      .verify(
        verifier
          .checkIf("update in collection-2")
          .isRunAfterPreviousVerifications()
      )
      .returns(null);

    Completable
      .concatArray(
        db
          .rxUpdateCollection(
            "collection-1",
            new JsonObject(),
            new JsonObject()
          )
          .ignoreElement(),
        db
          .rxUpdateCollection(
            "collection-2",
            new JsonObject(),
            new JsonObject()
          )
          .ignoreElement()
      )
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void verifyOrder_shall_fail_ifNotRunInExpectedOrder(TestContext ctx) {

    thrown.expect(AssertionError.class);
    thrown.expectMessage("expected 'update in collection-1' to be run before 'update in collection-2', but it was not");

    mock
      .updateCollection()
      .inCollection("collection-1")
      .verify(
        verifier
          .checkIf("update in collection-1")
          .isRunAfterPreviousVerifications()
      )
      .returns(null);

    mock
      .updateCollection()
      .inCollection("collection-2")
      .verify(
        verifier
          .checkIf("update in collection-2")
          .isRunAfterPreviousVerifications()
      )
      .returns(null);

    Completable
      .concatArray(
        db
          .rxUpdateCollection(
            "collection-2",
            new JsonObject(),
            new JsonObject()
          )
          .ignoreElement(),
        db
          .rxUpdateCollection(
            "collection-1",
            new JsonObject(),
            new JsonObject()
          )
          .ignoreElement()
      )
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

  @Test
  public void verify_RunAtMostOnce_shall_succeed_ifNeverRun() {
    mock
      .updateCollection()
      .verify(
        verifier
          .checkIf("update in any collection")
          .isRunAtMost(1)
      )
      .returns(null);
  }

  @Test
  public void verify_RunAtMostOnce_shall_succeed_ifRanOnce(TestContext ctx) {
    mock
      .updateCollection()
      .verify(
        verifier
          .checkIf("update in any collection")
          .isRunAtMost(1)
      )
      .returns(null);

    Completable
      .concatArray(
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

  @Test
  public void verify_RunAtMostTwice_shall_fail_ifRan3Times(TestContext ctx) {

    thrown.expect(AssertionError.class);
    thrown.expectMessage("expected 'update in any collection' to run at most 2 time(s), but it ran more often");

    mock
      .updateCollection()
      .verify(
        verifier
          .checkIf("update in any collection")
          .isRunAtMost(2)
      )
      .returns(null);

    Completable
      .concatArray(
        db
          .rxUpdateCollection(
            "any-collection-2",
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

  @Test
  public void verify_NeverRun_shall_fail_ifRun(TestContext ctx) {

    thrown.expect(AssertionError.class);
    thrown.expectMessage("expected 'find one and update in some-collection' to run at most 0 time(s), but it ran more often");

    mock
      .findOneAndUpdate()
      .inCollection("some-collection")
      .verify(
        verifier
          .checkIf("find one and update in some-collection")
          .isNeverRun()
      )
      .returns(null);


    db
      .rxFindOneAndUpdate(
        "some-collection",
        new JsonObject(),
        new JsonObject()
      )
      .ignoreElement()
      .subscribe(CompletableHelper.toObserver(ctx.asyncAssertSuccess()));
  }

}
