package window;

import entity.characters.Player;
import items.Items;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import main.App;
import main.Game;
import main.GameStates;

/**
 * Game screen where graphics and text gets drawn.
 */
public class GameArea extends JTextArea {
  private DisplayStates displayState = DisplayStates.DEFAULT;
  private GameStates gameState = GameStates.TEXT;
  private int startingLine;
  private int selectedLine;
  private int invStartingIndex = 0;
  private int invSelectedIndex = 0;
  private int invSelectedLine = 0;
  private int invLastIndex = 0;
  private static final Color CHOOSING_RECT_COLOR = new Color(100, 100, 100, 40);
  private static final int LINE_BORDER_WIDTH = 20;
  private static final int EMPTY_BORDER_WIDTH_LEFT = 10;
  private static final int EMPTY_BORDER_WIDTH_RIGHT = 5;
  private static final int CHOOSING_RECT_X = LINE_BORDER_WIDTH + EMPTY_BORDER_WIDTH_LEFT;
  private static final int CHOOSING_RECT_WIDTH_REDUCTION = 
      LINE_BORDER_WIDTH * 2 + EMPTY_BORDER_WIDTH_LEFT + EMPTY_BORDER_WIDTH_RIGHT;

  /**
   * Gameplay area.

   * @param font Font to use. (Font)
   */
  public GameArea(Font font, Color color) {
    setBackground(Color.BLACK);
    setBorder(
      new CompoundBorder(
          new LineBorder(color, LINE_BORDER_WIDTH),
          new EmptyBorder(0, EMPTY_BORDER_WIDTH_LEFT, 0, EMPTY_BORDER_WIDTH_RIGHT
        )
    ));
    setFocusable(false);
    setFont(font);
    setForeground(color);
    setEditable(false);
    setLineWrap(true);
    setWrapStyleWord(true);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    int fontHeight = g.getFontMetrics().getHeight();
    int width = getWidth();
    int choosingRectWidth = width - CHOOSING_RECT_WIDTH_REDUCTION;
    g.setColor(CHOOSING_RECT_COLOR);
    g.fillRect(
        CHOOSING_RECT_X,
        LINE_BORDER_WIDTH + fontHeight * selectedLine,
        choosingRectWidth,
        fontHeight
    );
    if (displayState != DisplayStates.DEFAULT) {
      int height = getHeight();
      int panelX = width / 10;
      int panelY = height / 10;
      int panelWidth = panelX * 7;
      int panelHeight = panelY * 7;
      g.setColor(Color.BLACK);
      g.fillRect(panelX, panelY, panelWidth, panelHeight);
      g.setColor(Color.GREEN);
      g.drawRect(panelX, panelY, panelWidth, panelHeight);
      g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
      ((Graphics2D) g).setRenderingHint(
          RenderingHints.KEY_TEXT_ANTIALIASING,
          RenderingHints.VALUE_TEXT_ANTIALIAS_ON
      );
      FontMetrics fontMetrics = g.getFontMetrics();
      fontHeight = fontMetrics.getHeight();
      int stringX = panelX + fontMetrics.getMaxAdvance();
      int stringY = (int) (panelY + fontHeight * 1.5);
      Player player = App.getMainWindow().getGame().getPlayer();
      switch (displayState) {
        case CHARACTER:
          for (int i = 0; i < 11; i++) {
            int textHeight = stringY + fontHeight * i;
            g.drawString(getPlayerStatString(i), stringX, textHeight);
            g.drawString(getPlayerStat(i, player), stringX + panelWidth / 2, textHeight);
          }
          break;
        case EQUIPMENT:
          g.drawString("Equipment: ", stringX, stringY);
          g.drawString("Weapon: " + player.getWeapon().getName(), stringX, stringY + fontHeight);
          g.drawString(" Armor: " + player.getArmor().getName(), stringX, stringY + fontHeight * 2);
          break;
        case INVENTORY:
          String inventoryString = "Inventory:";
          int underlineHeight = stringY + 8;
          g.drawString(inventoryString, stringX, stringY);
          g.drawLine(
              stringX,
              underlineHeight,
              stringX + g.getFontMetrics().stringWidth(inventoryString),
              underlineHeight
          );
          List<Items> items = player.getItems();
          invLastIndex = items.size() - 1;
          for (int i = invStartingIndex; i < invStartingIndex + 10; i++) {
            Items item = items.get(i);
            int textHeight = stringY + fontHeight * (i + 1 - invStartingIndex);
            g.drawString(item.toString(), stringX, textHeight);
            g.drawString(
                Integer.toString(player.getItemAmount(item)),
                stringX + panelWidth / 2,
                textHeight
            );
          }
          g.setColor(CHOOSING_RECT_COLOR);
          g.fillRect(
              stringX,
              stringY + 10 + invSelectedLine * fontHeight,
              panelWidth - fontMetrics.getMaxAdvance(),
              fontHeight
          );
          break;
        case MAP:
          break;
        default:
          break;
      }
    }
  }

