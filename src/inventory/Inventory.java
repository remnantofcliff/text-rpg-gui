package inventory;

import entity.Armor;
import entity.Item;
import entity.Weapon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import main.App;

/**
 * Player's inventory.
 */
public class Inventory {
  List<Item> container = new ArrayList<>();

  public Inventory() {
    //
  }

  private Map<String, Integer> getType(Class<?> classToGet) {
    Map<String, Integer> tempMap = new TreeMap<>();
    for (Item item : container) {
      if (item.getClass().getSuperclass() == classToGet) {
        if (tempMap.keySet().contains(item.getName())) {
          tempMap.replace(item.getName(), tempMap.get(item.getName()) + 1);
        } else {
          tempMap.put(item.getName(), 1);
        }
      }
    }
    return tempMap;
  }

  public Map<String, Integer> getArmors() {
    return getType(Armor.class);
  }

  public Map<String, Integer> getWeapons() {
    return getType(Weapon.class);
  }

  public Map<String, Integer> getItems() {
    return getType(Item.class);
  }

  public void add(Item item) {
    container.add(item);
    Collections.sort(container);
  }

  public void remove(Item item) {
    container.remove(item);
  }

  /**
   * Returns an item in the inventory with the given name.

   * @param name (String)
   * @return (Item)
   */
  public Item getItem(String name) {
    for (Item item : container) {
      if (item.getName().equals(name)) {
        return item;
      }
    }
    App.LOGGER.log(Level.SEVERE, "Item name not in inventory: {0}", name);
    return null;
  }
}