package entity;

import core.DamageTypes;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Abstract armor class.
 */
public abstract class Armor extends Item {
  protected int flatAbsorption;
  protected EnumMap<DamageTypes, Float> absorptionMap = new EnumMap<>(DamageTypes.class);
  /**
   * Returns damage after calculating negation by armor.

   * @param damage (float)
   * @param damageTypeMap contains attacks damagetypes and float numbers as percentage of damage.
   * @return (float)
   */
  public float absorb(float damage, Map<DamageTypes, Float> damageTypeMap) {
    float flat = damage - flatAbsorption;
    if (flat <= 0) {
      return 0;
    }
    damage = 0;
    for (Entry<DamageTypes, Float> entry : damageTypeMap.entrySet()) {
      damage = flat * entry.getValue() * (1 - absorptionMap.get(entry.getKey()));
    }
    return damage;
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
