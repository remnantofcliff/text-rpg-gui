package entity.items.weapons;

import entity.BattleEntity;
import entity.Weapon;
import entity.interfaces.Equippable;

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
    range = 0.5;
  }

  @Override
  public void equip(BattleEntity battleCharacter) {
    battleCharacter.setWeapon(this);
  }
}