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
  public String[] greeting() {
    return new String[] {"\"You should take a look at my wares.", "I craft them all by hand.\""};
  }

  @Override
  public Map<Item, Integer> prices() {
    return Map.of(new Robes(), 25, new LeatherArmor(), 50);
  }
    
}
