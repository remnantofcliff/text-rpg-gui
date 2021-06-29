package entity.items;

import entity.Item;
import entity.enemies.Player;
import entity.interfaces.Droppable;
import entity.interfaces.Usable;
import utilities.Utilities;

/**
 * Antidote item. Implements Droppable, Usable.
 */
public class Antidote extends Item implements Droppable, Usable {
  public Antidote() {
    name = "Antidote";
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
