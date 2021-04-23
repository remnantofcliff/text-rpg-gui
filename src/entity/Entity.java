package entity;

/**
 * Every entity in game must extend this class.
 */
public abstract class Entity {
  protected String name;
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
