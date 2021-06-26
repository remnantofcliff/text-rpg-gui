package entity.interfaces;

import entity.BattleEntity;

/**
 * For items that can be used.
 */
public interface Usable {
  void use(BattleEntity battleCharacter);
}