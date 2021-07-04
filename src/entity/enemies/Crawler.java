package entity.enemies;

import entity.Enemy;
import entity.abilities.specials.Rush;
import entity.interfaces.Stunnable;
import entity.items.CrawlerScale;
import entity.items.armors.CrawlerScales;
import entity.items.weapons.CrawlerBite;
import main.Game;

/**
 * Crawler-enemy.
 */
public class Crawler extends Enemy implements Stunnable {
  /**
   * Constructor sets the initial values.
   */
  public Crawler() {
    setDefaultValues();
  }

  @Override
  public void setDefaultValues() {
    name = "Crawler";
    maxHp = 50;
    maxMp = 0;
    maxSp = 0;
    hp = 50;
    mp = 0;
    sp = 0;
    dexterity = 1;
    magic = 0;
    strength = 1;
    weapon = new CrawlerBite();
    armor = new CrawlerScales();
    specials.add(new Rush());
    dropTable.put(0.2f, new CrawlerScale());
  }

  @Override
  public void chooseAttack(Game game) {
    if (game.getPlayer().getHp() < 50 && sp > specials.get(0).getResourceCost()) {
      specials.get(0).use(this, game, game.getPlayer());
    }
  }

  @Override
  public void stun() {
    statusEffects.add(STUNNED);
  }
}
