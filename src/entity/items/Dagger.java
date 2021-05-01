package entity.items;

import entity.BattleCharacter;
import entity.Weapon;
import entity.interfaces.Droppable;
import entity.interfaces.Equippable;

/**
 * Dagger-weapon.
 */
public class Dagger extends Weapon implements Droppable, Equippable {

  /**
   * Sets initial values for dagger.
   */
  public Dagger() {
    name = "Dagger";
    baseDamage = 2;
    dexterityModifier = 2;
    magicModifier = 0;
    strengthModifier = 1;
    range = 0.5;
  }

  @Override
  public void equip(BattleCharacter battleCharacter) {
    battleCharacter.setWeapon(this);
  }

  @Override
  public void drop(BattleCharacter battleCharacter) {
    battleCharacter.getInventory().remove(this);
  }
}