package entity;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import main.Game;

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

  public void setSpawnOnDeath(Enemy spawnOnDeath) {
    this.spawnOnDeath = spawnOnDeath;
  }

  public abstract boolean chooseAbility(Game game, int userIndex, List<Enemy> enemies);
}