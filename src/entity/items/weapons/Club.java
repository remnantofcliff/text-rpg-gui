package entity.items.weapons;

import core.DamageTypes;
import entity.Weapon;
import entity.interfaces.Droppable;
import entity.interfaces.Equippable;
import entity.player.Player;

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
    dexterityModifier = 0.5f;
    magicModifier = 0;
    strengthModifier = 2;
    range = 0.8f;
    damageTypeMap.put(DamageTypes.PHYSICAL, 1f);
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
