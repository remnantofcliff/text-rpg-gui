package entity.abilities.spells;

import entity.Enemy;
import entity.Spell;
import entity.enemies.Bear;
import entity.enemies.GiantSpider;
import entity.enemies.Werewolf;
import entity.events.EventConstants;
import java.util.List;
import main.Game;

/**
 * "Polymorph self"-spell.
 */
public class PolymorphSelf extends Spell {
  public PolymorphSelf() {
    name = "Polymorph self";
    resourceCost = 130;
  }

  @Override
  public void use(Game game, int userIndex, List<Enemy> enemies) {
    if (userIndex == EventConstants.PLAYER_INDEX) {
      //
    } else {
      var enemy = enemies.get(userIndex);
      game.addText(enemy.getName() + " used " + name);
      Enemy temp = switch (EventConstants.RANDOM.nextInt(3)) { 
        case 0 -> new Bear();
        case 1 -> new GiantSpider();
        case 2 -> new Werewolf();
        default -> new Bear();
      };
      temp.setSpawnOnDeath(enemy);
      enemies.set(userIndex, temp);
    }
  }
}
