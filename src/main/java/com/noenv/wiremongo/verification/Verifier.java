package com.noenv.wiremongo.verification;

import java.util.ArrayList;
import java.util.List;

/**
 * In best case, there should only exist a single instance of this class.
 *
 * Verifications shall only test their relative order if created by same {@link Verifier}.
 */
public class Verifier {

  private final List<Verification> verifications = new ArrayList<>();

  public Verification checkIf(String label) {
    Verification verification = new Verification(label, new ArrayList<>(this.verifications));
    this.verifications.add(verification);
    return verification;
  }

  public void assertAllSucceeded() {
    verifications.forEach(Verification::assertSucceed);
  }

}
