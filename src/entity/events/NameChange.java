package entity.events;

import entity.Event;
import javax.swing.JOptionPane;
import main.App;
import main.Game;

/**
 * Event for changing the player character name.
 */
public class NameChange extends Event {
  private NameChange() {
    name = "Name Change";
  }

  public NameChange(Game game) {
    this();
    event(game);
  }

  @Override
  public void event(Game game) {
    var player = game.getPlayer();
    while (game.getInput() != -2) {
      String newName = JOptionPane.showInputDialog(App.getMainWindow(), "What is your name?", player.getName());
      if (newName != null) {
        player.setName(newName);
      }
      game.addText("Your name is " + player.getName() + ".");
      if (game.addText("\nIs this correct?\n-Yes\n-No") == 0) {
        break;
      }
      game.clear();
    }
  }
}
