package entity.interfaces;

import entity.BattleCharacter;

/**
 * For items that can be used.
 */
public interface Usable {
  boolean use(BattleCharacter battleCharacter);
}