package window;

import entity.Item;
import entity.characters.Player;
import entity.interfaces.Droppable;
import entity.interfaces.Equippable;
import entity.interfaces.Usable;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import main.App;
import main.Game.TextStates;

/**
 * Game screen where graphics and text gets drawn.
 */
public class GameArea extends JTextArea {
  private DisplayStates displayState = DisplayStates.DEFAULT;
  private String message = "";
  private TextStates textState = TextStates.TEXT;
  private boolean invMiniPanel = false;
  private int invCategory = 1;
  private int invItems;
  private int invSelectedIndex = 0;
  private int invStartingIndex = 0;
  private int invSelectedLine = 0;
  private int miniSelectedLine = 0;
  private int startingLine;
  private int selectedLine;
  private static final Color CHOOSING_RECT_COLOR = new Color(100, 100, 100, 40);
  private static final String[] PLAYER_STAT_STRINGS = { 
      "Name:",
      "",
      "Hit Points:",
      "Mana Points:",
      "Stamina Points:",
      "",
      "Dexterity:",
      "Magic:",
      "Strength:",
      "",
      "Speed:"
  };
  private static final int LINE_BORDER_WIDTH = 20;
  private static final int EMPTY_BORDER_WIDTH_LEFT = 10;
  private static final int EMPTY_BORDER_WIDTH_RIGHT = 5;
  private static final int CHOOSING_RECT_X = LINE_BORDER_WIDTH + EMPTY_BORDER_WIDTH_LEFT;
  private static final int CHOOSING_RECT_WIDTH_REDUCTION = 
      LINE_BORDER_WIDTH * 2 + EMPTY_BORDER_WIDTH_LEFT + EMPTY_BORDER_WIDTH_RIGHT;
  private transient Item item;
  private transient List<String> itemNameList = new ArrayList<>();
  private transient Player player;
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

  private String getPlayerStat(int i) {
    switch (i) {
      case 0: return player.getName();
      case 2: return player.getHp() + " / " + player.getMaxHp();
      case 3: return player.getMp() + " / " + player.getMaxMp();
      case 4: return player.getSp() + " / " + player.getMaxSp();
      case 6: return Integer.toString(player.getDexterity());
      case 7: return Integer.toString(player.getMagic());
      case 8: return Integer.toString(player.getStrength());
      case 10: return String.valueOf(player.getSpeed());
      default: return "";
    }
  }

  private void itemOptionCheck(
      Item item, Class<?> check, String name, int i,
      Graphics g, int miniStringHeight, int miniStringX
  ) {
    var fontMetrics = g.getFontMetrics();
    if (check.isInstance(item)) {
      g.drawString(
          name,
          miniStringX - fontMetrics.stringWidth(name) / 2,
          miniStringHeight + fontMetrics.getHeight() * i
      );
    }
  }

  private void moveInvCategory(int dir, int check) {
    resetInventory();
    if (displayState == DisplayStates.INVENTORY && invCategory != check) {
      invCategory = invCategory + dir;
    }
  }

  private void moveInvItems(int dir, int check) {
    if (invSelectedLine != check) {
      invSelectedLine = invSelectedLine + dir;
    } else {
      invStartingIndex = invStartingIndex + dir;
    }
    invSelectedIndex = invStartingIndex + invSelectedLine;
  }

  private void moveInventory(int bound, int dir, int check1, int check2) {
    if (invSelectedIndex != bound && !invMiniPanel) {
      moveInvItems(dir, check1);
    } else if (invMiniPanel) {
      moveMiniSelection(dir, check2);
    }
  }

  private void moveMiniSelection(int dir, int check) {
    if (miniSelectedLine != check) {
      miniSelectedLine += dir;
    } else {
      miniSelectedLine = check - dir * 3;
    }
  }

  private void resetInventory() {
    invMiniPanel = false;
    invSelectedIndex = 0;
    invSelectedLine = 0;
    invStartingIndex = 0;
    miniSelectedLine = 0;
  }

