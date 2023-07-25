package nz.ac.auckland.se281;

public class LifePolicy extends Policy {

  private String age;

  public LifePolicy(String age, String sumInsured) {
    super(sumInsured);
    this.age = age;
  }

  public int basePremium() {
    int basePremium = 0;
    basePremium =
        (int) ((1.0 + Integer.parseInt(age) / 100.0) / 100 * Integer.parseInt(sumInsured));
    return basePremium;
  }

  public int getBasePremium() {
    return basePremium();
  }

  @Override
  public void printPolicyDetails(int discount) {
    MessageCli.PRINT_DB_LIFE_POLICY.printMessage(
        sumInsured, Integer.toString(getBasePremium()), Integer.toString(discount));
  }
}
