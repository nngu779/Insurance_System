package nz.ac.auckland.se281;

public class HomePolicy extends Policy {

  private String address;
  private String rental;

  public HomePolicy(String sumInsured, String address, String rental) {
    super(sumInsured);
    this.address = address;
    this.rental = rental;
  }

  // Calculate a home policy base premium based on whether the property is a rental or not
  public int basePremium() {
    int basePremium = 0;
    if (rental.contains("y") || rental.contains("Y")) {
      basePremium = (int) (0.02 * Integer.parseInt(sumInsured));
    } else {
      basePremium = (int) (0.01 * Integer.parseInt(sumInsured));
    }
    return basePremium;
  }

  public int getBasePremium() {
    return basePremium();
  }

  @Override
  public void printPolicyDetails(int discount) {
    MessageCli.PRINT_DB_HOME_POLICY.printMessage(
        address, sumInsured, Integer.toString(getBasePremium()), Integer.toString(discount));
  }
}
