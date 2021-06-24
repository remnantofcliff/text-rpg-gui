package entity;

import java.io.Serializable;

/**
 * Every entity in game must extend this class.
 */
public abstract class Entity implements Serializable {
  protected String name;
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
