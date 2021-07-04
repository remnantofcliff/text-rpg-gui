package entity.items.armors;

import static core.DamageTypes.FIRE;
import static core.DamageTypes.ICE;
import static core.DamageTypes.LIGHTNING;
import static core.DamageTypes.PHYSICAL;
import static core.DamageTypes.POISON;
import static core.DamageTypes.WATER;

import entity.Armor;
import entity.interfaces.Droppable;
import entity.interfaces.Equippable;
import entity.player.Player;
import java.util.Map;

/**
 * Rags-armor.
 */
public class Rags extends Armor implements Droppable, Equippable {
  /**
   * Sets initial values.
   */
  public Rags() {
    name = "Rags";
    flatAbsorption = 0;
    absorptionMap.putAll(Map.of(
        FIRE, 0f,
        ICE, 0.05f,
        LIGHTNING, 0f,
        PHYSICAL, 0.05f,
        POISON, 0f,
        WATER, 0.05f
    ));
  }

  @Override
  public void equip(Player player) {
    player.setArmor(this);
  }

  @Override
  public void drop(Player player) {
    if (player.getArmor() == this) {
      player.setArmor(Naked.getInstance());
    }
    player.getInventory().remove(this);
  }
}
