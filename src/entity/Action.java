package entity;

import entity.characters.Player;
import main.Game;

/**
 * Action abstract class. Contains event-abstract method.
 */
public abstract class Action extends Entity {
  public abstract void event(Game game);
}