package com.noenv.wiremongo.verification;

import java.util.List;

public class Verification {
  private final String label;
  private final List<Verification> previousVerifications;
  private Runnable check;
  private boolean checkOrder;
  private boolean checked;

  Verification(String label, List<Verification> previousVerifications) {
    this.label = label;
    this.previousVerifications = previousVerifications;
  }

  public Verification isRunExactlyOnce() {
    this.check = () -> {
      if (checked) {
        throw new AssertionError(String.format(
          "expected '%s' to run exactly once but ran more often",
          label
        ));
      }

      checkOrder();
    };

    return this;
  }

  public Verification isRunAtLeastOnce() {
    this.check = this::checkOrder;
    return this;
  }

  private void checkOrder() {
    this.checked = true;

    if (checkOrder) {
      previousVerifications
        .stream()
        .filter(verification -> !verification.isChecked())
        .findFirst()
        .ifPresent(verification -> {
          throw new AssertionError(String.format(
            "'%s' expected all previous verifications to be already checked, but '%s' was not",
            this.label,
            verification.label
          ));
        })
      ;
    }
  }

  private boolean isChecked() {
    return this.checked;
  }

  public void assertSucceed() {
    if (!checked) {
      throw new AssertionError(String.format("expected '%s' to be already checked, but it was not", label));
    }
  }

  public Verification isRunAfterPreviousVerifications() {
    this.checkOrder = true;
    return this;
  }

  public void runCheck() {
    this.check.run();
  }

}
