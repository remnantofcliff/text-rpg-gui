package main;

import entity.Area;
import entity.Entity;
import entity.Event;
import entity.enemies.Player;
import entity.events.NewGame;
import entity.interfaces.Leavable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.stream.Stream;

import window.Display;

/**
 * Game-class handles what gets displayed on the GameArea display.
 */
public class Game extends Thread {
  private Display display = Display.getInstance();
  private Player player;
  private boolean newgame;
  private int input = -1;
  public static final String BACK_NL = "\n-Back";
  
  /**
   * Constructor for game.

   * @param newgame Whether to start a new game or load a game. (boolean)
   */
  public Game(boolean newgame) {
    setName("Game");
    this.newgame = newgame;
  }

  private String getChoosingStrings(Object[] objects, String string) {
    var sb = new StringBuilder(string);
    Stream.of(objects).forEach(x -> sb.append("\n-" + ((Entity) x).getName()));
    return sb.append(BACK_NL).toString();
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
          var sb = new StringBuilder("Where do you want to go next?");
          ArrayList<String> directionList = new ArrayList<>();
          for (String string : l.getDirections()) {
            directionList.add(string);
            sb.append("\n-" + string);
          }
          int index = addText(sb.append(BACK_NL).toString());
          if (index != directionList.size()) {
            nextArea = l.nextLocation(directionList.get(index));
          }
        } else {
          addText("Unable to leave area...");
        }
        break;
      default:App.logWrongInput(temp);
    }
    if (input != -2) {
      area(nextArea);
    }
  }

  private void areaEvent(Area area) {
    if (area.hasEvents()) {
      var sb = new StringBuilder("What do you want to do?");
      Stream.of(area.getEvents()).forEach(x -> sb.append("\n-" + x.getName()));
      int index = addText(sb.append(BACK_NL).toString());
      if (index != area.getEvents().length) {
        area.getEvents()[index].event(this);
      }
    } else {
      addText("No actions available in this location.");
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
        var sb = new StringBuilder(vendor.greeting());
        ArrayList<String> nameList = new ArrayList<>();
        for (Entry<String, Integer> entry : vendor.prices().entrySet()) {
          String key = entry.getKey();
          nameList.add(key);
          sb.append("\n-" + key + ": " + entry.getValue().toString());
        }
        clear();
        index = addText(sb.append(BACK_NL).toString());
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

  private void areaTalk(Area area) {
    clear();
    if (area.hasTalkers()) {
      int index = addText(
          getChoosingStrings(area.getTalkers(), "Who do you want to talk to?")
      );
      if (index != area.getTalkers().length) {
        clear();
        Stream.of(area.getTalkers()[index].getText()).forEach(this::addText);
      }
    } else {
      addText("No one to talk to in this location.");
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
      var file = new File("saves/save.ser");
      try {
        if (file.createNewFile()) {
          App.getMainWindow().serialize();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      new NewGame(this);
      area(player.getArea());
    } else {
      try (var in = new ObjectInputStream(new FileInputStream("saves/save.ser"))) {
        player = (Player) in.readObject();
      } catch (IOException | ClassNotFoundException e) {
        App.logNoSaveFound();
        newgame = true;
        run();
      }
      area(player.getArea());
    }
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