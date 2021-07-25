package save;

import java.io.Serializable;

/**
 * Save-file contains variables that contain information about what the player has done in the world.
 */
public class Save implements Serializable {
  private boolean dretnosExploredCave = false;

  /**
   * Has the player gotten to the shack in the "Explored cave"-event, or "DretnosEvent1".

   * @return (boolean)
   */
  public boolean dretnosExploredCave() {
    return dretnosExploredCave;
  }

  /**
   * Changes the value of a variable controlling whether the player has gotten to the shack in "DretnosEvent1".

   * @param b (boolean)
   */
  public void setDretnosExploredCave(boolean b) {
    dretnosExploredCave = b;
  }
}
