package entity;


/**
 * Abstract class for special attacks.
 */
public abstract class Special extends Ability {
  protected boolean useResource(BattleEntity e, int amount) {
    if (e.getSp() < amount) {
      return false;
    }
    e.removeSp(amount);
    return true;
  }
}
