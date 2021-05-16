package entity.interfaces;

import entity.Area;

/**
 * Interface for game-areas that you can leave.
 */
public interface Leavable {
  String[] getDirections();

  Area nextLocation(String direction);
}