  private void showInv(Set<Entry<String, Integer>> entrySet, String category, int categoryX,
      Graphics g, int panelWidth, int stringX, int stringY
  ) {
    var fontMetrics = g.getFontMetrics();
    int fontHeight = fontMetrics.getHeight();
    var i = 0;
    invItems = entrySet.size() - 1;
    for (Entry<String, Integer> entry : entrySet) {
      itemNameList.add(entry.getKey());
      if (i >= invStartingIndex) {
        if (i == invStartingIndex + 9) {
          break;
        }
        g.drawString(
            entry.getKey(),
            stringX,
            stringY + fontHeight * ((i - invStartingIndex) + 2)
        );
        var numberString = entry.getValue().toString();
        g.drawString(
            numberString,
            panelWidth - fontMetrics.stringWidth(numberString),
            stringY + fontHeight * ((i - invStartingIndex) + 2)
        );
      }
      i++;
    }
    g.setColor(CHOOSING_RECT_COLOR);
    int choosingY = fontMetrics.getDescent() + stringY;
    g.fillRect(categoryX, choosingY - fontHeight, fontMetrics.stringWidth(category), fontHeight);
    int maxAdvance = fontMetrics.getMaxAdvance();
    g.fillRect(
        stringX,
        choosingY + fontHeight * (invSelectedLine + 1),
        panelWidth - maxAdvance * 3,
        fontHeight
    );
  }

  private void showMessage(String string) {
    message = string + item.getName();
    displayState = DisplayStates.MESSAGE;
  }

  public DisplayStates getDisplayState() {
    return displayState;
  }

  public int getInvCategory() {
    return invCategory;
  }

  /**
   * Adds the string to the gameArea.

   * @param text (String)
   */
  public void addText(String text) {
    if (getText().equals("")) {
      setText(text);
    } else {
      setText(getText() + " " + text);
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
      if (displayState == DisplayStates.INVENTORY) {
        invCategory = 1;
        resetInventory();
      }
    }
    updateUI();
  }

