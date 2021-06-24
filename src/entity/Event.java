package entity;

import main.Game;

/**
 * Action abstract class. Contains event-abstract method.
 */
public abstract class Event extends Entity {
  public abstract void event(Game game);
}