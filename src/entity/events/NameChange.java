package entity.events;

import entity.Event;
import entity.player.Player;
import javax.swing.JOptionPane;
import main.App;
import main.Game;

/**
 * Event for changing the player character name.
 */
public class NameChange extends Event {
  public NameChange() {
    name = "Name Change";
  }

  @Override
  public void event(Game game) {
    var player = Player.getInstance();
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
