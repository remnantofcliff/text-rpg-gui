package entity.characters;

import entity.Entity;
import entity.Item;
import entity.interfaces.Vendor;
import entity.items.armors.LeatherArmor;
import entity.items.armors.Robes;
import java.util.Map;

/**
 * Vendor 2 for Dretnos.
 */
public class DretnosVendor2 extends Entity implements Vendor {
  public DretnosVendor2() {
    name = "Helin";
  }

  @Override
  public String greeting() {
    return "\"You should take a look at my wares. I craft them all by hand.\"";
  }

  @Override
  public Map<String, Integer> prices() {
    return Map.of("Robes", 25, "Leather Armor", 50);
  }

  @Override
  public Item getItem(String name) {
    switch (name) {
      case"Robes": return new Robes();
      case"Leather Armor": return new LeatherArmor();
      default: return null;
    }
  }
}
