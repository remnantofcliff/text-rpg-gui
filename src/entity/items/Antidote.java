package entity.items;

import entity.Item;
import entity.interfaces.Droppable;
import entity.interfaces.Usable;
import entity.player.Player;

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
}
