package utilities;

import entity.Item;
import entity.enemies.Player;

/**
 * Contains static methods for various operations.
 */
public class Utilities {
  private Utilities(){}
  
  public static String correctCapitalization(String string) {
    return string.substring(0, 1) + string.substring(1, string.length()).toLowerCase();
  }

  public static void dropItem(Player player, Item item) {
    player.getInventory().remove(item);
  }
}
