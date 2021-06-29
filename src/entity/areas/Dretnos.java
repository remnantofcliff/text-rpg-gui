package entity.areas;

import entity.Area;
import entity.Event;
import entity.characters.DretnosFarmer;
import entity.characters.DretnosVendor1;
import entity.characters.DretnosVendor2;
import entity.enemies.Crawler;
import entity.events.Battle;
import entity.events.DretnosEvent1;
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
    description = """
      A small town built in a cavern with about a dozen people.
      There's some farmland with girnots growing in them. \
      A farmer is working the lands.
      The small cavern has two layers and the people are living in holes in the walls of the cavern.
      """;
    location = "Western Dereliquerat";
    vendors = new Vendor[] {new DretnosVendor1(), new DretnosVendor2()};
    talkers = new Talker[] {new DretnosFarmer()};
    events = new Event[] {new DretnosEvent1(), new Battle(new Crawler())};
  }

  @Override
  public String[] getDirections() {
    return new String[] {"Exit to the east"};
  }

  @Override
  public Area nextLocation(String direction) {
    return this;
  }
}
