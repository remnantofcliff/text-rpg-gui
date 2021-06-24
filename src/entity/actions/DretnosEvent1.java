package entity.actions;

import entity.Event;
import main.Game;

/**
 * First action in dretnos. "Explore cave"
 */
public class DretnosEvent1 extends Event {
  public DretnosEvent1() {
    name = "Explore cave";
  }

  @Override
  public void event(Game game) {
    game.clear();
    game.addText("There is a small cave in the corner of the settlement. ");
    game.addText("The opening has old-looking wooden supports. ");
    game.addText("There's an everburning lantern hanging from the ceiling. ");
    game.clear();
    if (game.addText("Do you want to go inside?\n-Yes\n-No") == 0) {
      game.clear();
      game.addText("");
    }
  }
}
