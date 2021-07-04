package entity.events;

import static utilities.Utilities.round;

import entity.Enemy;
import entity.Event;
import entity.Special;
import entity.player.Player;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;
import javax.swing.SwingUtilities;
import main.App;
import main.Game;

/**
 * Battle.
 */
public class Battle extends Event {
  private Enemy[] enemies;
  private Random random = new Random();
  private int turns = 1;
  private static final String BACK = "-Back";

  /**
   * Creates a new battle event. Takes in the enemies as a parameter.

   * @param enemies enemies to be fought. Can be an arbitary number or array.
   */
  public Battle(Enemy... enemies) {
    name = "Battle";
    this.enemies = enemies;
    Stream.of(enemies).forEach(Enemy::setDefaultValues);
  }

  @Override
  public void event(Game game) {
    var player = game.getPlayer();
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
      case 0:attack(game, player);
        break;
      case 1:special(game, player);
        break;
      case 2:
        break;
      case 3:
        break;
      default:App.logWrongInput(temp);
    }
    if (player.getHp() == 0) {
      game.clear();
      game.addText("You were defeated");
      game.addText(".");
      game.addText(".");
      game.addText(".");
      game.clear();
      game.interrupt();
      SwingUtilities.invokeLater(() -> {
        App.getMainWindow().reset();
        App.getMainWindow().menuScreen();
      });
      return;
    }
    if (Stream.of(enemies).allMatch(x -> x.getHp() == 0)) {
      game.clear();
      game.addText("Victory!");
      Stream.of(enemies).map(x -> x.getDrop(random.nextFloat())).filter(Objects::nonNull).forEach(x -> {
        player.getInventory().add(x);
        game.addText("Obtained: " + x.getName());
      });
      return;
    }
    turns++;
    if (game.getInput() != -2) {
      event(game);
    }
  }

  private void attack(Game game, Player player) {
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
    enemyAttack(game, player);
  }

  private void enemyAttack(Game game, Player player) {
    for (Enemy e : enemies) {
      float damage = round(player.getArmor().absorb(e.getWeapon().calculateDamage(e), e.getWeapon().getDamageTypeMap()));
      player.removeHp(damage);
      game.addText(e.getName() + " dealt " + damage + " damage to you!\n");
    }
  }

  private void special(Game game, Player player) {
    var sb = new StringBuilder();
    Special[] specials = player.getSpecials();
    Stream.of(specials).forEach(x -> sb.append("-" + x.getName() + "  " + x.getResourceCost() + "\n"));
    int index = game.addText(sb.append(BACK).toString());
    if (index == specials.length) {
      return;
    }
    game.clear();
    if (specials[index].getResourceCost() <= player.getSp()) {
      specials[index].use(player, game, enemies);
    } else {
      game.addText("Not enough SP: " + player.getSp() + " / " + specials[index].getResourceCost() + " required.");
    }
  }
}
