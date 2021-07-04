package entity;

import java.util.Iterator;
import java.util.LinkedHashMap;
import main.Game;

/**
 * Enemy-class, subclass of BattleEntity.
 */
public abstract class Enemy extends BattleEntity {
  protected static final LinkedHashMap<Float, Item> dropTable = new LinkedHashMap<>();
  
  /**
   * Returns a drop from the enemy's droptable given an appropriate float number. Can return null.

   * @param f (float)
   * @return (Item)
   */
  public Item getDrop(float f) {
    Iterator<Float> i = dropTable.keySet().iterator();
    while (i.hasNext()) {
      float temp = i.next();
      if (f < temp) {
        return dropTable.get(temp);
      }
    }
    return null;
  }

  public abstract void chooseAttack(Game game);
}