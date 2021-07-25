package entity;

import java.util.List;

/**
 * Abstract ability-class.
 */
public abstract class Ability extends Entity {
  protected int resourceCost;

  public int getResourceCost() {
    return resourceCost;
  }

  public abstract void use(int userIndex, List<Enemy> enemies);
}
