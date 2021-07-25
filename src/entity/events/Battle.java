package entity.events;

import static entity.events.EventConstants.BACK;
import static entity.events.EventConstants.BACK_NL;
import static entity.events.EventConstants.EMPTY;
import static entity.events.EventConstants.RANDOM;
import static utils.Utilities.round;

import entity.Ability;
import entity.Enemy;
import entity.Event;
import entity.Item;
import entity.Special;
import entity.interfaces.Usable;
import entity.player.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import core.ListItemHolder;
import main.App;
import main.Game;
import window.Display;

/**
 * Battle.
 */
public class Battle extends Event {
  private ArrayList<Enemy> enemies;
  private Player player = Player.getInstance();
  private boolean newTurn = true;
  private int size;
  private transient Game game = Game.getInstance();

  /**
   * Creates a new battle event. Takes in the enemies as a parameter.

   * @param enemies enemies to be fought. Can be an arbitary amount or array. (Enemy...)
   */
  public Battle(Enemy... enemies) {
    name = "Battle";
    this.enemies = new ArrayList<>(List.of(enemies));
  }

  @Override
  public void event() {
    if (newTurn) {
      newTurn();
    }
    game.clear();
    int temp = game.addText(
      player.getName()
      + "\nHP: " + player.getHp() + " / " + player.getMaxHp()
      + "\nMP: " + player.getMp() + " / " + player.getMaxMp()
      + "\nSP: " + player.getSp() + " / " + player.getMaxSp()
      + "\n-Attack\n-Special\n-Magic\n-Items"
    );
    game.clear();
    switch (temp) {
      case 0:attack();
        break;
      case 1:ability(player.getSpecials());
        break;
      case 2:ability(player.getSpells());
        break;
      case 3:item();
        break;
      default:App.logWrongInput(temp);
    }
    IntStream.range(0, size).mapToObj(i -> new ListItemHolder<Enemy>(enemies.get(i), i)).filter(x -> x.getItem().shouldSpawnOnDeath()).forEach(x -> enemies.set(x.getIndex(), x.getItem()));
    if (player.isDead()) {
      game.clear();
      game.addText("You were defeated");
      game.addText(".");
      game.addText(".");
      game.addText(".");
      Display.getInstance().exit(false);
      return;
    }
    if (enemies.stream().allMatch(Enemy::isDead)) {
      game.clear();
      game.addText("Victory!");
      enemies.stream().map(x -> x.getDrop(RANDOM.nextFloat())).filter(Objects::nonNull).forEach(x -> {
        player.getInventory().add(x);
        game.addText("Obtained: " + x.getName());
      });
      player.setToMaxSp();
      return;
    }
    if (game.getInput() != -2) {
      event();
    }
  }

  private void item() {
    game.clear();
    Item[] quickItems = player.getQuickItems();
    int index = game.addText(
      "Choose item to use:"
      + "\n-" + (quickItems[0] == null ? EMPTY :  quickItems[0].getName())
      + "\n-" + (quickItems[1] == null ? EMPTY :  quickItems[1].getName())
      + "\n-" + (quickItems[2] == null ? EMPTY :  quickItems[2].getName())
      + BACK_NL
    );
    if (index != 3 && quickItems[index] != null) {
      ((Usable) quickItems[index]).use();
      quickItems[index] = null;
      enemyAttack();
    }
  }

  private void newTurn() {
    newTurn = false;
    size = enemies.size();
    IntStream.range(0, size).mapToObj(i -> new ListItemHolder<>(enemies.get(i), i)).forEach(x -> {
      if (x.getItem().isDead()) {
        enemies.remove(x.getIndex());
      } else {
        x.getItem().regenerate();
      }
    });
    player.regenerate();
  }

  private void attack() {
    int index = selectEnemies(enemies, "Choose who to attack:");
    if (index == size || index == -2) {
      return;
    }
    var weapon = player.getWeapon();
    var enemy = enemies.get(index);
    float damage = round(enemy.getArmor().absorb(weapon.calculateDamage(player), weapon.getDamageTypeMap()));
    enemy.removeHp(damage);
    game.clear();
    game.addText("Dealt " + damage + " damage to " + enemy.getName());
    enemyAttack();
  }

  private void enemyAttack() {
    game.clear();
    newTurn = true;
    IntStream.range(0, size).mapToObj(i -> new ListItemHolder<Enemy>(enemies.get(i), i)).filter(x -> x.getItem().shouldAttack(x.getIndex(), enemies)).forEach(x -> {
      var weapon = x.getItem().getWeapon();
      float damage = round(player.getArmor().absorb(weapon.calculateDamage(x.getItem()), weapon.getDamageTypeMap()));
      player.removeHp(damage);
      game.addText(x.getItem().getName() + " dealt " + damage + " damage to you!\n");
    });
  }

  private void ability(Ability[] abilities) {
    String choose;
    String stat;
    int resource;
    if (abilities instanceof Special[]) {
      choose = "special ability";
      resource = player.getSp();
      stat = "SP";
    } else {
      choose = "spell";
      resource = player.getMp();
      stat = "MP";
    }
    var sb = new StringBuilder("Choose " + choose + ":\n");
    Stream.of(abilities).forEach(x -> sb.append("-" + x.getName() + " " + x.getResourceCost() + stat + "\n"));
    int index = game.addText(sb.append(BACK).toString());
    if (index == abilities.length || index == -2) {
      return;
    }
    game.clear();
    if (abilities[index].getResourceCost() <= resource) {
      abilities[index].use(EventConstants.PLAYER_INDEX, enemies);
      enemyAttack();
    } else {
      game.addText("Not enough " + stat + ": " + resource + " / " + abilities[index].getResourceCost() + " required.");
    }
  }

  /**
   * Adds the enemies to the screen for selection and returns the selection index.

   * @return (int)
   */
  public static int selectEnemies(List<Enemy> enemies, String initialString) {
    var sb = new StringBuilder(initialString + "\n");
    enemies.forEach(x -> sb.append("-" + x.getName() + " " + x.getHp() + " / " + x.getMaxHp() + "\n"));
    return Game.getInstance().addText(sb.append(BACK).toString());
  }
}
