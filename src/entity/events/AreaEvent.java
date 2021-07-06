package entity.events;

import entity.Area;
import entity.Entity;
import entity.Event;
import entity.interfaces.Leavable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import main.App;
import main.Game;

/**
 * Generic event for areas.
 */
public class AreaEvent extends Event {
  private Area area;
  private static final String BACK_NL = "\n-Back";

  /**
   * Calls the event method with the specified area.

   * @param area (Area)
   * @param game (Game)
   */
  public AreaEvent(Area area, Game game) {
    name = "Area";
    this.area = area;
    event(game);
  }

  private String getChoosingStrings(Object[] objects, String string) {
    var sb = new StringBuilder(string);
    Stream.of(objects).forEach(x -> sb.append("\n-" + ((Entity) x).getName()));
    return sb.append(BACK_NL).toString();
  }

  @Override
  public void event(Game game) {
    game.getPlayer().setArea(area);
    game.clear();
    int temp = game.addText(area.getName() + " | " + area.getLocation() + "\n-Action\n-Description\n-Talk\n-Shop\n-Inventory\n-Leave");
    var nextArea = area;
    game.clear();
    switch (temp) {
      case 0:areaEvent(game);
        break;
      case 1:game.addText(area.getDescription());
        break;
      case 2:areaTalk(game);
        break;
      case 3:areaShop(game);
        break;
      case 4:inventory(game);
        break;
      case 5: if (area instanceof Leavable l) {
          var sb = new StringBuilder("Where do you want to go next?");
          ArrayList<String> directionList = new ArrayList<>();
          for (String string : l.getDirections()) {
            directionList.add(string);
            sb.append("\n-" + string);
          }
          int index = game.addText(sb.append(BACK_NL).toString());
          if (index != directionList.size()) {
            nextArea = l.nextLocation(directionList.get(index));
          }
        } else {
          game.addText("Unable to leave area...");
        }
        break;
      default:App.logWrongInput(temp);
    }
    if (game.getInput() != -2) {
      new AreaEvent(nextArea, game);
    }
  }

  private void inventory(Game game) {
    var inventory = game.getPlayer().getInventory();
    var sb = new StringBuilder();
    switch (game.addText("Choose category:\n-Items\n-Weapons\n-Armor\n-Back")) {
      case 0:
        game.clear();
        sb.append("Items:\n");
        Map<String, Integer> items = inventory.getItems();
        items.entrySet().stream().forEach(x -> {
          sb.append("-" + x.getKey() + " - " + x.getValue() + "\n");
        });
        game.addText(sb.toString());
        break;
      case 1:
        break;
      case 2:
        break;
      default:
    }
  }

  private void areaEvent(Game game) {
    if (area.hasEvents()) {
      var sb = new StringBuilder("What do you want to do?");
      Stream.of(area.getEvents()).forEach(x -> sb.append("\n-" + x.getName()));
      int index = game.addText(sb.append(BACK_NL).toString());
      if (index != area.getEvents().length) {
        area.getEvents()[index].event(game);
      }
    } else {
      game.addText("No actions available in this location.");
    }
  }

  private void areaShop(Game game) {
    game.clear();
    if (area.hasVendors()) {
      int index = game.addText(getChoosingStrings(area.getVendors(), "Who would you like to barter with?"));
      if (index != area.getVendors().length) {
        var vendor = area.getVendors()[index];
        var sb = new StringBuilder(vendor.greeting());
        ArrayList<String> nameList = new ArrayList<>();
        for (Entry<String, Integer> entry : vendor.prices().entrySet()) {
          String key = entry.getKey();
          nameList.add(key);
          sb.append("\n-" + key + ": " + entry.getValue().toString());
        }
        game.clear();
        index = game.addText(sb.append(BACK_NL).toString());
        if (index != nameList.size()) {
          var selectedString = nameList.get(index);
          var inventory = game.getPlayer().getInventory();
          int itemPrice = vendor.prices().get(selectedString);
          game.clear();
          if (inventory.hasGold(itemPrice)) {
            var item = vendor.getItem(selectedString);
            inventory.removeGold(itemPrice);
            inventory.add(item);
            game.addText("Bought one " + item.getName());
          } else {
            game.addText("Not enough gold.");
          }
        }
      }
    } else {
      game.addText("No vendors in this location.");
    }
  }

  private void areaTalk(Game game) {
    game.clear();
    if (area.hasTalkers()) {
      int index = game.addText(getChoosingStrings(area.getTalkers(), "Who do you want to talk to?"));
      if (index != area.getTalkers().length) {
        game.clear();
        Stream.of(area.getTalkers()[index].getText()).forEach(game::addText);
      }
    } else {
      game.addText("No one to talk to in this location.");
    }
  }
}
