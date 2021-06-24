package entity.interfaces;

import entity.BattleCharacter;

/**
 * For items that can be used.
 */
public interface Usable {
  void use(BattleCharacter battleCharacter);
}