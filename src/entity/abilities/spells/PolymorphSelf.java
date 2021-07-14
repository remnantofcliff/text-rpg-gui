package entity.abilities.spells;

import entity.BattleEntity;
import entity.Spell;
import entity.player.Player;
import main.Game;

/**
 * "Polymorph self"-spell.
 */
public class PolymorphSelf extends Spell {

  public PolymorphSelf() {
    name = "Polymorph self";
    resourceCost = 60;
  }

  @Override
  public void use(BattleEntity user, Game game, BattleEntity... targets) {
    if (user instanceof Player) {

    } else {
      
    }
  }
  
}
