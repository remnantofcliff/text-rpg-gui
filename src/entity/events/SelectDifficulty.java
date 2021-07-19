package entity.events;

import core.Difficulties;
import entity.Event;
import entity.player.Player;
import main.Game;

/**
 * Event for changing the difficulty of the game.
 */
public class SelectDifficulty extends Event {
  public SelectDifficulty() {
    name = "Select Difficulty";
  }

  @Override
  public void event(Game game) {
    game.clear();
    if (game.addText("Select difficulty:\n-Normal\n-Critical") == 1) {
      Player.getInstance().setDifficulty(Difficulties.CRITICAL);
    }
  }
}
