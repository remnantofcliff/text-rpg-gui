package entity;

/**
 * Abstract armor class.
 */
public abstract class Armor extends Item {
  protected int flatAbsorption;
  protected double physicalAbsorption;
  protected double magicAbsorption;
  /**
   * Returns damage after calculating negation by armor.

   * @param damage (double)
   * @param magic true if magic, false if physical damage type (boolean)
   * @return (double)
   */
  public double absorb(double damage, boolean magic) {
    double flat = damage - flatAbsorption;
    if (magic) {
      return flat * magicAbsorption;
    }
    return flat * physicalAbsorption;
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
