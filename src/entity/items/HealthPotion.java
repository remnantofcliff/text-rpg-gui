package entity.items;

import entity.Item;
import entity.interfaces.Droppable;
import entity.interfaces.Usable;
import entity.player.Player;

/**
 * Basic health potion class. Restores hp to player.
 */
public class HealthPotion extends Item implements Droppable, Usable {
  public HealthPotion() {
    name = "Health Potion";
  }
  
  @Override
  public void use(Player player) {
    drop(player);
    player.addHp(25);
  }
}