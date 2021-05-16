package entity.interfaces;

import entity.Item;
import java.util.Map;

/**
 * For npc vendors. talk() to bring up greeting, prices() for Map of items and prices.
 */
public interface Vendor {
  String[] greeting();

  Map<Item, Integer> prices();
}
