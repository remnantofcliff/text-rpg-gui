package entity.player;

import core.Difficulties;
import entity.Area;
import entity.Armor;
import entity.BattleEntity;
import entity.Weapon;
import entity.abilities.specials.Rush;
import entity.items.armors.Rags;
import entity.items.weapons.Fists;
import inventory.Inventory;

/**
 * Player character class.
 */
public final class Player extends BattleEntity {
  private transient Area area;
  private Difficulties difficulty = Difficulties.NORMAL;
  private Inventory inventory = new Inventory();
  private int areaId = 0;
  
  /**
   * Creates a new player-object.
   */
  public Player() {
    setDefaultValues();
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

  public int getAreaId() {
    return areaId;
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
    setAreaId(area.getId());
  }

  public void setAreaId(int id) {
    areaId = id;
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