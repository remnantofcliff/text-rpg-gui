package main;

import entity.characters.Player;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import utilities.FileHandler;
import window.GameArea;

/**
 * Game-class handles what gets displayed on the GameArea display.
 */
public class Game extends Thread {
  private GameArea display;
  private int input = -1;
  private Player player;
  
  public Game(GameArea gameArea) {
    display = gameArea;
  }

  private String replaceAddresses(String string) {
    while (string.contains("@")) {
      int index = string.indexOf("@") + 1;
      String subString = string.substring(index, string.indexOf("@", index));
      switch (subString) {
        case "player_name":
          string = string.replace("@player_name@", player.getName());
          break;
        case "profession_description":
          String tempString = "";
          switch (player.getProfession()) {
            case "Soldier":
              tempString = "foot soldier";
              break;
            case "Thief":
              tempString = "lowly thief";
              break;
            case "Mage":
              tempString = "scholarly mage";
              break;
            default:
              break;
          }
          string = string.replace("@profession_description@", tempString);
          break;
        default:
          App.LOGGER.log(Level.WARNING, "Invalid address: {0}", subString);
          break;
      }
    }
    return string;
  }

  private int waitForInput() {
    //Input is the selected option number. 
    //0 if no option to choose
    //0 < if option selected
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
            getClass().getName());
        input = -2;
        return -2;
      }
    }
    int temp = input;
    input = -1;
    return temp;
  }

  private void event(String[] text) {
    TextStates mode = TextStates.TEXT;
    int i = 0;
    int startingLine = 0;
    for (String string : text) {
      if (string.startsWith("@")) {
        mode = TextStates.valueOf(string.substring(1, string.indexOf(" ")));
        i = 0;
        startingLine = Character.getNumericValue(string.charAt(string.length() - 1)) - 1;
      } else {
        string = replaceAddresses(string);
        switch (mode) {
          case CHOOSING:
            textToDisplay(i, startingLine, mode, string);
            i++;
            break;
          case TEXT:
            textToDisplay(i, startingLine, mode, string);
            if (waitForInput() == -2) {
              return;
            }
            i++;
            break;
          default:
            App.LOGGER.log(Level.WARNING, "Invalid mode in current event: {0}", mode);
            break;
        }
      }
    }
  }

  private void newGame() {
    player = new Player();
    event(FileHandler.readText("data/text/new_game1.txt"));
    while (input != -2) {
      String name;
      name = JOptionPane.showInputDialog(App.getMainWindow(), "Give me your name.", "Johnny");
      if (name != null) {
        player.setName(name);
      }
      event(FileHandler.readText("data/text/new_game2.txt"));
      if (waitForInput() == 1) {
        break;
      }
    }
    event(FileHandler.readText("data/text/new_game3.txt"));
    switch (waitForInput()) {
      case 1:
        player.incrementStrength();
        player.setProfession("Soldier");
        break;
      case 2:
        player.incrementDexterity();
        player.setProfession("Thief");
        break;
      case 3:
        player.incrementMagic();
        player.setProfession("Mage");
        break;
      default:
        return;
    }
    event(FileHandler.readText("data/text/new_game4.txt"));
  }

  private void textToDisplay(int i, int startingLine, TextStates mode, String string) {
    if (i == 0) {
      display.setText(string, mode, startingLine);
    } else {
      display.addText(string);
    }
  }

  @Override
  public void run() {
    newGame();
  }
  
  public synchronized Player getPlayer() {
    return player;
  }

  public synchronized void setInput(int input) {
    this.input = input;
  }
}
