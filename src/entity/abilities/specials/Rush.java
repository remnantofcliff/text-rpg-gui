package entity.abilities.specials;

import static utilities.Utilities.round;

import entity.BattleEntity;
import entity.Enemy;
import entity.Special;
import entity.events.EventConstants;
import entity.interfaces.Stunnable;
import entity.player.Player;
import java.util.stream.Stream;
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
  public void use(Game game, int userIndex, Enemy[] enemies) {
    BattleEntity user;
    if (userIndex == EventConstants.PLAYER_INDEX) {
      user = Player.getInstance();
      user.removeSp(resourceCost);
      game.clear();
      var sb = new StringBuilder("Choose target:\n");
      Stream.of(enemies).forEach(x -> sb.append("-" + x.getName() + "\n"));
      int index = game.addText(sb.toString());
      float damage = 25f + user.getStrength() * 2;
      damage = round(enemies[index].getArmor().absorb(damage, user.getWeapon().getDamageTypeMap()));
      enemies[index].removeHp(damage);
      game.clear();
      String name = enemies[index].getName();
      game.addText("Dealt " + damage + " to " + name + ".");
      if (enemies[index] instanceof Stunnable s) {
        s.stun();
        game.clear();
        game.addText("Stunned " + name + ".");
      }
    } else {
      var player = Player.getInstance();
      float damage = 20;
      user = enemies[userIndex];
      damage = round(player.getArmor().absorb(damage, user.getWeapon().getDamageTypeMap()));
      player.removeHp(damage);
      game.addText(user.getName() + " used " + getName() + ".\n");
      game.addText("You were dealt " + damage + ".");
    }
  }
}
