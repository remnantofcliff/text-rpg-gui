package entity.items.armors;

import entity.Armor;
import entity.BattleCharacter;
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
