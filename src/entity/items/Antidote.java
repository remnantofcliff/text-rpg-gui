package entity.items;

import entity.BattleCharacter;
import entity.Item;
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
  public void use(BattleCharacter battleCharacter) {
    drop(battleCharacter);
  }

  @Override
  public void drop(BattleCharacter battleCharacter) {
    battleCharacter.getInventory().remove(this);
  }
}
