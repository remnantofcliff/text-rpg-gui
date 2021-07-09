package entity.events;

import static entity.events.EventConstants.BACK;
import static entity.events.EventConstants.RANDOM;
import static utilities.Utilities.round;

import entity.Ability;
import entity.Enemy;
import entity.Event;
import entity.Special;
import entity.interfaces.Stunnable;
import entity.player.Player;
import java.util.Objects;
import java.util.stream.Stream;
import javax.swing.SwingUtilities;
import main.App;
import main.Game;

/**
 * Battle.
 */
public class Battle extends Event {
  private Enemy[] enemies;
  private int turns = 1;

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
    Stream.of(enemies).forEach(Enemy::regenerateSp);
    player.regenerateSp();
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
      case 1:ability(game, player, player.getSpecials());
        break;
      case 2:ability(game, player, player.getSpells());
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
      Stream.of(enemies).map(x -> x.getDrop(RANDOM.nextFloat())).filter(Objects::nonNull).forEach(x -> {
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
    Stream.of(enemies).filter(x -> !x.getStatusEffects().contains(Stunnable.STUNNED)).filter(x -> !x.chooseAbility(game)).forEach(x -> {
      float damage = round(player.getArmor().absorb(x.getWeapon().calculateDamage(x), x.getWeapon().getDamageTypeMap()));
      player.removeHp(damage);
      game.addText(x.getName() + " dealt " + damage + " damage to you!\n");
    });
  }

  private void ability(Game game, Player player, Ability[] abilities) {
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
      enemyAttack(game, player);
    } else {
      game.addText("Not enough " + stat + ": " + resource + " / " + abilities[index].getResourceCost() + " required.");
    }
  }
}
