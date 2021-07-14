package entity.interfaces;

import entity.BattleEntity;

/**
 * For BattleEntities that are poisonable.
 */
public interface Poisonable {
  String POISONED = "Poisoned";
  default void poison() {
    ((BattleEntity) this).getStatusEffects().add(POISONED);
  }
}
