package entity.interfaces;

import entity.BattleCharacter;

/**
 * For items that can be equipped.
 */
public interface Equippable {
  void equip(BattleCharacter battleCharacter);
}
