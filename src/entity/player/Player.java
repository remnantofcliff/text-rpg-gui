package entity.player;

import core.Difficulties;
import entity.Armor;
import entity.BattleEntity;
import entity.Item;
import entity.Weapon;
import entity.abilities.specials.Rush;
import entity.items.Antidote;
import entity.items.HealthPotion;
import entity.items.armors.Rags;
import entity.items.weapons.Fists;
import inventory.Inventory;
import save.Save;

/**
 * Player character class.
 */
public final class Player extends BattleEntity {
  private Difficulties difficulty = Difficulties.NORMAL;
  private Inventory inventory = new Inventory();
  private Item[] quickItems = new Item[3];
  private Save save = new Save();
  private int areaId = 0;
  private static Player instance;
  
  /**
   * Creates a new player-object.
   */
  private Player() {
    setDefaultValues();
    inventory.add(new HealthPotion());
    inventory.add(new Antidote());
  }

  public Difficulties getDifficulty() {
    return difficulty;
  }

  public Inventory getInventory() {
    return inventory;
  }

  public Item[] getQuickItems() {
    return quickItems;
  }

  public Save getSave() {
    return save;
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
    setMaxResources(1000, 100, 5);
    setStats(10, 1, 1);
  }

  public void setDifficulty(Difficulties difficulty) {
    this.difficulty = difficulty;
  }

  public void setToMaxSp() {
    sp = maxSp;
  }

  /**
   * Returns the current single instance of player.

   * @return (Player)
   */
  public static Player getInstance() {
    if (instance == null) {
      instance = new Player();
    }
    return instance;
  }
}