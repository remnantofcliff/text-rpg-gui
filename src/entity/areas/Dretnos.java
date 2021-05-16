package entity.areas;

import action.Action;
import entity.Area;
import entity.characters.DretnosFarmer;
import entity.characters.DretnosVendor1;
import entity.characters.DretnosVendor2;
import entity.interfaces.Leavable;
import entity.interfaces.Talker;
import entity.interfaces.Vendor;

/**
 * Dretnos-area.
 */
public class Dretnos extends Area implements Leavable {
  /**
   * Sets the initial values for area.
   */
  public Dretnos() {
    name = "Dretnos";
    description =
        "A small town built in a cavern with a dozen people. "
      + "There's a some farmland with Girnots growing in them.";
    location = "Western Dereliquerat";
    vendors = new Vendor[] {new DretnosVendor1(), new DretnosVendor2()};
    talkers = new Talker[] {new DretnosFarmer()};
    actions = new Action[] {};
  }
  
  @Override
  public String[] getDirections() {
    return new String[] {"Exit to the east"};
  }

  @Override
  public Area nextLocation(String direction) {
    return null;
  }
}
