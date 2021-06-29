package entity.items;

import entity.Item;
import entity.enemies.Player;
import entity.interfaces.Droppable;
import entity.interfaces.Usable;
import utilities.Utilities;

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

  @Override
  public void drop(Player player) {
    Utilities.dropItem(player, this);
  }
}