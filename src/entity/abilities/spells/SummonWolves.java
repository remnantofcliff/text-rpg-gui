package entity.abilities.spells;

import entity.Enemy;
import entity.Spell;
import entity.enemies.Wolf;
import entity.events.EventConstants;
import java.util.Collections;
import java.util.List;
import main.Game;

/**
 * Spell that summons wolves to the battlefield.
 */
public class SummonWolves extends Spell {
  public SummonWolves() {
    name = "Summon wolves";
    resourceCost = 60;
  }

  @Override
  public void use(Game game, int userIndex, List<Enemy> enemies) {
    if (userIndex == EventConstants.PLAYER_INDEX) {
      //
    } else {
      game.addText(enemies.get(userIndex).getName() + " used " + name);
      enemies.addAll(List.of(new Wolf(), new Wolf()));
      Collections.shuffle(enemies);
    }
  }
}