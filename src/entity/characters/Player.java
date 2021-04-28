package entity.characters;

import entity.Armor;
import entity.BattleCharacter;
import entity.Weapon;
import entity.items.Antidote;
import entity.items.Fists;
import entity.items.HealthPotion;
import entity.items.Rags;
import inventory.Inventory;

/**
 * Player character class.
 */
public class Player extends BattleCharacter {
  private Inventory inventory = new Inventory();
  private String profession;

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
    for (int i = 0; i < 20; i++) {
      inventory.add(new HealthPotion());
      inventory.add(new Antidote());
    }
    weapon = (Weapon) inventory.getItem("Fists");
    armor = (Armor) inventory.getItem("Rags");
  }

  public Inventory getInventory() {
    return inventory;
  }

  public String getProfession() {
    return profession;
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

  public void setProfession(String profession) {
    this.profession = profession;
  }
}