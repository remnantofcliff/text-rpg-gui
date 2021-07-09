package entity.events;

import static entity.events.EventConstants.BACK;
import static entity.events.EventConstants.BACK_NL;

import entity.Area;
import entity.Entity;
import entity.Event;
import entity.interfaces.Droppable;
import entity.interfaces.Equippable;
import entity.interfaces.Leavable;
import entity.interfaces.Usable;
import entity.player.Player;
import inventory.Inventory;
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
  private Player player;

  /**
   * Calls the event method with the specified area.

   * @param area (Area)
   * @param game (Game)
   */
  public AreaEvent(Area area, Game game) {
    name = "Area";
    this.area = area;
    player = game.getPlayer();
    event(game);
  }

  private String getChoosingStrings(Object[] objects, String string) {
    var sb = new StringBuilder(string);
    Stream.of(objects).forEach(x -> sb.append("\n-" + ((Entity) x).getName()));
    return sb.append(BACK_NL).toString();
  }

  @Override
  public void event(Game game) {
    player.setArea(area);
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
    game.clear();
    var inventory = player.getInventory();
    switch (game.addText("Choose category:\n-Items\n-Weapons\n-Armor\n-Back")) {
      case 0:invCategory(game, inventory, inventory.getItems(), "Items");
        break;
      case 1:invCategory(game, inventory, inventory.getWeapons(), "Weapons");
        break;
      case 2:invCategory(game, inventory, inventory.getArmors(), "Armors");
        break;
      default:
    }
  }

  private void invCategory(Game game, Inventory inventory, Map<String, Integer> itemMap, String catString) {
    var sb = new StringBuilder();
    game.clear();
    game.setOverlay(catString + " -- Gold: " + inventory.getGold());
    sb.append("\n");
    ArrayList<Entry<String, Integer>> entries = new ArrayList<>(itemMap.entrySet());
    entries.stream().forEach(x -> sb.append("-" + x.getKey() + " -- " + x.getValue() + "\n"));
    int index = game.addText(sb.append(BACK).toString());
    game.removeOverlay();
    if (index != entries.size()) {
      var item = inventory.getItem(entries.get(index).getKey());
      sb.setLength(0);
      sb.append(item.getName() + "\n");
      ArrayList<Runnable> list = new ArrayList<>(3);
      if (item instanceof Usable u) {
        sb.append("-Use\n");
        list.add(() -> u.use(player));
      }
      if (item instanceof Equippable e) {
        sb.append("-Equip\n");
        list.add(() -> e.equip(player));
      }
      if (item instanceof Droppable d) {
        sb.append("-Drop\n");
        list.add(() -> d.drop(player));
      }
      game.clear();
      index = game.addText(sb.append(BACK).toString());
      if (index != list.size()) {
        list.get(index).run();
      } else {
        invCategory(game, inventory, itemMap, catString);
      }
    } else {
      inventory(game);
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
          var inventory = player.getInventory();
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