  public DisplayStates getDisplayState() {
    return displayState;
  }

  private String getPlayerStat(int i, Player player) {
    switch (i) {
      case 0:
        return player.getName();
      case 2:
        return player.getHp() + " / " + player.getMaxHp();
      case 3:
        return player.getMp() + " / " + player.getMaxMp();
      case 4:
        return player.getSp() + " / " + player.getMaxSp();
      case 6:
        return Integer.toString(player.getDexterity());
      case 7:
        return Integer.toString(player.getMagic());
      case 8:
        return Integer.toString(player.getStrength());
      case 10:
        return player.getSpeed().toString();
      default:
        return "";
    }
  }

  private String getPlayerStatString(int i) {
    switch (i) {
      case 0:
        return "Name:";
      case 2:
        return "Hit Points:";
      case 3:
        return "Mana Points:";
      case 4:
        return "Stamina Points:";
      case 6:
        return "Dexterity:";
      case 7:
        return "Magic:";
      case 8:
        return "Strength:";
      case 10:
        return "Speed:";
      default:
        return "";
    }
  }

  public void addText(String text) {
    setText(getText() + "\n" + text);
  }

  /**
   * Sets text and changes state and the line where selection starts at.

   * @param text Text to set. (String)
   * @param state State to set gamearea to. Use constants contained in class.
   * @param startingLine Line to start selection from.
   */
  public void setText(String text, GameStates state, int startingLine) {
    setText(text);
    this.gameState = state;
    this.startingLine = startingLine;
    selectedLine = startingLine;
  }

  /**
   * Moves selection.

   * @param b false == up, true = down.
   */
  public void moveSelection(boolean b) {
    if (b) {
      if (displayState == DisplayStates.DEFAULT) {
        if (selectedLine <= getLineCount() - 2) {
          selectedLine++;
        }
      } else {
        moveInventoryDown();
      }
    } else {
      if (displayState == DisplayStates.DEFAULT) {
        if (selectedLine > startingLine) {
          selectedLine--;
        }
      } else {
        moveInventoryUp();
      }
    }
    updateUI();
  }

  private void moveInventoryDown() {
    if (invSelectedLine != 9) {
      invSelectedLine++;
    } else {
      if (invLastIndex == invSelectedIndex) {
        return;
      }
      invStartingIndex++;
    }
    invSelectedIndex++;
  }

  private void moveInventoryUp() {
    if (invSelectedLine != 0) {
      invSelectedLine--;
    } else {
      if (invStartingIndex == 0) {
        return;
      }
      invStartingIndex--;
    }
    invSelectedIndex--;
  }

  /**
   * Returns number based on selection. If no selection returns 0.

   * @return (int)
   */
  public int select() {
    if (displayState == DisplayStates.INVENTORY) {
      Game game = App.getMainWindow().getGame();
      game.useItem(game.getPlayer().getItems().get(invSelectedIndex));
      return - 1;
    }
    if (gameState == GameStates.CHOOSING) {
      return selectedLine - startingLine + 1;
    } else {
      return 0;
    }    
  }

  /**
   * Shows the given displaystate.

   * @param displayState (DisplayStates)
   */
  public void changeDisplayState(DisplayStates displayState) {
    if (this.displayState == displayState) {
      this.displayState = DisplayStates.DEFAULT;
    } else {
      this.displayState = displayState;
    }
    updateUI();
  }
}