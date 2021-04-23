package entity.characters;

import entity.Armor;
import entity.BattleCharacter;
import entity.Weapon;
import entity.armors.Rags;
import entity.weapons.Fists;
import items.Items;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Player character class.
 */
public class Player extends BattleCharacter {
  private Map<Items, Integer> inventory = new EnumMap<>(Items.class);
  private List<Weapon> weapons = new ArrayList<>();
  private List<Armor> armors = new ArrayList<>();
  private String profession;

  /**
   * Creates a new player-object.
   */
  public Player() {
    name = "Johnny";
    maxHp = 100;
    maxMp = 100;
    maxSp = 100;
    hp = BigDecimal.valueOf(100);
    mp = BigDecimal.valueOf(100);
    sp = BigDecimal.valueOf(100);
    speed = BigDecimal.valueOf(1);
    dexterity = 1;
    magic = 1;
    strength = 1;
    weapons.add(new Fists());
    armors.add(new Rags());
    weapon = weapons.get(0);
    armor = armors.get(0);
    inventory.put(Items.ITEM, 2);
  }

  /**
   * Returns a sorted list of items.

   * @return (List)
   */
  public List<Items> getItems() {
    List<Items> list = new ArrayList<>(inventory.keySet());
    Collections.sort(list);
    return list;
  }

  public String getProfession() {
    return profession;
  }

  public int getItemAmount(Items item) {
    return inventory.get(item);
  }

  /**
   * Puts the amount of items into inventory.

   * @param item Items-enum value (Item)
   * @param amount amount of items to put (int)
   */
  public void putInInventory(Items item, int amount) {
    if (inventory.containsKey(item)) {
      inventory.put(item, inventory.get(item) + amount);
    } else {
      inventory.put(item, amount);
    }
  }

  /**
   * Remove one item from inventory.

   * @param item Items-enum value (Item)
   */
  public void removeFromInventory(Items item) {
    if (inventory.get(item) == 1) {
      inventory.remove(item);
    } else {
      inventory.put(item, inventory.get(item) - 1);
    }
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