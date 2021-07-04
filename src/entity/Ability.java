package entity;

import main.Game;

/**
 * Abstract ability-class.
 */
public abstract class Ability extends Entity {
  protected int resourceCost;

  public int getResourceCost() {
    return resourceCost;
  }

  public abstract void use(BattleEntity user, Game game, BattleEntity... targets);
}
