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
 * Leather armor.
 */
public class LeatherArmor extends Armor implements Droppable, Equippable {
  /**
   * Sets the initial values for leather armor.
   */
  public LeatherArmor() {
    name = "Leather Armor";
    flatAbsorption = 5;
    absorptionMap.putAll(Map.of(
      FIRE, 0.1f,
      ICE, 0.4f,
      LIGHTNING, 0f,
      PHYSICAL, 0.15f,
      POISON, 0f,
      WATER, 0.2f
    ));
  }

  @Override
  public void equip() {
    Player.getInstance().setArmor(this);
  }

  @Override
  public void drop() {
    var player = Player.getInstance();
    if (player.getArmor() == this) {
      player.setArmor(Naked.getInstance());
    }
    player.getInventory().remove(this);
  }
}
