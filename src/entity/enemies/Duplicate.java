package entity.enemies;

import entity.Enemy;
import java.util.List;
import main.Game;

/**
 * Duplicate made by the "Duplicate Deception"-spell.
 */
public class Duplicate extends Enemy {
  private Enemy enemy;

  public Duplicate(Enemy enemy) {
    this.enemy = enemy;
    setDefaultValues();
  }

  @Override
  public boolean chooseAbility(Game game, int userIndex, List<Enemy> enemies) {
    return false;
  }

  @Override
  public void removeHp(float num) {
    hp = 0;
  }

  @Override
  public void setDefaultValues() {
    name = enemy.getName();
    weapon = enemy.getWeapon();
    armor = enemy.getArmor();
    maxHp = enemy.getMaxHp();
    maxMp = enemy.getMaxMp();
    maxSp = enemy.getMaxSp();
    hp = enemy.getHp();
    mp = enemy.getMp();
    sp = enemy.getSp();
    setStats(enemy.getDexterity(), enemy.getMagic(), enemy.getStrength());
  }
}
