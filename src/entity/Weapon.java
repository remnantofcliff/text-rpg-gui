package entity;

import core.DamageTypes;
import java.util.EnumMap;
import java.util.Map;

/**
 * Abstract weapon class.
 */
public abstract class Weapon extends Item {
  protected EnumMap<DamageTypes, Float> damageTypeMap = new EnumMap<>(DamageTypes.class);
  protected float baseDamage;
  protected float dexterityModifier;
  protected float magicModifier;
  protected float strengthModifier;

  public Map<DamageTypes, Float> getDamageTypeMap() {
    return damageTypeMap;
  }
  /**
   * Calculates the damage that the weapon does given battleEntity stats.

   * @param e The BattleEntity 
   * @return (float)
   */
  public float calculateDamage(BattleEntity e) {
    return baseDamage + dexterityModifier * e.getDexterity() + magicModifier * e.getMagic() + strengthModifier * e.getStrength();
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }
  
  @Override
  public int hashCode() {
    return super.hashCode();
  }
}