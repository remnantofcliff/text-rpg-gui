package entity.interfaces;

import entity.characters.Player;

/**
 * For items that can be equipped.
 */
public interface Equippable {
  void equip(Player player);
}
