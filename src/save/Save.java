package save;

import java.io.Serializable;

/**
 * Save-file contains variables that contain information about what the player has done in the world.
 */
public class Save implements Serializable {
  private boolean dretnosExploredCave = false;

  public boolean dretnosExploredCave() {
    return dretnosExploredCave;
  }

  public void setDretnosExploredCave(boolean b) {
    dretnosExploredCave = b;
  }
}
