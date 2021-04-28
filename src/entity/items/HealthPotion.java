package entity.items;

import entity.BattleCharacter;
import entity.Item;
import entity.interfaces.Usable;

/**
 * Basic health potion class. Restores hp to player.
 */
public class HealthPotion extends Item implements Usable {
  
  public HealthPotion() {
    name = "Health Potion";
  }

  @Override
  public boolean use(BattleCharacter battleCharacter) {
    return battleCharacter.addHp(25);
  }
}