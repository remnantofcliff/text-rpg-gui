package entity.items.armors;

import entity.Armor;
import entity.BattleCharacter;
import entity.interfaces.Droppable;
import entity.interfaces.Equippable;

/**
 * Robes-armor.
 */
public class Robes extends Armor implements Droppable, Equippable {
  /**
   * Sets initial values for robes.
   */
  public Robes() {
    name = "Robes";
    flatAbsorption = 2;
    physicalAbsorption = 0.1;
    magicAbsorption = 0.2;
  }

  @Override
  public void equip(BattleCharacter battleCharacter) {
    battleCharacter.setArmor(this);
  }

  @Override
  public void drop(BattleCharacter battleCharacter) {
    if (battleCharacter.getArmor() == this) {
      battleCharacter.setArmor(Naked.getInstance());
    }
    battleCharacter.getInventory().remove(this);
  }
}
