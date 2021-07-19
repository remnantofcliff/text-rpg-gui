package entity;

import java.util.List;
import main.Game;

/**
 * Abstract ability-class.
 */
public abstract class Ability extends Entity {
  protected int resourceCost;

  public int getResourceCost() {
    return resourceCost;
  }

  public abstract void use(Game game, int userIndex, List<Enemy> enemies);
}
