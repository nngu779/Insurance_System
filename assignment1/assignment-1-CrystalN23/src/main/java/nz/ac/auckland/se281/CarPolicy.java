package nz.ac.auckland.se281;

public class CarPolicy extends Policy {

  private String makeModel;
  private String licensePlate;
  private String mechanicalBreakdown;
  private String age;

  public CarPolicy(
      String age,
      String sumInsured,
      String makeModel,
      String licensePlate,
      String mechanicalBreakdown) {
    super(sumInsured);
    this.makeModel = makeModel;
    this.licensePlate = licensePlate;
    this.mechanicalBreakdown = mechanicalBreakdown;
    this.age = age;
  }

  // Calculate the base premium based on loaded profile's age
  public int basePremium() {
    int basePremium = 0;
    if (Integer.parseInt(age) < 25) {
      // basePremium is 15% of sumInsured if profile is less than 25 years old
      basePremium = (int) (0.15 * Integer.parseInt(sumInsured));
    } else {
      // basePremium is 10% of sumInsured if profil is 25 years old or over
      basePremium = (int) (0.10 * Integer.parseInt(sumInsured));
    }
    // Increase premium when mechanical breakdown is included
    if (mechanicalBreakdown.contains("y") || mechanicalBreakdown.contains("Y")) {
      basePremium = basePremium + 80;
    }
    return basePremium;
  }

  public int getBasePremium() {
    return basePremium();
  }

  @Override
  public void printPolicyDetails(int discount) {
    MessageCli.PRINT_DB_CAR_POLICY.printMessage(
        makeModel, sumInsured, Integer.toString(getBasePremium()), Integer.toString(discount));
  }
}
