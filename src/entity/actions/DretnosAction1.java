package entity.actions;

import entity.Action;
import main.Game;

/**
 * First action in dretnos. "Explore cave"
 */
public class DretnosAction1 extends Action {
  public DretnosAction1() {
    name = "Explore cave";
  }

  @Override
  public void event(Game game) {
    game.clear();
    game.addText("There is a small cave in the corner of the cavern. ");
    game.addText("The opening has old-looking wooden supports. ");
    game.addText("There's an everburning lantern hanging from the ceiling. ");
    game.clear();
    game.addText("Do you want to go inside?\n-Yes\n-No");
  }
}
