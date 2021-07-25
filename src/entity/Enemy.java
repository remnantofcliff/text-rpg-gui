package entity;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import entity.interfaces.Stunnable;

/**
 * Enemy-class, subclass of BattleEntity.
 */
public abstract class Enemy extends BattleEntity {
  protected Enemy spawnOnDeath;
  protected static final LinkedHashMap<Float, Item> dropTable = new LinkedHashMap<>();
  
  /**
   * Returns a drop from the enemy's droptable given an appropriate float number. Can return null.

   * @param f (float)
   * @return (Item)
   */
  public Item getDrop(float f) {
    Iterator<Entry<Float, Item>> i = dropTable.entrySet().iterator();
    while (i.hasNext()) {
      Entry<Float, Item> entry = i.next();
      float temp = entry.getKey();
      if (f < temp) {
        return entry.getValue();
      }
    }
    return null;
  }

  public Enemy getSpawnOnDeath() {
    return spawnOnDeath;
  }

  public boolean shouldAttack(int i, List<Enemy> enemies) {
    return !getStatusEffects().contains(Stunnable.STUNNED) && !chooseAbility(i, enemies);
  }

  public boolean shouldSpawnOnDeath() {
    return spawnOnDeath != null && hp == 0;
  }

  public void setSpawnOnDeath(Enemy spawnOnDeath) {
    this.spawnOnDeath = spawnOnDeath;
  }

  public abstract boolean chooseAbility(int userIndex, List<Enemy> enemies);
}