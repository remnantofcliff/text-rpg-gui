package entity;

import action.Action;
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
}
