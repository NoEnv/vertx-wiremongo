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
    isRunAtLeastOnce();
  }

  public Verification isRunExactlyOnce() {
    this.check = () -> {
      if (checked) {
        throw new AssertionError(String.format(
          "expected '%s' to run exactly once, but it ran more often",
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
        .filter(verification -> !verification.isChecked())
        .findFirst()
        .ifPresent(verification -> {
          throw new AssertionError(String.format(
            "expected '%s' to be run before '%s', but it was not",
            verification.label,
            this.label
          ));
        })
      ;
    }
  }

  private boolean isChecked() {
    return this.checked;
  }

  void assertSucceed() {
    if (!checked) {
      throw new AssertionError(String.format(
        "expected '%s' to be checked, but it was not",
        label
      ));
    }
  }

  public void runCheck() {
    this.check.run();
  }

}
