package entity.events;

import static entity.events.EventConstants.BACK;
import static entity.events.EventConstants.BACK_NL;
import static entity.events.EventConstants.EMPTY;
import static entity.events.EventConstants.RANDOM;
import static utilities.Utilities.round;

import entity.Ability;
import entity.BattleEntity;
import entity.Enemy;
import entity.Event;
import entity.Item;
import entity.Special;
import entity.interfaces.Stunnable;
import entity.interfaces.Usable;
import entity.player.Player;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;
import main.App;
import main.Game;
import window.Display;

/**
 * Battle.
 */
public class Battle extends Event {
  private Enemy[] enemies;
  private ArrayList<BattleEntity> usedSpecial = new ArrayList<>();
  private Player player;

  /**
   * Creates a new battle event. Takes in the enemies as a parameter.

   * @param enemies enemies to be fought. Can be an arbitary number or array.
   */
  public Battle(Enemy... enemies) {
    name = "Battle";
    this.enemies = enemies;
    player = Player.getInstance();
  }

  @Override
  public void event(Game game) {
    newTurn();
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
      case 0:attack(game);
        break;
      case 1:ability(game, player.getSpecials());
        break;
      case 2:ability(game, player.getSpells());
        break;
      case 3:item(game);
        break;
      default:App.logWrongInput(temp);
    }
    if (player.getHp() == 0) {
      game.clear();
      game.addText("You were defeated");
      game.addText(".");
      game.addText(".");
      game.addText(".");
      Display.getInstance().exit(false);
      return;
    }
    if (Stream.of(enemies).allMatch(x -> x.getHp() == 0)) {
      game.clear();
      game.addText("Victory!");
      Stream.of(enemies).map(x -> x.getDrop(RANDOM.nextFloat())).filter(Objects::nonNull).forEach(x -> {
        player.getInventory().add(x);
        game.addText("Obtained: " + x.getName());
      });
      player.setToMaxSp();
      return;
    }
    if (game.getInput() != -2) {
      event(game);
    }
  }

  private void item(Game game) {
    game.clear();
    Item[] quickItems = player.getQuickItems();
    int index = game.addText("Choose item to use:"
        + "\n-" + (quickItems[0] == null ? EMPTY :  quickItems[0].getName())
        + "\n-" + (quickItems[1] == null ? EMPTY :  quickItems[1].getName())
        + "\n-" + (quickItems[2] == null ? EMPTY :  quickItems[2].getName())
        + BACK_NL
    );
    if (index != 3 && quickItems[index] != null) {
      ((Usable) quickItems[index]).use();
      quickItems[index] = null;
      enemyAttack(game);
    }
  }

  private void newTurn() {
    Stream.of(enemies).filter(x -> !usedSpecial.contains(x)).forEach(Enemy::regenerateSp);
    if (!usedSpecial.contains(player)) {
      player.regenerateSp();
    }
    usedSpecial.clear();
  }

  private void attack(Game game) {
    var sb = new StringBuilder("Choose who to attack:\n");
    Stream.of(enemies).forEach(x -> sb.append("-" + x.getName() + " " + x.getHp() + " / " + x.getMaxHp() + "\n"));
    int index = game.addText(sb.append(BACK).toString());
    if (index == enemies.length) {
      return;
    }
    float damage = round(enemies[index].getArmor().absorb(player.getWeapon().calculateDamage(player), player.getWeapon().getDamageTypeMap()));
    enemies[index].removeHp(damage);
    game.clear();
    game.addText("Dealt " + damage + " damage to " + enemies[index].getName());
    game.clear();
    enemyAttack(game);
  }

  private void enemyAttack(Game game) {
    Stream.of(enemies).filter(x -> !x.getStatusEffects().contains(Stunnable.STUNNED)).filter(x -> !x.chooseAbility(game)).forEach(x -> {
      float damage = round(player.getArmor().absorb(x.getWeapon().calculateDamage(x), x.getWeapon().getDamageTypeMap()));
      player.removeHp(damage);
      game.addText(x.getName() + " dealt " + damage + " damage to you!\n");
    });
  }

  private void ability(Game game, Ability[] abilities) {
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
    if (index == abilities.length) {
      return;
    }
    game.clear();
    if (abilities[index].getResourceCost() <= resource) {
      abilities[index].use(player, game, enemies);
      enemyAttack(game);
      usedSpecial.add(player);
    } else {
      game.addText("Not enough " + stat + ": " + resource + " / " + abilities[index].getResourceCost() + " required.");
    }
  }
}
