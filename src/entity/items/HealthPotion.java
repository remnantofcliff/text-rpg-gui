package entity.items;

import entity.BattleEntity;
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
  public void use(BattleEntity battleCharacter) {
    drop(battleCharacter);
    battleCharacter.addHp(25);
  }

  @Override
  public void drop(BattleEntity battleCharacter) {
    battleCharacter.getInventory().remove(this);
  }
}