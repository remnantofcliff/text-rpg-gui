package entity.events;

import entity.Enemy;
import entity.Event;
import entity.enemies.Player;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;
import main.App;
import main.Game;

/**
 * Battle.
 */
public class Battle extends Event {
  private Enemy[] enemies;
  private Random random = new Random();

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
      case 1:
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
      App.getMainWindow().menuScreen();
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
    event(game);
  }

  private void attack(Game game, Player player) {
    var sb = new StringBuilder();
    Stream.of(enemies).forEach(x -> sb.append("-" + x.getName() + " " + x.getHp() + " / " + x.getMaxHp() + "\n"));
    int temp = game.addText(sb.append("-Back").toString());
    double damage = enemies[temp].getArmor().absorb(player.getWeapon().calculateDamage(player), false);
    enemies[temp].removeHp(damage);
    game.clear();
    game.addText("Dealt " + damage + " damage to " + enemies[temp].getName());
    game.clear();
    for (Enemy e : enemies) {
      damage = player.getArmor().absorb(e.getWeapon().calculateDamage(e), false);
      player.removeHp(damage);
      game.addText(e.getName() + " dealt " + damage + " damage to you!\n");
    }
  }
}
