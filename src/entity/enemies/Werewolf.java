package entity.enemies;

import static core.DamageTypes.FIRE;
import static core.DamageTypes.ICE;
import static core.DamageTypes.LIGHTNING;
import static core.DamageTypes.PHYSICAL;
import static core.DamageTypes.POISON;
import static core.DamageTypes.WATER;

import core.DamageTypes;
import entity.Armor;
import entity.Enemy;
import entity.Weapon;
import entity.abilities.spells.SummonWolves;
import entity.interfaces.Poisonable;
import entity.interfaces.Stunnable;
import java.util.List;
import java.util.Map;
import main.Game;

/**
 * Werewolf-enemy.
 */
public class Werewolf extends Enemy implements Poisonable, Stunnable {
  private boolean summonedWolves;

  public Werewolf() {
    setDefaultValues();
  }

  private class WerewolfClaw extends Weapon {
    private WerewolfClaw() {
      name = "Claw";
      baseDamage = 15;
      dexterityModifier = 2;
      magicModifier = 0;
      strengthModifier = 3;
      damageTypeMap.put(DamageTypes.PHYSICAL, 1f);
    }
  }

  private class WerewolfFur extends Armor {
    private WerewolfFur() {
      name = "Fur";
      flatAbsorption = 1;
      absorptionMap.putAll(Map.of(
        FIRE, 0f,
        ICE, 0.3f,
        LIGHTNING, 0.2f,
        PHYSICAL, 0.1f,
        POISON, 0f,
        WATER, 0.5f
      ));
    }
  }

  @Override
  public boolean chooseAbility(Game game, int userIndex, List<Enemy> enemies) {
    var spell = spells.get(0);
    if (!summonedWolves && mp >= spell.getResourceCost()) {
      summonedWolves = true;
      spell.use(game, userIndex, enemies);
      return true;
    }
    return false;
  }

  @Override
  public void setDefaultValues() {
    name = "Werewolf";
    weapon = new WerewolfClaw();
    armor = new WerewolfFur();
    spells.add(new SummonWolves());
    setMaxResources(200, 100, 5);
    setStats(4, 1, 4);
    summonedWolves = false;
  }
  
}
