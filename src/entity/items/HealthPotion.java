package entity.items;

import entity.BattleCharacter;
import entity.Item;
import entity.interfaces.Droppable;
import entity.interfaces.Usable;

/**
 * Basic health potion class. Restores hp to player.
 */
public class HealthPotion extends Item implements Droppable, Usable {
  public HealthPotion() {
    name = "Health Potion";
  }
  
  @Override
  public boolean use(BattleCharacter battleCharacter) {
    drop(battleCharacter);
    return battleCharacter.addHp(25);
  }

  @Override
  public void drop(BattleCharacter battleCharacter) {
    battleCharacter.getInventory().remove(this);
  }
}