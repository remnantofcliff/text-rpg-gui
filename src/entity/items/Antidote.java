package entity.items;

import entity.BattleEntity;
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
  public void use(BattleEntity battleCharacter) {
    drop(battleCharacter);
  }

  @Override
  public void drop(BattleEntity battleCharacter) {
    battleCharacter.getInventory().remove(this);
  }
}
