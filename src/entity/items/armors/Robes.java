package entity.items.armors;

import entity.Armor;
import entity.enemies.Player;
import entity.interfaces.Droppable;
import entity.interfaces.Equippable;

/**
 * Robes-armor.
 */
public class Robes extends Armor implements Droppable, Equippable {
  /**
   * Sets initial values for robes.
   */
  public Robes() {
    name = "Robes";
    flatAbsorption = 2;
    physicalAbsorption = 0.1;
    magicAbsorption = 0.2;
  }

  @Override
  public void equip(Player player) {
    player.setArmor(this);
  }

  @Override
  public void drop(Player player) {
    if (player.getArmor() == this) {
      player.setArmor(Naked.getInstance());
    }
    player.getInventory().remove(this);
  }
}
