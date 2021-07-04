package entity.interfaces;

import entity.Item;
import entity.player.Player;

/**
 * Interface for droppable items.
 */
public interface Droppable {
  default void drop(Player player) {
    player.getInventory().remove((Item) this);
  }
}
