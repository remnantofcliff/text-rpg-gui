package entity.items;

import entity.Armor;
import entity.characters.Player;
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
    player.getInventory().remove(this);
  }
}