  /**
   * Moves selection in GameArea.

   * @param direction (Direction)
   */
  public void moveSelection(Directions direction) {
    switch (direction) {
      case UP: if (displayState == DisplayStates.DEFAULT 
            && selectedLine > startingLine 
            && textState != TextStates.TEXT
        ) {
          selectedLine--;
        } else if (displayState == DisplayStates.INVENTORY && invItems != -1) {
          moveInventory(0, -1, 0, 0);
        }
        break;
      case DOWN: if (displayState == DisplayStates.DEFAULT 
            && selectedLine <= getLineCount() - 2 
            && textState != TextStates.TEXT
        ) {
          selectedLine++;
        } else if (displayState == DisplayStates.INVENTORY && invItems != -1) {
          moveInventory(invItems, 1, 8, 3);
        }
        break;
      case LEFT:moveInvCategory(-1, 1);
        break;
      case RIGHT:moveInvCategory(1, 3);
        break;
      default:break;
    }
    updateUI();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    int fontHeight = g.getFontMetrics().getHeight();
    int width = getWidth();
    int choosingRectWidth = width - CHOOSING_RECT_WIDTH_REDUCTION;
    g.setColor(CHOOSING_RECT_COLOR);
    if (textState != TextStates.TEXT) {
      g.fillRect(
          CHOOSING_RECT_X,
          LINE_BORDER_WIDTH + fontHeight * selectedLine,
          choosingRectWidth,
          fontHeight
      );
    }
    if (displayState != DisplayStates.DEFAULT) {
      int height = getHeight();
      int panelX = width / 10;
      int panelY = height / 10;
      int panelWidth = panelX * 7;
      int panelHeight = panelY * 7;
      player = App.getMainWindow().getGame().getPlayer();
      g.setColor(Color.BLACK);
      g.fillRect(panelX, panelY, panelWidth, panelHeight);
      g.setColor(Color.GREEN);
      g.drawRect(panelX, panelY, panelWidth, panelHeight);
      g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
      ((Graphics2D) g).setRenderingHint(
          RenderingHints.KEY_TEXT_ANTIALIASING,
          RenderingHints.VALUE_TEXT_ANTIALIAS_ON
      );
      var fontMetrics = g.getFontMetrics();
      int stringX = panelX + fontMetrics.getMaxAdvance();
      int secondColumnX = stringX + panelWidth / 2;
      fontHeight = fontMetrics.getHeight();
      int stringY = panelY + fontHeight * 3 / 2;
      switch (displayState) {
        case CHARACTER: for (var i = 0; i < 11; i++) {
            int textHeight = stringY + fontHeight * i;
            g.drawString(PLAYER_STAT_STRINGS[i], stringX, textHeight);
            g.drawString(getPlayerStat(i), secondColumnX, textHeight);
          }
          break;
        case EQUIPMENT:g.drawString(PLAYER_STAT_STRINGS[0], stringX, stringY);
          g.drawString(getPlayerStat(0), secondColumnX, stringY);
          g.drawString(
              "Weapon:  " + player.getWeapon().getName(),
              stringX,
              stringY + fontHeight * 2
          );
          g.drawString(
              "Armor:   " + player.getArmor().getName(),
              stringX,
              stringY + fontHeight * 3
          );
          break;
        case INVENTORY:itemNameList.clear();
          int temp = (panelWidth - fontMetrics.getMaxAdvance()) / 3;
          int category2X = stringX + temp;
          int category3X = stringX + temp * 2;
          g.drawString(
              "Gold:" + player.getInventory().getGold(),
              category3X,
              panelY + fontHeight - fontMetrics.getMaxDescent()
          );
          g.drawString("Items", stringX, stringY);
          g.drawString("Weapons", category2X, stringY);
          g.drawString("Armors", category3X, stringY);
          switch (invCategory) {
            case 1:showInv(
                player.getInventory().getItems().entrySet(), "Items", stringX,
                g, panelWidth, stringX, stringY
            );
              break;
            case 2:showInv(
                player.getInventory().getWeapons().entrySet(), "Weapons", category2X,
                g, panelWidth, stringX, stringY
            );
              break;
            case 3:showInv(
                player.getInventory().getArmors().entrySet(), "Armors", category3X,
                g, panelWidth, stringX, stringY
            );
              break;
            default:break;
          }
          if (invMiniPanel && !itemNameList.isEmpty()) {
            int miniWidth = panelWidth / 5;
            int miniHeight = panelHeight / 3;
            int miniY = stringY + fontHeight * (invSelectedLine + 2);
            g.setColor(Color.BLACK);
            g.fillRect(secondColumnX, miniY, miniWidth, miniHeight);
            g.setColor(Color.GREEN);
            g.drawRect(secondColumnX, miniY, miniWidth, miniHeight);
            item = player.getInventory().getItem(itemNameList.get(invSelectedIndex));
            int miniStringHeight = miniY + fontMetrics.getMaxDescent() * 3;
            int miniStringX = secondColumnX + miniWidth / 2;
            itemOptionCheck(item, Usable.class, "Use", 0, g, miniStringHeight, miniStringX);
            itemOptionCheck(item, Equippable.class, "Equip", 1, g, miniStringHeight, miniStringX);
            itemOptionCheck(item, Droppable.class, "Drop", 2, g, miniStringHeight, miniStringX);
            itemOptionCheck(item, Object.class, "Cancel", 3, g, miniStringHeight, miniStringX);
            g.setColor(CHOOSING_RECT_COLOR);
            g.fillRect(secondColumnX, miniY + fontHeight * miniSelectedLine, miniWidth, fontHeight);
          }
          break;
        case MAP:break;
        case MESSAGE:g.drawString(
            message,
            panelX + panelWidth / 2 - fontMetrics.stringWidth(message) / 2,
            panelY + panelHeight / 2 - fontHeight / 2
        );
          break;
        default:break;
      }
    }
  }

  /**
   * Returns number based on selection. If no selection returns 0.

   * @return (int)
   */
  public int select() {
    if (displayState != DisplayStates.DEFAULT) {
      switch (displayState) {
        case INVENTORY: if (invMiniPanel) {
            miniPanelSelect();
            invMiniPanel = false;
          } else {
            miniSelectedLine = 0;
            invMiniPanel = true;
          }
          break;
        case MESSAGE:displayState = DisplayStates.INVENTORY;
          break;
        default:break;
      }
      updateUI();
    } else if (textState == TextStates.CHOOSING) {
      return selectedLine - startingLine;
    }
    return 0;
  }

  private void miniPanelSelect() {
    switch (miniSelectedLine) {
      case 0: if (item instanceof Usable u) {
          u.use(player);
          showMessage("Used ");
        }
        break;
      case 1: if (item instanceof Equippable e) {
          e.equip(player);
          showMessage("Equipped ");
        }
        break;
      case 2: if (item instanceof Droppable d) {
          d.drop(player);
        }
        break;
      default:break;
    }
  }

  /**
   * Changes the text-state.

   * @param textState (TextStates)
   * @param startingLine The line to start selection at, 0 if no selection. (int)
   */
  public void setTextState(TextStates textState, int startingLine) {
    this.textState = textState;
    this.startingLine = startingLine;
    selectedLine = startingLine;
  }

  public void clear() {
    setText("");
  }

  enum Directions {
    DOWN, LEFT, RIGHT, UP
  }
}