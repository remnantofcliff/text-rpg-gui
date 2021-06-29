package entity.items.armors;

import entity.Armor;
import entity.enemies.Player;
import entity.interfaces.Droppable;
import entity.interfaces.Equippable;

/**
 * Leather armor.
 */
public class LeatherArmor extends Armor implements Droppable, Equippable {
  /**
   * Sets the initial values for leather armor.
   */
  public LeatherArmor() {
    name = "Leather Armor";
    flatAbsorption = 5;
    magicAbsorption = 0.25;
    physicalAbsorption = 0.2;
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
