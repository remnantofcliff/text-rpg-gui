package entity.abilities.specials;

import static utilities.Utilities.round;

import core.DamageTypes;
import entity.BattleEntity;
import entity.Enemy;
import entity.Special;
import entity.events.Battle;
import entity.events.EventConstants;
import entity.interfaces.Stunnable;
import entity.player.Player;
import java.util.List;
import java.util.Map;
import main.Game;

/**
 * Rush-special ability.
 */
public class Rush extends Special {
  public Rush() {
    name = "Rush";
    resourceCost = 4;
  }

  @Override
  public void use(Game game, int userIndex, List<Enemy> enemies) {
    BattleEntity user;
    if (userIndex == EventConstants.PLAYER_INDEX) {
      user = Player.getInstance();
      user.removeSp(resourceCost);
      game.clear();
      int index = Battle.selectEnemies(game, enemies, "Choose target:");
      if (index == enemies.size() || index == -2) {
        return;
      }
      float damage = 25f + user.getStrength() * 2;
      Enemy target = enemies.get(index);
      damage = round(target.getArmor().absorb(damage, Map.of(DamageTypes.PHYSICAL, 1f)));
      target.removeHp(damage);
      game.clear();
      String name = target.getName();
      game.addText("Dealt " + damage + " to " + name + ".");
      if (target instanceof Stunnable s) {
        s.stun();
        game.clear();
        game.addText("Stunned " + name + ".");
      }
    } else {
      var player = Player.getInstance();
      float damage = 20;
      user = enemies.get(userIndex);
      damage = round(player.getArmor().absorb(damage, Map.of(DamageTypes.PHYSICAL, 1f)));
      player.removeHp(damage);
      game.addText(user.getName() + " used " + getName() + ".\n");
      game.addText("You were dealt " + damage + ".");
    }
  }
}
