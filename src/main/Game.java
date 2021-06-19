package main;

import entity.Action;
import entity.Area;
import entity.Entity;
import entity.areas.Dretnos;
import entity.characters.Player;
import entity.interfaces.Leavable;
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
        Thread.sleep(6, 944444);
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
    addText("You wake up... ");
    addText("The last thing you remember is that you were captured by the guards of Nilium. ");
    addText("Your memory otherwise feels hazy.");
    clear();
    addText("You are in a dark room. ");
    addText("You can't really see anything.");
    clear();
    addText("It's cold and damp in the room. ");
    addText("You are wearing rags that you don't recognize. ");
    addText("None of your possessions are on you. ");
    addText("There is a small amount of light coming through one part of the door.");
    clear();
    addText("As you get closer to the light, ");
    addText("you realize that it is shining through the decrepit doorframes. ");
    addText("You open the door and see an outline of a person.");
    clear();
    addText("The person begins talking: ");
    addText("\"Oh, you woke up already..? ");
    addText("I've been keeping watch on you while you were out. ");
    addText("You are just like the others... ");
    addText("Thrown in here, and left to die...\"");
    clear();
    addText("\"Be careful out there\" ");
    addText(
        """
        
        There's nothing worse than seeing the people I save just die a gruelsome death.., 
        """
    );
    addText("as many have...\"");
    clear();
    addText("The man lights up a candle. ");
    addText("You can now see his face. ");
    addText("It is scarred and he has a stoic expression.");
    clear();
    addText("\"You need to leave. ");
    addText("I don't have anything else for you.\"");
    clear();
    addText("You can't quite tell how old the man is due to the scars and damage he's suffered. ");
    addText("He shows you the way to the door that leads you to the outside.");
    clear();
    addText("He opens the door and says in a calming manner: ");
    addText("\"There might be no getting out. ");
    addText("Many have tried, ");
    addText("and just as many have failed.\"");
    clear();
    addText(
        """
        "This place is where criminals, fugitives, and heretics of Nilium are left to die." 
        """
    );
    clear();
    addText("You see a large cave out of the door. ");
    addText("The place is lit up by torches and you see a few people standing around.");
    clear();
    addText("\"This is our little community here in the western corner of Dereliquerat. ");
    addText("All these people are too stubborn to die. ");
    addText("However, ");
    addText("we can't sustain any more people living here. ");
    addText("Crops don't do too well down here...\", he finishes with a smirk.");
    clear();
    addText("\"See ya.\", He says with a smile and, in one swift motion, ");
    addText("waves goodbye, ");
    addText("turns around, ");
    addText("and closes the door.");
    clear();
    addText("You can't remember your name... ");
    addText("How should you introduce yourself? ");
    changeName();
    area(new Dretnos());
  }

  private void area(Area area) {
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
      case 0: if (area.hasActions()) {
          var stringBuilder = new StringBuilder("What do you want to do?");
          for (Action action : area.getActions()) {
            stringBuilder.append("\n-" + action.getName());
          }
          area.getActions()[addText(stringBuilder.toString())].event(this);
        } else {
          addText("No actions available in this location.");
        }
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
          nextArea = l.nextLocation(directionList.get(addText(stringBuilder.toString())));
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

  private void areaTalk(Area area) {
    clear();
    if (area.hasTalkers()) {
      int index = addText(
          getChoosingStrings(area.getTalkers(), "Who do you want to talk to?")
      );
      clear();
      for (String string : area.getTalkers()[index].getText()) {
        addText(string);
      }
    } else {
      addText("No one to talk to in this location.");
    }
  }

  private void areaShop(Area area) {
    clear();
    if (area.hasVendors()) {
      var vendor = area.getVendors()[
          addText(getChoosingStrings(area.getVendors(), "Who would you like to barter with?"))
          ];
      var stringBuilder = new StringBuilder(vendor.greeting());
      ArrayList<String> nameList = new ArrayList<>();
      for (Entry<String, Integer> entry : vendor.prices().entrySet()) {
        String key = entry.getKey();
        nameList.add(key);
        stringBuilder.append("\n-" + key + ": " + entry.getValue().toString());
      }
      clear();
      var selectedString = nameList.get(addText(stringBuilder.toString()));
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
    } else {
      addText("No vendors in this location.");
    }
  }

  private String getChoosingStrings(Object[] objects, String string) {
    var stringBuilder = new StringBuilder(string);
    for (Object vendor : objects) {
      stringBuilder.append("\n-" + ((Entity) vendor).getName());
    }
    return stringBuilder.toString();
  }

  private void changeName() {
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
      newGame();
    } else {
      //
    }
  }
  
  public synchronized Player getPlayer() {
    return player;
  }

  public synchronized void setInput(int input) {
    this.input = input;
  }
}