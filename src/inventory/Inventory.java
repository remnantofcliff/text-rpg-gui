package inventory;

import entity.Armor;
import entity.Item;
import entity.Weapon;
import entity.interfaces.BattleItem;
import entity.items.CustomItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;

/**
 * Player's inventory.
 */
public class Inventory implements Serializable {
  private ArrayList<Item> container = new ArrayList<>();
  private int gold;

  private Map<String, Integer> generateMap(Predicate<? super Item> filter) {
    Map<String, Integer> tempMap = new TreeMap<>();
    container.stream().filter(filter).forEach(x -> putItemInMap(tempMap, x));
    return tempMap;
  }

  private Map<String, Integer> getType(Class<?> classToGet) {
    return generateMap(x -> x.getClass().getSuperclass() == classToGet);
  }

  private void putItemInMap(Map<String, Integer> tempMap, Item x) {
    if (tempMap.keySet().contains(x.getName())) {
      tempMap.replace(x.getName(), tempMap.get(x.getName()) + 1);
    } else {
      tempMap.put(x.getName(), 1);
    }
  }

  public Item getItem(String name) {
    return container.stream().filter(x -> x.compareTo(new CustomItem(name)) == 0).findAny().orElse(null);
  }

  public Item[] array() {
    return container.toArray(new Item[0]);
  }

  public Map<String, Integer> getArmors() {
    return getType(Armor.class);
  }
  
  public Map<String, Integer> getBattleItems() {
    return generateMap(BattleItem.class::isInstance);
  }
  
  public Map<String, Integer> getItems() {
    return getType(Item.class);
  }
  
  public Map<String, Integer> getWeapons() {
    return getType(Weapon.class);
  }

  public boolean hasGold(int gold) {
    return this.gold >= gold;
  }

  public int getGold() {
    return gold;
  }

  public void add(Item item) {
    container.add(item);
  }

  public void addGold(int gold) {
    this.gold += gold;
  }

  public void remove(Item item) {
    container.remove(item);
  }

  public void removeGold(int gold) {
    this.gold -= gold;
  }
}