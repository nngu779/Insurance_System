package nz.ac.auckland.se281;

import java.util.ArrayList;
import nz.ac.auckland.se281.Main.PolicyType;

public class InsuranceSystem {

  // Initialise array list to store all usernames and ages
  private ArrayList<Profile> database = new ArrayList<Profile>();

  // Initialise variables
  private boolean loadSuccess = false;
  private String loadedProfile = "";
  private String profileFound;
  private String loadedProfileAge;
  private Profile loadedProfileObject;

  public InsuranceSystem() {
    // Only this constructor can be used (if you need to initialise fields).
  }

  public void printDatabase() {

    if (database.isEmpty()) {
      // Print message if database is empty
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage("0", "s", ".");
    } else if (database.size() == 1) {
      // Print message when database has one profile
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage("1", "", ":");
    } else if (database.size() > 1) {
      // Print message when database has more than one profile
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage(Integer.toString(database.size()), "s", ":");
    }

    // Print database details
    int i = 1;
    for (Profile p : database) {
      String userName = p.getUserName();
      String age = p.getAge();
      int policiesSize = p.getPoliciesSize();
      String finalPremium = Integer.toString(p.totalDiscount());
      if (policiesSize == 0 && loadedProfile.equals(userName)) {
        MessageCli.PRINT_DB_PROFILE_HEADER_LONG.printMessage(
            "*** ", Integer.toString(i), loadedProfile, age, "0", "ies", finalPremium);
        i++;
      } else if (policiesSize == 0 && !loadedProfile.equals(userName)) {
        MessageCli.PRINT_DB_PROFILE_HEADER_LONG.printMessage(
            "", Integer.toString(i), userName, age, "0", "ies", finalPremium);
        i++;
      } else if (policiesSize == 1 && loadedProfile.equals(userName)) {
        MessageCli.PRINT_DB_PROFILE_HEADER_LONG.printMessage(
            "*** ", Integer.toString(i), loadedProfile, age, "1", "y", finalPremium);
        p.printPolicy();
        i++;
      } else if (policiesSize == 1 && !loadedProfile.equals(userName)) {
        MessageCli.PRINT_DB_PROFILE_HEADER_LONG.printMessage(
            "", Integer.toString(i), userName, age, "1", "y", finalPremium);
        p.printPolicy();
        i++;
      } else if (policiesSize > 1 && loadedProfile.equals(userName)) {
        MessageCli.PRINT_DB_PROFILE_HEADER_LONG.printMessage(
            "*** ",
            Integer.toString(i),
            loadedProfile,
            age,
            Integer.toString(policiesSize),
            "ies",
            finalPremium);
        p.printPolicy();
        i++;
      } else if (policiesSize > 1 && !loadedProfile.equals(userName)) {
        MessageCli.PRINT_DB_PROFILE_HEADER_LONG.printMessage(
            "",
            Integer.toString(i),
            userName,
            age,
            Integer.toString(policiesSize),
            "ies",
            finalPremium);
        p.printPolicy();
        i++;
      }
    }
  }

  public void createNewProfile(String userName, String age) {

    // Formatting of the letters in userName
    // Create substrings between index 0 and 1 of userName then turn first letter uppercase and the
    // rest lowercase
    userName = userName.substring(0, 1).toUpperCase() + userName.substring(1).toLowerCase();

    // Logic for userName
    if (userName.length() < 3) {
      // userName cannot be shorter than 3 letters
      MessageCli.INVALID_USERNAME_TOO_SHORT.printMessage(userName);
      return;
    }
    // check for uniqueness of username
    for (Profile p : database) {
      if (userName.equals(p.getUserName())) {
        MessageCli.INVALID_USERNAME_NOT_UNIQUE.printMessage(userName);
        return;
      }
    }

    // Logic for age
    // Invalidate decimals and non-numeric input for age
    try {
      int number = Integer.parseInt(age);
      // Invalidate non-positive integer input
      if (number < 1) {
        MessageCli.INVALID_AGE.printMessage(age, userName);
        return;
      }
    } catch (Exception ex) {
      MessageCli.INVALID_AGE.printMessage(age, userName);
      return;
    }

    // Forbid Create-Profile when a profile is loaded
    if (loadSuccess == true) {
      MessageCli.CANNOT_CREATE_WHILE_LOADED.printMessage(loadedProfile);
      return;
    }

    // Create profile when userName and age are successfully checked
    Profile newProfile = new Profile(userName, age);
    database.add(newProfile);
    MessageCli.PROFILE_CREATED.printMessage(userName, age);
  }

