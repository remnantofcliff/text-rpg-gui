package main;

import entity.Area;
import entity.areas.Dretnos;
import entity.characters.Player;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import window.GameArea;

/**
 * Game-class handles what gets displayed on the GameArea display.
 */
public class Game extends Thread {
  private GameArea display;
  private Player player;
  private boolean newgame;
  private int input = -1;
  
  public Game(GameArea gameArea, boolean newgame) {
    this.newgame = newgame;
    display = gameArea;
  }

  private int waitForInput() {
    //Input is the selected option number. 
    //1, 2, 3... if option selected
    //0 if no option to choose
    //-1 does nothing
    //-2 stops thread
    while (input == -1) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        interrupt();
        App.LOGGER.log(
            Level.INFO,
            "Thread interrupted.\n      Closing thread: {0}",
            getClass().getName()
        );
        input = -2;
        return input;
      }
    }
    int temp = input;
    input = -1;
    return temp;
  }

  private void newGame() {
    player = new Player();
    addText("You wake up...");
    addText("The last thing you remember is that you were captured by the guards of Nilium.");
    addText("Your memory otherwise feels hazy.");
    clear();
    addText("You are in a dark room.");
    addText("You can't really see anything.");
    clear();
    addText("It's cold and damp in the room.");
    addText("You are wearing rags that you don't recognize.");
    addText("None of your possessions are on you.");
    addText("There is a small amount of light coming through one part of the door.");
    clear();
    addText("As you get closer to the light,");
    addText("you realize that it is shining through the decrepit doorframes.");
    addText("You open the door and see an outline of a person.");
    clear();
    addText("The person begins talking:");
    addText("\"Oh, you woke up already..?");
    addText("I've been keeping watch on you while you were out.");
    addText("You are just like the others...");
    addText("Thrown in here, and left to die...\"");
    clear();
    addText("\"Be careful out there\"");
    addText(
        "\n\"There's nothing worse than seeing the people I save just die a gruelsome death..,"
    );
    addText("as many have...\"");
    clear();
    addText("The man lights up a candle.");
    addText("You can now see his face.");
    addText("It is scarred and he has a stoic expression.");
    clear();
    addText("\"You need to leave.");
    addText("I don't have anything else for you.\"");
    clear();
    addText("You can't quite tell how old the man is due to the scars and damage he's suffered.");
    addText("He shows you the way to the door that leads you to the outside.");
    clear();
    addText("He opens the door and says in a calming manner:");
    addText("\"There might be no getting out.");
    addText("Many have tried,");
    addText("and just as many have failed.\"");
    clear();
    addText(
        "\"This place is where criminals, fugitives, and heretics of Nilium are left to die.\""
    );
    clear();
    addText("You see a large cave out of the door.");
    addText("The place is lit up by torches and you see a few people standing around.");
    clear();
    addText("\"This is our little community here in the western corner of Dereliquerat.");
    addText("All these people are too stubborn to die.");
    addText("However,");
    addText("we can't sustain any more people living here.");
    addText("Crops don't do too well down here...\", he finishes with a smirk.");
    clear();
    addText("\"See ya.\", He says with a smile and, in one swift motion,");
    addText("waves goodbye,");
    addText("turns around,");
    addText("and closes the door.");
    clear();
    addText("You can't remember your name...");
    addText("How should you introduce yourself?");
    changeName();
    area(new Dretnos());
  }

  private void area(Area area) {
    clear();
    addText(
        area.getName() 
        + " | " + area.getLocation() 
        + "\n-Action\n-Description\n-Talk\n-Shop\n-Leave"
    );
  }

  private void changeName() {
    while (input != -2) {
      clear();
      String newName = 
          JOptionPane.showInputDialog(App.getMainWindow(), "What is your name?", player.getName());
      if (newName != null) {
        player.setName(newName);
      }
      addText("Your name is " + player.getName() + ".");
      display.setTextState(TextStates.CHOOSING, 2);
      if (addText("\nIs this correct?\n-Yes\n-No") == 1) {
        break;
      }
    }
  }

  private int addText(String text) {
    if (input == -2) {
      return -2;
    }
    display.addText(text);
    return waitForInput();
  }

  private void clear() {
    if (input == -2) {
      return;
    }
    display.clear();
  }

  @Override
  public void run() {
    if (newgame) {
      newGame();
    } else {

    }
  }
  
  public synchronized Player getPlayer() {
    return player;
  }

  public synchronized void setInput(int input) {
    this.input = input;
  }

  /**
   * Enum for the type of text being displayed.
   */
  public enum TextStates {
    CHOOSING, TEXT
  }
}