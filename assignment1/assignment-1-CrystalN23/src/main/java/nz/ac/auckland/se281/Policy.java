package nz.ac.auckland.se281;

public abstract class Policy {

  protected String sumInsured;

  public Policy(String sumInsured) {
    this.sumInsured = sumInsured;
  }

  // Each policy type has a different way to calculate basePremium
  abstract int basePremium();

  // Getter for basePremium
  public abstract int getBasePremium();

  // Each policy type has a different way to print basePremium
  public abstract void printPolicyDetails(int discount);
}
