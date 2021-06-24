package main;

import entity.Area;
import entity.Entity;
import entity.Event;
import entity.actions.NewGame;
import entity.characters.Player;
import entity.interfaces.Leavable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import window.Display;

/**
 * Game-class handles what gets displayed on the GameArea display.
 */
public class Game extends Thread {
  private Display display = Display.getInstance();
  private Player player;
  private boolean newgame;
  private static final String BACK = "\n-Back";
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

  private void area(Area area) {
    player.setArea(area);
    clear();
    int temp = addText(
        area.getName()
        + " | "
        + area.getLocation()
        + "\n-Action\n-Description\n-Talk\n-Shop\n-Leave"
    );
    var nextArea = area;
    clear();
    switch (temp) {
      case 0:areaEvent(area);
        break;
      case 1:addText(area.getDescription());
        break;
      case 2:areaTalk(area);
        break;
      case 3:areaShop(area);
        break;
      case 4: if (area instanceof Leavable l) {
          var stringBuilder = new StringBuilder("Where do you want to go next?");
          ArrayList<String> directionList = new ArrayList<>();
          for (String string : l.getDirections()) {
            directionList.add(string);
            stringBuilder.append("\n-" + string);
          }
          int index = addText(stringBuilder.append(BACK).toString());
          if (index != directionList.size()) {
            nextArea = l.nextLocation(directionList.get(index));
          }
        } else {
          addText("Unable to leave area...");
        }
        break;
      default:App.LOGGER.log(Level.SEVERE, "Wrong input in area: {0}", temp);
        break;
    }
    if (input != -2) {
      area(nextArea);
    }
  }

  private void areaEvent(Area area) {
    if (area.hasEvents()) {
      var stringBuilder = new StringBuilder("What do you want to do?");
      for (Event action : area.getEvents()) {
        stringBuilder.append("\n-" + action.getName());
      }
      int index = addText(stringBuilder.append(BACK).toString());
      if (index != area.getEvents().length) {
        area.getEvents()[index].event(this);
      }
    } else {
      addText("No actions available in this location.");
    }
  }

  private void areaTalk(Area area) {
    clear();
    if (area.hasTalkers()) {
      int index = addText(
          getChoosingStrings(area.getTalkers(), "Who do you want to talk to?")
      );
      if (index != area.getTalkers().length) {
        clear();
        for (String string : area.getTalkers()[index].getText()) {
          addText(string);
        }
      }
    } else {
      addText("No one to talk to in this location.");
    }
  }

  private void areaShop(Area area) {
    clear();
    if (area.hasVendors()) {
      int index = addText(
          getChoosingStrings(area.getVendors(), "Who would you like to barter with?")
      );
      if (index != area.getVendors().length) {
        var vendor = area.getVendors()[index];
        var stringBuilder = new StringBuilder(vendor.greeting());
        ArrayList<String> nameList = new ArrayList<>();
        for (Entry<String, Integer> entry : vendor.prices().entrySet()) {
          String key = entry.getKey();
          nameList.add(key);
          stringBuilder.append("\n-" + key + ": " + entry.getValue().toString());
        }
        clear();
        index = addText(stringBuilder.append(BACK).toString());
        if (index != nameList.size()) {
          var selectedString = nameList.get(index);
          var inventory = player.getInventory();
          int itemPrice = vendor.prices().get(selectedString);
          clear();
          if (inventory.hasGold(itemPrice)) {
            var item = vendor.getItem(selectedString);
            inventory.removeGold(itemPrice);
            inventory.add(item);
            addText("Bought one " + item.getName());
          } else {
            addText("Not enough gold.");
          }
        }
      }
    } else {
      addText("No vendors in this location.");
    }
  }

  private String getChoosingStrings(Object[] objects, String string) {
    var stringBuilder = new StringBuilder(string);
    for (Object o : objects) {
      stringBuilder.append("\n-" + ((Entity) o).getName());
    }
    return stringBuilder.append(BACK).toString();
  }

  /**
   * Change name -method.
   */
  public void changeName() {
    while (input != -2) {
      String newName = 
          JOptionPane.showInputDialog(App.getMainWindow(), "What is your name?", player.getName());
      if (newName != null) {
        player.setName(newName);
      }
      addText("Your name is " + player.getName() + ".");
      if (addText("\nIs this correct?\n-Yes\n-No") == 0) {
        break;
      }
      clear();
    }
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
      new NewGame(this);
      area(player.getArea());
    } else {
      try (var in = new ObjectInputStream(new FileInputStream("tmp/save.ser"))) {
        player = (Player) in.readObject();
      } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
        newgame = true;
        run();
      }
      area(player.getArea());
    }
  }

  /**
   * Select difficulty.
   */
  public void selectDifficulty() {
    clear();
    if (addText("Select difficulty:\n-Normal\n-Critical") == 1) {
      player.setDifficulty(Difficulties.CRITICAL);
    }
  }
  
  public synchronized Player getPlayer() {
    return player;
  }

  public synchronized void setInput(int input) {
    this.input = input;
  }
}