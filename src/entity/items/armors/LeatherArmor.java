package entity.items.armors;

import entity.Armor;
import entity.BattleEntity;
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
  public void equip(BattleEntity battleCharacter) {
    battleCharacter.setArmor(this);
  }

  @Override
  public void drop(BattleEntity battleCharacter) {
    if (battleCharacter.getArmor() == this) {
      battleCharacter.setArmor(Naked.getInstance());
    }
    battleCharacter.getInventory().remove(this);
  }
  
}
