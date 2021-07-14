package entity.items;

import entity.Item;
import entity.interfaces.BattleItem;
import entity.interfaces.Droppable;
import entity.player.Player;

/**
 * Antidote item. Implements Droppable, Usable.
 */
public class Antidote extends Item implements BattleItem, Droppable {
  public Antidote() {
    name = "Antidote";
  }

  @Override
  public void use() {
    drop();
    Player.getInstance().getStatusEffects();
  }
}
