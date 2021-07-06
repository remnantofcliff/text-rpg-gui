package entity.abilities.specials;

import static utilities.Utilities.round;

import entity.BattleEntity;
import entity.Special;
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
  public void use(BattleEntity user, Game game, BattleEntity... targets) {
    float damage = 25f + user.getStrength() * 2;
    user.removeSp(resourceCost);
    if (user instanceof Player) {
      game.clear();
      var sb = new StringBuilder("Choose target:\n");
      Stream.of(targets).forEach(x -> sb.append("-" + x.getName() + "\n"));
      int index = game.addText(sb.toString());
      damage = round(targets[index].getArmor().absorb(damage, user.getWeapon().getDamageTypeMap()));
      targets[index].removeHp(damage);
      game.clear();
      String name = targets[index].getName();
      game.addText("Dealt " + damage + " to " + name + ".");
      if (targets[index] instanceof Stunnable s) {
        s.stun();
        game.clear();
        game.addText("Stunned " + name + ".");
      }
    } else {
      damage = round(targets[0].getArmor().absorb(damage, user.getWeapon().getDamageTypeMap()));
      targets[0].removeHp(damage);
      game.addText(user.getName() + " used " + getName() + ".\n");
      game.addText("You were dealt " + damage + ".");
    }
  }
}
