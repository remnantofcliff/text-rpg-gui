package entity.player;

import core.Difficulties;
import entity.Area;
import entity.Armor;
import entity.BattleEntity;
import entity.Weapon;
import entity.abilities.specials.Rush;
import entity.areas.Dretnos;
import entity.items.CustomItem;
import entity.items.armors.Rags;
import entity.items.weapons.Fists;
import inventory.Inventory;

/**
 * Player character class.
 */
public class Player extends BattleEntity {
  private Area area = new Dretnos();
  private Difficulties difficulty = Difficulties.NORMAL;
  private Inventory inventory = new Inventory();
  
  /**
   * Creates a new player-object.
   */
  public Player() {
    setDefaultValues();
    for (var i = 0; i < 100; i++) {
      inventory.add(new CustomItem(Integer.toString(i)));
    }
  }

  public Area getArea() {
    return area;
  }

  public Difficulties getDifficulty() {
    return difficulty;
  }

  public Inventory getInventory() {
    return inventory;
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

  @Override
  public void setDefaultValues() {
    name = "Johnny";
    inventory.add(new Fists());
    inventory.add(new Rags());
    weapon = (Weapon) inventory.getItem("Fists");
    armor = (Armor) inventory.getItem("Rags");
    specials.add(new Rush());
    setMaxResources(100, 100, 5);
    setStats(1, 1, 1);
  }

  public void setDifficulty(Difficulties difficulty) {
    this.difficulty = difficulty;
  }
}