package entity.interfaces;

import entity.BattleEntity;

/**
 * For items that can be equipped.
 */
public interface Equippable {
  void equip(BattleEntity battleCharacter);
}
