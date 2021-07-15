package entity.abilities.spells;

import entity.Enemy;
import entity.Spell;
import entity.enemies.Bear;
import main.Game;

/**
 * "Polymorph self"-spell.
 */
public class PolymorphSelf extends Spell {
  public PolymorphSelf() {
    name = "Polymorph self";
    resourceCost = 60;
  }

  @Override
  public void use(Game game, int userIndex, Enemy[] enemies) {
    var bear = new Bear();
    bear.setSpawnOnDeath(enemies[userIndex]);
    enemies[userIndex] = bear;
  }
}
