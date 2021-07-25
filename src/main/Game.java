package main;

import entity.Area;
import entity.events.AreaEvent;
import entity.events.NewGame;
import entity.player.Player;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import window.Display;

/**
 * Game-class handles what gets displayed on the GameArea display.
 */
public class Game extends Thread {
  private Display display = Display.getInstance();
  private Player player;
  private boolean newgame;
  private int input = -1;
  private static Game instance;
  
  
  /**
   * Constructor for game.

   * @param newgame Whether to start a new game or load a game. (boolean)
   */
  private Game(boolean newgame) {
    setName("Game");
    this.newgame = newgame;
  }

  private int waitForInput() {
    while (input == -1) {
      try {
        Thread.sleep(16, 666666);
      } catch (InterruptedException e) {
        interrupt();
        App.logThreadExit(getName());
        input = -2;
        return input;
      }
    }
    int temp = input;
    input = -1;
    return temp;
  }

  /**
   * Adds text to the display and waits for input.

   * @param text Text to be added (String)
   * @return The input which is the selectable line number, starting from 0 (int)
   */
  public int addText(String text) {
    if (input == -2) {
      return -2;
    }
    display.addText(text);
    return waitForInput();
  }

  /**
   * Clears the display.
   */
  public void clear() {
    if (input == -2) {
      return;
    }
    display.clear();
  }

  @Override
  public void run() {
    App.logThreadStart();
    if (newgame) {
      player = Player.getInstance();
      var file = new File("saves/save.ser");
      try {
        if (file.createNewFile()) {
          App.getMainWindow().serialize();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      new NewGame().event();
      new AreaEvent(Area.loadArea(player.getAreaId())).event();
    } else {
      try (var in = new ObjectInputStream(new FileInputStream("saves/save.ser"))) {
        player = (Player) in.readObject();
      } catch (IOException | ClassNotFoundException e) {
        App.logNoSaveFound();
        newgame = true;
        run();
      }
      new AreaEvent(Area.loadArea(player.getAreaId())).event();
    }
  }

  /**
   * Removes the overlay.
   */
  public void removeOverlay() {
    display.setOverlay(false, "");
  }

  /**
   * Sets a black overlay on top of the display that contains text.

   * @param overlayText The text to be added (String)
   */
  public void setOverlay(String overlayText) {
    display.setOverlay(true, overlayText);
  }

  /**
   * Use to close the instance of the game.
   */
  public static void closeInstance() {
    instance = null;
  }

  /**
   * Returns singleton instance. Starts new game if instance is null.

   * @return (Game)
   */
  public static Game getInstance() {
    return getInstance(true);
  }

  /**
   * Returns singleton instance. Creates new instance if instance is null.

   * @param newgame (boolean)
   * @return (Game)
   */
  public static Game getInstance(boolean newgame) {
    if (instance == null) {
      App.logNewGameInstance();
      instance = new Game(newgame);
    }
    return instance;
  }

  /**
   * Returns the value of the input variable, which is based on the selection from the display.

   * @return 0 is the default and first option, 1 is second, 2 is third etc. -1 does nothing and -2 is signal to close thread (int)
   */
  public synchronized int getInput() {
    return input;
  }

  /**
   * Sets the input variable, which selection gets made in the game, as well as continues the game.

   * @param input 0 is the default and first option, 1 is second, 2 is third etc. -1 does nothing and -2 is signal to close thread (int)
   */
  public synchronized void setInput(int input) {
    this.input = input;
  }
}