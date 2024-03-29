package entity.items.weapons;

import core.DamageTypes;
import entity.Weapon;
import entity.interfaces.Droppable;
import entity.interfaces.Equippable;
import entity.player.Player;

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