package entity;

import static utilities.Utilities.round;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Class for characters that can battle.
 */
public abstract class BattleEntity extends Entity {
  protected Armor armor;
  protected ArrayList<Special> specials = new ArrayList<>();
  protected ArrayList<Spell> spells = new ArrayList<>();
  protected HashSet<String> statusEffects = new HashSet<>();
  protected Weapon weapon;
  protected float hp;
  protected int mp;
  protected int dexterity;
  protected int magic;
  protected int maxHp;
  protected int maxMp;
  protected int maxSp;
  protected int sp;
  protected int strength;

  protected void setMaxResources(int hp, int mp, int sp) {
    maxHp = hp;
    this.hp = hp;
    maxMp = mp;
    this.mp = mp;
    maxSp = sp;
    this.sp = sp;
  }

  protected void setStats(int dex, int mag, int str) {
    dexterity = dex;
    magic = mag;
    strength = str;
  }
  
  public Armor getArmor() {
    return armor;
  }

  public Special[] getSpecials() {
    return specials.toArray(new Special[0]);
  }

  public Spell[] getSpells() {
    return spells.toArray(new Spell[0]);
  }

  public Set<String> getStatusEffects() {
    return statusEffects;
  }

  public Weapon getWeapon() {
    return weapon;
  }

  public float getHp() {
    return hp;
  }

  public int getMp() {
    return mp;
  }

  public int getSp() {
    return sp;
  }

  public int getDexterity() {
    return dexterity;
  }

  public int getMagic() {
    return magic;
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
  /**
   * Adds the parameter amount of hp. If larger than maxHp, sets hp to maxHp.

   * @param num Hp to be added (float)
   */
  public void addHp(float num) {
    hp += num;
    if (hp > maxHp) {
      hp = maxHp;
    } else {
      hp = round(hp);
    }
  }
  /**
   * Adds the parameter amount of mp. If larger than maxMp, sets mp to maxMp.

   * @param num Mp to be added (int)
   */
  public void addMp(int num) {
    mp += num;
    if (mp > maxMp) {
      mp = maxMp;
    }
  }
  /**
   * Adds the parameter amount of sp. If larger than maxSp, sets sp to maxSp.

   * @param num Sp to be added (int)
   */
  public void addSp(int num) {
    sp += num;
    if (sp > maxSp) {
      sp = maxSp;
    }
  }

  public void addSpecial(Special special) {
    specials.add(special);
  }

  public void addSpell(Spell spell) {
    spells.add(spell);
  }

  /**
   * Regenerates sp for character.
   */
  public void regenerateSp() {
    if (sp != maxSp) {
      sp++;
    }
  }
  /**
   * Removes the parameter amount of hp. If smaller than 0, sets hp to 0.

   * @param num Hp to be removed (float)
   */
  public void removeHp(float num) {
    hp -= num;
    if (hp < 0) {
      hp = 0;
    } else {
      hp = round(hp);
    }
  }
  /**
   * Removes the parameter amount of mp. If smaller than 0, sets mp to 0.

   * @param remove Mp to be removed (int)
   */
  public void removeMp(int remove) {
    mp -= remove;
    if (mp < 0) {
      mp = 0; 
    }
  }
  /**
   * Removes the parameter amount of sp. If smaller than 0, sets sp to 0.

   * @param remove Sp to be removed (int)
   */
  public void removeSp(int remove) {
    sp -= remove;
    if (sp < 0) {
      sp = 0;
    }
  }
  
  public void setArmor(Armor armor) {
    this.armor = armor;
  }

  public void setWeapon(Weapon weapon) {
    this.weapon = weapon;
  }

  public abstract void setDefaultValues();
}
