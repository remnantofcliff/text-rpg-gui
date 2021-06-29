package entity.items;

import entity.Item;
import entity.enemies.Player;
import entity.interfaces.Droppable;
import utilities.Utilities;

/**
 * Class for crawler scale -items.
 */
public class CrawlerScale extends Item implements Droppable {
  public CrawlerScale() {
    name = "Crawler scale";
  }

  @Override
  public void drop(Player player) {
    Utilities.dropItem(player, this);
  }
}
