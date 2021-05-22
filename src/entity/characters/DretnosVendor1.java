package entity.characters;

import entity.Entity;
import entity.Item;
import entity.interfaces.Vendor;
import entity.items.HealthPotion;
import entity.items.weapons.Club;
import entity.items.weapons.Dagger;
import java.util.Map;

/**
 * Dretnos-character. Shop owner.
 */
public class DretnosVendor1 extends Entity implements Vendor {
  public DretnosVendor1() {
    name = "Dialys";
    
  }

  @Override
  public String greeting() {
    return "\"I sell everything I can scavenge from the fields..\"";
  }

  @Override
  public Map<String, Integer> prices() {
    return Map.of("Health Potion", 10, "Dagger", 15, "Club", 20);
  }

  @Override
  public Item getItem(String name) {
    switch (name) {
      case"Health Potion": return new HealthPotion();
      case"Dagger": return new Dagger();
      case"Club": return new Club();
      default: return null;
    }
  }
}