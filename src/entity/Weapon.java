package entity;

/**
 * Abstract weapon class.
 */
public abstract class Weapon extends Item {
  protected double baseDamage;
  protected double dexterityModifier;
  protected double magicModifier;
  protected double strengthModifier;
  protected double range;
  /**
   * Calculates the damage that the weapon does given battleEntity stats.

   * @param e The BattleEntity 
   * @return (double)
   */
  public double calculateDamage(BattleEntity e) {
    return Math.floor(
      baseDamage 
      + dexterityModifier * e.getDexterity() 
      + magicModifier * e.getMagic()
      + strengthModifier * e.getStrength()
    );
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