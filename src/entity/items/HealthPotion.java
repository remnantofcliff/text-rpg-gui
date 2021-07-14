package entity.items;

import entity.Item;
import entity.interfaces.BattleItem;
import entity.interfaces.Droppable;
import entity.player.Player;

/**
 * Basic health potion class. Restores hp to player.
 */
public class HealthPotion extends Item implements BattleItem, Droppable {
  public HealthPotion() {
    name = "Health Potion";
  }
  
  @Override
  public void use() {
    drop();
    Player.getInstance().addHp(25);
  }
}