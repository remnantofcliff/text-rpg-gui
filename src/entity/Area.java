package entity;

import entity.areas.Dretnos;
import entity.interfaces.Talker;
import entity.interfaces.Vendor;

/**
 * Abstract area game.
 */
public abstract class Area extends Entity {
  protected Event[] events;
  protected String description;
  protected String location;
  protected Talker[] talkers;
  protected Vendor[] vendors;
  protected int id;

  public Event[] getEvents() {
    return events;
  }
  
  public String getDescription() {
    return description;
  }

  public String getLocation() {
    return location;
  }

  public Talker[] getTalkers() {
    return talkers;
  }

  public Vendor[] getVendors() {
    return vendors;
  }

  private boolean checkIfEmpty(Object[] objects) {
    return objects.length != 0;
  }

  public boolean hasEvents() {
    return checkIfEmpty(events);
  }

  public boolean hasTalkers() {
    return checkIfEmpty(talkers);
  }

  public boolean hasVendors() {
    return checkIfEmpty(vendors);
  }

  public int getId() {
    return id;
  }

  /**
   * Returns the approppriate area with the given id.

   * @param id (int)
   * @return (Area)
   */
  public static Area loadArea(int id) {
    return switch (id) {
      case 0 -> new Dretnos();
      case 1 -> new Area(){};
      default -> new Dretnos();
    };
  }
}
