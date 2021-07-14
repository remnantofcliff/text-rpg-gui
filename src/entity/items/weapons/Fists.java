package entity.items.weapons;

import core.DamageTypes;
import entity.Weapon;
import entity.interfaces.Equippable;
import entity.player.Player;

/**
 * Fists-weapon.
 */
public class Fists extends Weapon implements Equippable {

  /**
   * Sets the initial values.
   */
  public Fists() {
    name = "Fists";
    baseDamage = 1;
    dexterityModifier = 1;
    magicModifier = 0;
    strengthModifier = 2;
    range = 0.5f;
    damageTypeMap.put(DamageTypes.PHYSICAL, 1f);
  }

  @Override
  public void equip() {
    Player.getInstance().setWeapon(this);
  }
}