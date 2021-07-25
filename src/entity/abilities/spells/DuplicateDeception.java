package entity.abilities.spells;

import entity.Enemy;
import entity.Spell;
import entity.enemies.Duplicate;
import entity.events.EventConstants;
import main.Game;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Spell that summons multiple copies of caster. When a copy is attacked, it disappears, and when the caster is attacked, all copies disappear.
 */
public class DuplicateDeception extends Spell {
  public DuplicateDeception() {
    name = "Duplicate deception";
    resourceCost = 60;
  }

  @Override
  public void use(int userIndex, List<Enemy> enemies) {
    var game = Game.getInstance();
    if (userIndex == EventConstants.PLAYER_INDEX) {
      //
    } else {
      var enemy = enemies.get(userIndex);
      game.addText(enemy.getName() + " used " + name);
      IntStream.range(0, EventConstants.RANDOM.nextInt(4) + 1).forEach(i -> enemies.add(new Duplicate(enemy)));
      Collections.shuffle(enemies);
    }
  }
}
