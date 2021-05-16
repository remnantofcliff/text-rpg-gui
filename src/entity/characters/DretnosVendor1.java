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
  public String[] greeting() {
    return new String[] { "I sell everything I can scavenge from the fields.." };
  }

  @Override
  public Map<Item, Integer> prices() {
    return Map.of(new HealthPotion(), 10, new Dagger(), 15, new Club(), 20);
  }
}