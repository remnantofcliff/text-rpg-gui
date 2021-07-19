package entity.events;

import entity.Event;
import entity.enemies.Dris;
import entity.player.Player;
import main.Game;
import save.Save;

/**
 * First action in dretnos. "Explore cave"
 */
public class DretnosEvent1 extends Event {
  private Save save = Player.getInstance().getSave();

  /**
   * First event in Dretnos. "Explore cave" / "Visit the gnome in the cave" / "Visit old shack"
   */
  public DretnosEvent1() {
    if (save.dretnosExploredCave()) {
      name = "Visit the gnome in the cave";
    } else {
      name = "Explore cave";
    }
  }

  @Override
  public void event(Game game) {
    game.clear();
    game.addText("There is a small cave in the corner of the settlement. ");
    game.addText("The opening has old-looking wooden supports. ");
    game.addText("There's an lantern hanging from the ceiling. ");
    game.clear();
    if (game.addText("Do you want to go inside?\n-Yes\n-No") == 0) {
      game.clear();
      if (game.addText("Inspect the lantern?\n-Yes\n-No") == 0) {
        game.clear();
        game.addText("The lantern is magical and is stuck in the ceiling of the cavern. ");
        game.addText("You wonder how it ended up here. ");
      }
      game.addText("Continuing into the cavern, you don't notice anything unusual. ");
      game.addText("The light from the lantern is fading slowly as you continue. ");
      game.addText("Suddenly, around a corner, there is a bright light at the end of the tunnel. ");
      game.addText("As you continue, you notice that there is a green field of grass, illuminated by the light. ");
      game.addText("In the middle of the small field, there is a small house. ");
      game.clear();
      if (game.addText("Continue?\n-Yes\n-No") == 0) {
        afterCave(game);
      } else {
        leave(game);
      }
    } else {
      leave(game);
    }
  }

  private void afterCave(Game game) {
    game.clear();
    game.addText("You carefully try the door.. ");
    game.addText("as you do the door opens and a small man greets you. ");
    game.addText("He is wearing a green robe and has a bald, round head. ");
    game.clear();
    game.addText("\"What are you doing here? ");
    game.addText("You people are not allowed here!\" ");
    game.addText("\n\"Need I remind you what happened the last time you people came here\" ");
    if (game.addText("\n-Yes\n-No") == 0) {
      game.clear();
      game.addText("Well you get what you ask for! ");
      new Battle(new Dris()).event(game);
    } else {
      save.setDretnosExploredCave(true);
      setName("Visit the gnome in the cave");
      game.addText("\"Sigh.. ");
      game.addText("Just leave please. ");
      game.addText("And don't tell the other townsfolk about me.. ");
      game.addText("");
    }
  }

  private void leave(Game game) {
    game.clear();
    game.addText("You decide to leave");
  }
}
