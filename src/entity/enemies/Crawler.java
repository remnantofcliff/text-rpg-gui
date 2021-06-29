package entity.enemies;

import entity.Enemy;
import entity.items.CrawlerScale;
import entity.items.armors.CrawlerScales;
import entity.items.weapons.CrawlerBite;

/**
 * Crawler-enemy.
 */
public class Crawler extends Enemy {
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
    speed = 0.5;
    dexterity = 1;
    magic = 0;
    strength = 200;
    weapon = new CrawlerBite();
    armor = new CrawlerScales();
    dropTable.put(0.2f, new CrawlerScale());
  }
}
