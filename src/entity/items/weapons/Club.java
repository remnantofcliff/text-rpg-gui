package entity.items.weapons;

import entity.Weapon;
import entity.enemies.Player;
import entity.interfaces.Droppable;
import entity.interfaces.Equippable;

/**
 * Club-weapon.
 */
public class Club extends Weapon implements Droppable, Equippable {
  /**
   * Sets the initial values for Club.
   */
  public Club() {
    name = "Club";
    baseDamage = 5;
    dexterityModifier = 0.5;
    magicModifier = 0;
    strengthModifier = 2;
    range = 0.8;
  }
  
  @Override
  public void drop(Player player) {
    if (player.getWeapon() == this) {
      player.setWeapon(new Fists());
    }
    player.getInventory().remove(this);
  }

  @Override
  public void equip(Player player) {
    player.setWeapon(this);
  }
}
