package entity.items.weapons;

import core.DamageTypes;
import entity.Weapon;
import entity.interfaces.Droppable;
import entity.interfaces.Equippable;
import entity.player.Player;

/**
 * Cane-weapon.
 */
public class Cane extends Weapon implements Droppable, Equippable {

  /**
   * Sets the initial values.
   */
  public Cane() {
    name = "Cane";
    baseDamage = 10;
    dexterityModifier = 1;
    magicModifier = 0;
    strengthModifier = 1;
    damageTypeMap.put(DamageTypes.PHYSICAL, 1f);
  }

  @Override
  public void drop() {
    var player = Player.getInstance();
    if (player.getWeapon() == this) {
      player.setWeapon(new Fists());
    }
    player.getInventory().remove(this);
  }

  @Override
  public void equip() {
    Player.getInstance().setWeapon(this);
  }
}