  public void loadProfile(String userName) {
    // Formatting of the letters in userName
    userName = userName.substring(0, 1).toUpperCase() + userName.substring(1).toLowerCase();

    // Check if the username exists in the system or not
    for (Profile p : database) {
      if (userName.equals(p.getUserName())) {
        MessageCli.PROFILE_LOADED.printMessage(userName);
        loadSuccess = true;
        loadedProfile = userName;
        loadedProfileAge = p.getAge();
        loadedProfileObject = p;
        return;
      }
    }

    if (!loadedProfile.equals(userName)) {
      MessageCli.NO_PROFILE_FOUND_TO_LOAD.printMessage(userName);
    }
  }

  public void unloadProfile() {
    if (loadSuccess == true) {
      MessageCli.PROFILE_UNLOADED.printMessage(loadedProfile);
      loadedProfile = "";
      loadSuccess = false;
    } else {
      MessageCli.NO_PROFILE_LOADED.printMessage();
    }
  }

  public void deleteProfile(String userName) {
    // Format input userName
    userName = userName.substring(0, 1).toUpperCase() + userName.substring(1).toLowerCase();

    // Check if profile is currently loaded
    if (loadedProfile.equals(userName)) {
      MessageCli.CANNOT_DELETE_PROFILE_WHILE_LOADED.printMessage(userName);
      return;
    }
    // Check if profile exists in the database (if yes then note down index to remove from database)
    int i = 0;
    for (Profile p : database) {
      if (userName.equals(p.getUserName())) {
        MessageCli.PROFILE_DELETED.printMessage(userName);
        profileFound = userName;
        database.remove(i);
        return;
      }
      i++;
    }
    if (!userName.equals(profileFound)) {
      MessageCli.NO_PROFILE_FOUND_TO_DELETE.printMessage(userName);
    }
  }

  public void createPolicy(PolicyType type, String[] options) {

    // Check if a profile is loaded or not
    if (loadSuccess == false) {
      MessageCli.NO_PROFILE_FOUND_TO_CREATE_POLICY.printMessage();
    } else {
      if (Integer.parseInt(loadedProfileAge) > 100 && type == PolicyType.LIFE) {
        MessageCli.OVER_AGE_LIMIT_LIFE_POLICY.printMessage(loadedProfile);
      } else {
        if (type == PolicyType.HOME) {
          loadedProfileObject.createHomePolicy(options[0], options[1], options[2]);
          MessageCli.NEW_POLICY_CREATED.printMessage("home", loadedProfile);
        } else if (type == PolicyType.LIFE && loadedProfileObject.getlifePolicyCount() == 0) {
          loadedProfileObject.createLifePolicy(loadedProfileAge, options[0]);
          MessageCli.NEW_POLICY_CREATED.printMessage("life", loadedProfile);
        } else if (type == PolicyType.LIFE && loadedProfileObject.getlifePolicyCount() == 1) {
          MessageCli.ALREADY_HAS_LIFE_POLICY.printMessage(loadedProfile);
        } else if (type == PolicyType.CAR) {
          loadedProfileObject.createCarPolicy(
              loadedProfileAge, options[0], options[1], options[2], options[3]);
          MessageCli.NEW_POLICY_CREATED.printMessage("car", loadedProfile);
        }
      }
    }
  }
}
