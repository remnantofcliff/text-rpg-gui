package entity.characters;

import entity.Area;
import entity.Armor;
import entity.BattleEntity;
import entity.Weapon;
import entity.areas.Dretnos;
import entity.items.armors.Rags;
import entity.items.weapons.Fists;
import main.Difficulties;

/**
 * Player character class.
 */
public class Player extends BattleEntity {
  private Area area = new Dretnos();
  private Difficulties difficulty = Difficulties.NORMAL;
  
  /**
   * Creates a new player-object.
   */
  public Player() {
    name = "Johnny";
    maxHp = 100;
    maxMp = 100;
    maxSp = 100;
    hp = 100;
    mp = 100;
    sp = 100;
    speed = 1;
    dexterity = 1;
    magic = 1;
    strength = 1;
    inventory.add(new Fists());
    inventory.add(new Rags());
    weapon = (Weapon) inventory.getItem("Fists");
    armor = (Armor) inventory.getItem("Rags");
    
  }

  public Area getArea() {
    return area;
  }

  public Difficulties getDifficulty() {
    return difficulty;
  }

  public void incrementStrength() {
    strength++;
  }

  public void incrementDexterity() {
    dexterity++;
  }
  
  public void incrementMagic() {
    magic++;
  }

  public void setArea(Area area) {
    this.area = area;
  }

  public void setDifficulty(Difficulties difficulty) {
    this.difficulty = difficulty;
  }
}