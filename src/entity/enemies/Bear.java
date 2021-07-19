package entity.enemies;

import static core.DamageTypes.FIRE;
import static core.DamageTypes.ICE;
import static core.DamageTypes.LIGHTNING;
import static core.DamageTypes.PHYSICAL;
import static core.DamageTypes.POISON;
import static core.DamageTypes.WATER;
import static utilities.Utilities.round;

import core.DamageTypes;
import entity.Armor;
import entity.Enemy;
import entity.Special;
import entity.Weapon;
import entity.abilities.specials.Rush;
import entity.interfaces.Poisonable;
import entity.interfaces.Stunnable;
import entity.player.Player;
import java.util.List;
import java.util.Map;
import main.Game;

/**
 * Bear-enemy.
 */
public class Bear extends Enemy implements Poisonable, Stunnable {
  public Bear() {
    setDefaultValues();
  }
  
  private class BearBite extends Special {
    private BearBite() {
      name = "Bite";
      resourceCost = 5;
    }

    @Override
    public void use(Game game, int userIndex, List<Enemy> enemies) {
      game.addText("The bear bites you!\n");
      var player = Player.getInstance();
      float damage = round(player.getArmor().absorb(20, weapon.getDamageTypeMap()));
      player.removeHp(damage);
      game.addText("You were dealt " + damage + ".");
    }
  }

  private class BearClaw extends Weapon {
    private BearClaw() {
      name = "Claw";
      baseDamage = 8;
      dexterityModifier = 0;
      magicModifier = 0;
      strengthModifier = 2;
      damageTypeMap.put(DamageTypes.PHYSICAL, 1f);
    }
  }

  private class BearFur extends Armor {
    private BearFur() {
      name = "Leather Armor";
      flatAbsorption = 5;
      absorptionMap.putAll(Map.of(
        FIRE, 0f,
        ICE, 0.25f,
        LIGHTNING, 0f,
        PHYSICAL, 0.05f,
        POISON, 0f,
        WATER, 0.3f
      ));
    }
  }

  @Override
  public boolean chooseAbility(Game game, int userIndex, List<Enemy> enemies) {
    if (sp >= specials.get(0).getResourceCost() && hp > 50) {
      specials.get(0).use(game, userIndex, enemies);
      return true;
    } else if (sp >= specials.get(1).getResourceCost() && hp < 50) {
      specials.get(1).use(game, userIndex, enemies);
      return true;
    }
    return false;
  }

  @Override
  public void setDefaultValues() {
    name = "Bear";
    weapon = new BearClaw();
    armor = new BearFur();
    specials.addAll(List.of(new BearBite(), new Rush()));
    setMaxResources(200, 0, 8);
    setStats(0, 0, 4);
  }
}
