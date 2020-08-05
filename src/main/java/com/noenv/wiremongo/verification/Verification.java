package com.noenv.wiremongo.verification;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Verification {
  private final String label;
  private final List<Verification> previousVerifications;
  private Runnable execution;
  private Runnable assertion;
  private boolean checkOrder;
  private boolean checked;
  private boolean failed;
  private String failure;

  Verification(String label, List<Verification> previousVerifications) {
    this.label = label;
    this.previousVerifications = previousVerifications;
    isRunAtLeastOnce();
  }

  public Verification isRunExactlyOnce() {
    return isRunExactly(1);
  }

  public Verification isRunExactly(int times) {
    AtomicInteger actualRuns = new AtomicInteger();

    this.execution = () -> {
      actualRuns.incrementAndGet();
      checkOrder();
    };

    this.assertion = () -> {
      if (actualRuns.get() > times) {
        this.failed = true;
        this.failure = String.format(
          "expected '%s' to run exactly %d time(s), but it ran more often",
          label,
          times
        );
      }
    };

    return this;
  }

  public Verification isRunAtLeastOnce() {
    return isRunAtLeast(1);
  }

  public Verification isRunAtLeast(int times) {
    AtomicInteger actualRuns = new AtomicInteger();

    this.execution = () -> {
      actualRuns.incrementAndGet();
      checkOrder();
    };

    this.assertion = () -> {
      if (actualRuns.get() < times) {
        this.failed = true;
        this.failure = String.format(
          "expected '%s' to run at least %d time(s), but it ran less often",
          label,
          times
        );
      }
    };

    return this;
  }

  public Verification isNeverRun() {
    return isRunAtMost(0);
  }

  public Verification isRunAtMost(int times) {
    AtomicInteger actualRuns = new AtomicInteger();
    // at most always means possibly never
    this.checked = true;

    this.execution = () -> {
      actualRuns.incrementAndGet();
      checkOrder();
    };

    this.assertion = () -> {
      if (actualRuns.get() > times) {
        this.failed = true;
        this.failure = String.format(
          "expected '%s' to run at most %d time(s), but it ran more often",
          label,
          times
        );
      }
    };

    return this;
  }

  /**
   * Makes sure that the verification is run after others created previously using same {@link Verifier}.
   */
  public Verification isRunAfterPreviousVerifications() {
    this.checkOrder = true;
    return this;
  }

  private void checkOrder() {
    this.checked = true;

    if (checkOrder) {
      previousVerifications
        .stream()
        .filter(verification -> !verification.checked)
        .findFirst()
        .ifPresent(verification -> {
          this.failed = true;
          this.failure = String.format(
            "expected '%s' to be run before '%s', but it was not",
            verification.label,
            this.label
          );
        })
      ;
    }
  }

  void assertSucceed() {
    if (this.assertion != null) {
      this.assertion.run();
    }

    if (!checked) {
      throw new AssertionError(String.format(
        "expected '%s' to be checked, but it was not",
        label
      ));

    } else if (failed) {
      throw new AssertionError(this.failure);
    }
  }

  public void execute() {
    this.execution.run();
  }

}
