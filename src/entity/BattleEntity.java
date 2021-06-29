package entity;

/**
 * Class for characters that can battle.
 */
public abstract class BattleEntity extends Entity {
  protected Armor armor;
  protected Weapon weapon;
  protected double hp;
  protected double mp;
  protected double sp;
  protected double speed;
  protected int dexterity;
  protected int magic;
  protected int maxHp;
  protected int maxMp;
  protected int maxSp;
  protected int strength;
  
  public Armor getArmor() {
    return armor;
  }
  /**
   * Adds the parameter amount of hp. If larger than maxHp, sets hp to maxHp.

   * @param add Hp to be added (double)
   */
  public boolean addHp(double add) {
    if (hp == maxHp) {
      return false;
    }
    hp += Math.floor(add);
    if (hp >= maxHp) {
      hp = maxHp;
    }
    return true;
  }
  /**
   * Removes the parameter amount of hp. If smaller than 0, sets hp to 0.

   * @param remove Hp to be removed (double)
   */
  public void removeHp(double remove) {
    hp -= Math.floor(remove);
    if (hp <= 0) {
      hp = 0;
    }
  }
  
  public void setArmor(Armor armor) {
    this.armor = armor;
  }

  public Weapon getWeapon() {
    return weapon;
  }

  public void setWeapon(Weapon weapon) {
    this.weapon = weapon;
  }

  public double getHp() {
    return hp;
  }

  public double getMp() {
    return mp;
  }

  public double getSp() {
    return sp;
  }

  public double getSpeed() {
    return speed;
  }

  public int getMaxHp() {
    return maxHp;
  }

  public int getMaxMp() {
    return maxMp;
  }

  public int getMaxSp() {
    return maxSp;
  }

  public int getStrength() {
    return strength;
  }

  public int getDexterity() {
    return dexterity;
  }

  public int getMagic() {
    return magic;
  }

  public void setHp(double hp) {
    this.hp = hp;
  }

  public void setMp(double mp) {
    this.mp = mp;
  }

  public void setSp(double sp) {
    this.sp = sp;
  }

  public void setSpeed(double speed) {
    this.speed = speed;
  }

  public void setMaxHp(int maxHp) {
    this.maxHp = maxHp;
  }

  public void setMaxMp(int maxMp) {
    this.maxMp = maxMp;
  }

  public void setMaxSp(int maxSp) {
    this.maxSp = maxSp;
  }

  public void setStrength(int strength) {
    this.strength = strength;
  }

  public void setDexterity(int dexterity) {
    this.dexterity = dexterity;
  }

  public void setMagic(int magic) {
    this.magic = magic;
  }

  public abstract void setDefaultValues();
}
