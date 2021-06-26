package entity;

import main.App;
import main.Game;

/**
 * Battle.
 */
public class Battle extends Event {
  BattleEntity[] enemies;

  public Battle(BattleEntity... enemies) {
    this.enemies = enemies;
  }

  @Override
  public void event(Game game) {
    var player = game.getPlayer();
    game.clear();
    int temp = game.addText(player.getName()
        + "\nHP: " + player.getHp() + " / " + player.getMaxHp()
        + "\nMP: " + player.getMp() + " / " + player.getMaxMp()
        + "\nSP: " + player.getSp() + " / " + player.getMaxSp()
        + "\n-Attack\n-Special\n-Magic\n-Items"
    );
    game.clear();
    switch (temp) {
      case 0:
        var sb = new StringBuilder();
        for (BattleEntity e : enemies) {
          sb.append("-" + e.getName() + " " + e.getHp() + " / " + e.getMaxHp() + "\n");
        }
        game.addText(sb.toString());
        break;
      case 1:
        break;
      case 2:
        break;
      case 3:
        break;
      default: App.logWrongInput(temp);
    }
  }
}
