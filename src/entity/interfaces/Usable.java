package entity.interfaces;

import entity.enemies.Player;

/**
 * For items that can be used.
 */
public interface Usable {
  void use(Player e);
}