package entity.interfaces;

import entity.enemies.Player;

/**
 * Interface for droppable items.
 */
public interface Droppable {
  void drop(Player player);
}
