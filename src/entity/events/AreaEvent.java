package entity.events;

import static entity.events.EventConstants.BACK;
import static entity.events.EventConstants.BACK_NL;

import entity.Area;
import entity.Entity;
import entity.Event;
import entity.Item;
import entity.interfaces.Droppable;
import entity.interfaces.Equippable;
import entity.interfaces.Leavable;
import entity.interfaces.Usable;
import entity.interfaces.Vendor;
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
  private Player player = Player.getInstance();
  private transient Game game = Game.getInstance();

  /**
   * Calls the event method with the specified area.

   * @param area (Area)
   */
  public AreaEvent(Area area) {
    name = "Area";
    this.area = area;
  }

  private String getChoosingStrings(Object[] objects, String string) {
    var sb = new StringBuilder(string);
    Stream.of(objects).forEach(x -> sb.append("\n-" + ((Entity) x).getName()));
    return sb.append(BACK_NL).toString();
  }

  @Override
  public void event() {
    player.setAreaId(area.getId());
    game.clear();
    int temp = game.addText(area.getName() + " | " + area.getLocation() + "\n-Action\n-Description\n-Talk\n-Shop\n-Inventory\n-Leave");
    var nextArea = area;
    game.clear();
    switch (temp) {
      case 0:areaEvent();
        break;
      case 1:game.addText(area.getDescription());
        break;
      case 2:areaTalk();
        break;
      case 3:areaShop();
        break;
      case 4:inventory();
        break;
      case 5: if (area instanceof Leavable l) {
          var sb = new StringBuilder("Where do you want to go next?");
          ArrayList<String> directionList = new ArrayList<>(4);
          for (String string : l.getDirections()) {
            directionList.add(string);
            sb.append("\n-" + string);
          }
          int index = game.addText(sb.append(BACK_NL).toString());
          if (index != directionList.size() || index != -2) {
            nextArea = l.nextLocation(directionList.get(index));
          }
        } else {
          game.addText("Unable to leave area...");
        }
        break;
      default:App.logWrongInput(temp);
    }
    if (game.getInput() != -2) {
      new AreaEvent(nextArea).event();
    }
  }

  private void inventory() {
    game.clear();
    var inventory = player.getInventory();
    switch (game.addText("Choose category:\n-Items\n-Weapons\n-Armor\n-Choose items to keep with you.\n-Back")) {
      case 0:invCategory(inventory, inventory.getItems(), "Items");
        break;
      case 1:invCategory(inventory, inventory.getWeapons(), "Weapons");
        break;
      case 2:invCategory(inventory, inventory.getArmors(), "Armors");
        break;
      case 3:quickItems(inventory);
        break;
      default:
    }
  }

  private void quickItems(Inventory inventory) {
    Item[] quickItems = player.getQuickItems();
    game.clear();
    int qiIndex = game.addText("Add item to quick items:"
      + "\n-Item 1: " + (quickItems[0] == null ? "" : quickItems[0].getName())
      + "\n-Item 2: " + (quickItems[1] == null ? "" : quickItems[1].getName())
      + "\n-Item 3: " + (quickItems[2] == null ? "" : quickItems[2].getName())
      + BACK_NL
    );
    if (qiIndex != 3) {
      game.clear();
      var sb = new StringBuilder();
      ArrayList<Entry<String, Integer>> entries = new ArrayList<>(inventory.getBattleItems().entrySet());
      int index = displayInvCategory(entries, sb);
      if (index != entries.size() && index != -2) {
        quickItems[qiIndex] = inventory.getItem(entries.get(index).getKey());
      }
    }
  }

  private int displayInvCategory(ArrayList<Entry<String, Integer>> entries, StringBuilder sb) {
    entries.stream().forEach(x -> sb.append("-" + x.getKey() + " -- " + x.getValue() + "\n"));
    return game.addText(sb.append(BACK).toString());
  }

  private void invCategory(Inventory inventory, Map<String, Integer> itemMap, String catString) {
    var sb = new StringBuilder();
    game.clear();
    game.setOverlay(catString + " -- Gold: " + inventory.getGold());
    sb.append("\n");
    ArrayList<Entry<String, Integer>> entries = new ArrayList<>(itemMap.entrySet());
    int index = displayInvCategory(entries, sb);
    game.removeOverlay();
    if (index != entries.size() || index != -2) {
      var item = inventory.getItem(entries.get(index).getKey());
      sb.setLength(0);
      sb.append(item.getName() + "\n");
      ArrayList<Runnable> list = new ArrayList<>(3);
      if (item instanceof Usable u) {
        sb.append("-Use\n");
        list.add(u::use);
      }
      if (item instanceof Equippable e) {
        sb.append("-Equip\n");
        list.add(e::equip);
      }
      if (item instanceof Droppable d) {
        sb.append("-Drop\n");
        list.add(d::drop);
      }
      game.clear();
      index = game.addText(sb.append(BACK).toString());
      if (index != list.size() && index != -2) {
        list.get(index).run();
      } else {
        invCategory(inventory, itemMap, catString);
      }
    } else {
      inventory();
    }
  }

  private void areaEvent() {
    if (area.hasEvents()) {
      var sb = new StringBuilder("What do you want to do?");
      Stream.of(area.getEvents()).forEach(x -> sb.append("\n-" + x.getName()));
      int index = game.addText(sb.append(BACK_NL).toString());
      if (index != area.getEvents().length && index != -2) {
        area.getEvents()[index].event();
      }
    } else {
      game.addText("No actions available in this location.");
    }
  }

  private void areaShop() {
    game.clear();
    if (area.hasVendors()) {
      int index = game.addText(getChoosingStrings(area.getVendors(), "Who would you like to barter with?"));
      if (index != area.getVendors().length && index != -2) {
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
        if (index != nameList.size() && index != -2) {
          var selectedString = nameList.get(index);
          var inventory = player.getInventory();
          int itemPrice = vendor.prices().get(selectedString);
          game.clear();
          goldCheck(vendor, selectedString, inventory, itemPrice);
        }
      }
    } else {
      game.addText("No vendors in this location.");
    }
  }

  private void goldCheck(Vendor vendor, String selectedString, Inventory inventory, int itemPrice) {
    if (inventory.hasGold(itemPrice)) {
      var item = vendor.getItem(selectedString);
      inventory.removeGold(itemPrice);
      inventory.add(item);
      game.addText("Bought one " + item.getName());
    } else {
      game.addText("Not enough gold.");
    }
  }

  private void areaTalk() {
    game.clear();
    if (area.hasTalkers()) {
      int index = game.addText(getChoosingStrings(area.getTalkers(), "Who do you want to talk to?"));
      if (index != area.getTalkers().length && index != -2) {
        game.clear();
        Stream.of(area.getTalkers()[index].getText()).forEach(game::addText);
      }
    } else {
      game.addText("No one to talk to in this location.");
    }
  }
}
