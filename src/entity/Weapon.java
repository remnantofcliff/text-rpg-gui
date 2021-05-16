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
   * Calculates the damage that the weapon does given player stats.

   * @param dexterity (int)
   * @param magic (int)
   * @param strength (int)
   * @return (double)
   */
  public double calculateDamage(int dexterity, int magic, int strength) {
    return Math.floor(
      baseDamage 
      + dexterityModifier * dexterity 
      + magicModifier * magic 
      + strengthModifier * strength
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