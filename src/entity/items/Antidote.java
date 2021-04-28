package entity.items;

import entity.BattleCharacter;
import entity.Item;
import entity.characters.Player;
import entity.interfaces.Droppable;
import entity.interfaces.Usable;

/**
 * Antidote item. Implements Droppable, Usable.
 */
public class Antidote extends Item implements Droppable, Usable {

  public Antidote() {
    name = "Antidote";
  }

  @Override
  public boolean use(BattleCharacter battleCharacter) {
    return battleCharacter.getStatusEffects().remove("Poisoned");
  }

  @Override
  public void drop(Player player) {
    player.getInventory().remove(this);
  }
}
