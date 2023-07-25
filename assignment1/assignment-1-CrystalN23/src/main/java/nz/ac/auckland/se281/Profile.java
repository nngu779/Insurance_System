package nz.ac.auckland.se281;

import java.util.ArrayList;

public class Profile {

  // Initialise arraylist and variables
  private ArrayList<Policy> policies = new ArrayList<Policy>();
  private String userName;
  private String age;
  private int individualDiscount;
  private int totalDiscount;
  private int totalSum;
  private int lifePolicyCount;

  public Profile(String userName, String age) {
    this.userName = userName;
    this.age = age;
  }

  public String getUserName() {
    return this.userName;
  }

  public String getAge() {
    return this.age;
  }

  // Create a new home policy and add into policies arraylist
  public void createHomePolicy(String sumInsured, String address, String rental) {
    Policy newHomePolicy = new HomePolicy(sumInsured, address, rental);
    policies.add(newHomePolicy);
  }

  // Create a new life policy and add into policies arraylist
  public void createLifePolicy(String age, String sumInsured) {
    if (lifePolicyCount == 1) {
      return;
    }
    Policy newLifePolicy = new LifePolicy(age, sumInsured);
    policies.add(newLifePolicy);
    lifePolicyCount = 1;
  }

  public void createCarPolicy(
      String age,
      String sumInsured,
      String makeModel,
      String licensePlate,
      String mechanicalBreakdown) {
    // Create a new car policy and add into policies arraylist
    Policy newCarPolicy =
        new CarPolicy(age, sumInsured, makeModel, licensePlate, mechanicalBreakdown);
    policies.add(newCarPolicy);
  }

  public int getlifePolicyCount() {
    return lifePolicyCount;
  }

  public int getPoliciesSize() {
    return policies.size();
  }

  // Calculate total premium
  public int totalSum() {
    totalSum = 0;
    for (Policy pol : policies) {
      totalSum += pol.getBasePremium();
    }
    return totalSum;
  }

  // Calculate policy discounts
  public int totalDiscount() {
    totalDiscount = totalSum();
    if (policies.size() == 2) {
      totalDiscount = (int) (0.9 * totalSum);
    } else if (policies.size() >= 3) {
      totalDiscount = (int) (0.8 * totalSum);
    }
    return totalDiscount;
  }

  // Print policy method and applying discounts depending on how many policies there are
  public void printPolicy() {
    for (Policy pol : policies) {
      int individualPremium = pol.getBasePremium();
      // No discounts for a profile with less than 2 policies
      if (policies.size() == 0 || policies.size() == 1) {
        individualDiscount = individualPremium;
      } else if (policies.size() == 2) {
        // 10% discount for profiles with 2 policies
        individualDiscount = (int) (0.9 * individualPremium);
      } else if (policies.size() >= 3) {
        // 20% discount for profiles with 3+ policies
        individualDiscount = (int) (0.8 * individualPremium);
      }
      pol.printPolicyDetails(individualDiscount);
    }
  }
}
