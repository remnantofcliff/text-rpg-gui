package entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for characters that can battle.
 */
public abstract class BattleCharacter extends Entity {
  protected Armor armor;
  protected BigDecimal hp;
  protected BigDecimal mp;
  protected BigDecimal sp;
  protected BigDecimal speed;
  protected List<String> statusEffects = new ArrayList<>();
  protected Weapon weapon;
  protected int maxHp;
  protected int maxMp;
  protected int maxSp;
  
  public Armor getArmor() {
    return armor;
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

  public BigDecimal getHp() {
    return hp;
  }

  public BigDecimal getMp() {
    return mp;
  }

  public BigDecimal getSp() {
    return sp;
  }

  public BigDecimal getSpeed() {
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

  public void setHp(BigDecimal hp) {
    this.hp = hp;
  }

  public void setMp(BigDecimal mp) {
    this.mp = mp;
  }

  public void setSp(BigDecimal sp) {
    this.sp = sp;
  }

  public void setSpeed(BigDecimal speed) {
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
