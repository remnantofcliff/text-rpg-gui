package entity.interfaces;

import entity.BattleEntity;

/**
 * Interface for stunnable BattleEntities.
 */
public interface Stunnable {
  String STUNNED = "Stunned";
  default void stun() {
    ((BattleEntity) this).getStatusEffects().add(STUNNED);
  }
}
