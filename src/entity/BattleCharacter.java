package entity;

import inventory.Inventory;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for characters that can battle.
 */
public abstract class BattleCharacter extends Entity {
  protected Armor armor;
  protected List<String> statusEffects = new ArrayList<>();
  protected Inventory inventory = new Inventory();
  protected Weapon weapon;
  protected double hp;
  protected double mp;
  protected double sp;
  protected double speed;
  protected int maxHp;
  protected int maxMp;
  protected int maxSp;
  
  public Armor getArmor() {
    return armor;
  }

  public Inventory getInventory() {
    return inventory;
  }
  /**
   * Adds the parameter amount of hp. If larger than maxHp, sets hp to maxHp.

   * @param add Hp to be added (double)
   */
  public boolean addHp(double add) {
    if (hp == maxHp) {
      return false;
    }
    hp += add;
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
    hp -= remove;
    if (hp <= 0) {
      hp = 0;
    }
  }
  
  public void setArmor(Armor armor) {
    this.armor = armor;
  }

  public List<String> getStatusEffects() {
    return statusEffects;
  }

  public void setStatusEffects(List<String> statusEffects) {
    this.statusEffects = statusEffects;
  }

  public Weapon getWeapon() {
    return weapon;
  }

  public void setWeapon(Weapon weapon) {
    this.weapon = weapon;
  }

  protected int dexterity;
  protected int strength;
  protected int magic;

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
}
