package entity.items.armors;

import entity.Armor;
import entity.enemies.Player;
import entity.interfaces.Droppable;
import entity.interfaces.Equippable;

/**
 * Rags-armor.
 */
public class Rags extends Armor implements Droppable, Equippable {
  /**
   * Sets initial values.
   */
  public Rags() {
    name = "Rags";
    flatAbsorption = 0;
    physicalAbsorption = 0;
    magicAbsorption = 0.1;
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
