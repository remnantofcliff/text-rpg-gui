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
  
  
  /**
   * Constructor for game.

   * @param newgame Whether to start a new game or load a game. (boolean)
   */
  public Game(boolean newgame) {
    setName("Game");
    this.newgame = newgame;
  }

  private int waitForInput() {
    //Input is the selected option number. 
    //0, 1, 2, 3... if option selected
    //-1 does nothing
    //-2 stops thread
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
   * @return The input (int)
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
    if (newgame) {
      player = new Player();
      var file = new File("saves/save.ser");
      try {
        if (file.createNewFile()) {
          App.getMainWindow().serialize();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      player.setArea(Area.loadArea(player.getAreaId()));
      new NewGame(this);
      new AreaEvent(player.getArea(), this);
    } else {
      try (var in = new ObjectInputStream(new FileInputStream("saves/save.ser"))) {
        player = (Player) in.readObject();
      } catch (IOException | ClassNotFoundException e) {
        App.logNoSaveFound();
        newgame = true;
        run();
      }
      player.setArea(Area.loadArea(player.getAreaId()));
      new AreaEvent(player.getArea(), this);
    }
  }

  public void removeOverlay() {
    display.setOverlay(false);
  }

  public void setOverlay(String overlayText) {
    display.setOverlay(true, overlayText);
  }

  public synchronized Player getPlayer() {
    return player;
  }

  public synchronized int getInput() {
    return input;
  }

  public synchronized void setInput(int input) {
    this.input = input;
  }
}