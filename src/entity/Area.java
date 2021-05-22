package entity;

import entity.interfaces.Talker;
import entity.interfaces.Vendor;

/**
 * Abstract area game.
 */
public abstract class Area extends Entity {
  protected Action[] actions;
  protected String description;
  protected String location;
  protected Talker[] talkers;
  protected Vendor[] vendors;

  public Action[] getActions() {
    return actions;
  }
  
  private boolean checkIfEmpty(Object[] objects) {
    return objects.length != 0;
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

  public boolean hasActions() {
    return checkIfEmpty(actions);
  }

  public boolean hasTalkers() {
    return checkIfEmpty(talkers);
  }

  public boolean hasVendors() {
    return checkIfEmpty(vendors);
  }
}
