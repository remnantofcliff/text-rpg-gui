package entity.interfaces;

import entity.Item;
import java.io.Serializable;
import java.util.Map;

/**
 * For npc vendors. talk() to bring up greeting, prices() for Map of items and prices.
 */
public interface Vendor extends Serializable {
  String greeting();

  Map<String, Integer> prices();

  Item getItem(String name);
}
