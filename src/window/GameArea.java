package window;

import entity.characters.Player;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
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
import main.TextStates;

/**
 * Game screen where graphics and text gets drawn.
 */
public class GameArea extends JTextArea {
  private DisplayStates displayState = DisplayStates.DEFAULT;
  private TextStates textState = TextStates.TEXT;
  private boolean invMiniPanel = false;
  private int startingLine;
  private int selectedLine;
  private int invCategory = 1;
  private int invItems;
  private int invSelectedIndex = 0;
  private int invSelectedLine = 0;
  private int invStartingIndex = 0;
  private static final Color CHOOSING_RECT_COLOR = new Color(100, 100, 100, 40);
  private static final int LINE_BORDER_WIDTH = 20;
  private static final int EMPTY_BORDER_WIDTH_LEFT = 10;
  private static final int EMPTY_BORDER_WIDTH_RIGHT = 5;
  private static final int CHOOSING_RECT_X = LINE_BORDER_WIDTH + EMPTY_BORDER_WIDTH_LEFT;
  private static final int CHOOSING_RECT_WIDTH_REDUCTION = 
      LINE_BORDER_WIDTH * 2 + EMPTY_BORDER_WIDTH_LEFT + EMPTY_BORDER_WIDTH_RIGHT;
  private transient List<String> itemNameList = new ArrayList<>();

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
      Player player = App.getMainWindow().getGame().getPlayer();
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
      int stringX = panelX + fontMetrics.getMaxAdvance();
      int secondRowX = stringX + panelWidth / 2;
      fontHeight = fontMetrics.getHeight();
      int stringY = (int) (panelY + fontHeight * 1.5);
      switch (displayState) {
        case CHARACTER:
          for (int i = 0; i < 11; i++) {
            int textHeight = stringY + fontHeight * i;
            g.drawString(getPlayerStatString(i), stringX, textHeight);
            g.drawString(getPlayerStat(i, player), secondRowX, textHeight);
          }
          break;
        case EQUIPMENT:
          g.drawString(getPlayerStatString(0), stringX, stringY);
          g.drawString(getPlayerStat(0, player), secondRowX, stringY);
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
        case INVENTORY:
          itemNameList.clear();
          int maxAdvance = fontMetrics.getMaxAdvance();
          int category2X = stringX + (panelWidth - maxAdvance) / 3;
          int category3X = stringX + (panelWidth - maxAdvance) / 3 * 2;
          g.drawString("Items", stringX, stringY);
          g.drawString("Weapons", category2X, stringY);
          g.drawString("Armors", category3X, stringY);
          switch (invCategory) {
            case 1:
              showInv(
                  player.getInventory().getItems().entrySet(), "Items", stringX,
                  g, panelWidth, stringX, stringY
              );
              break;
            case 2:
              showInv(
                  player.getInventory().getWeapons().entrySet(), "Weapons", category2X,
                  g, panelWidth, stringX, stringY
              );
              break;
            case 3:
              showInv(
                  player.getInventory().getArmors().entrySet(), "Armors", category3X,
                  g, panelWidth, stringX, stringY
              );
              break;
            default:
              break;
          }
          if (invMiniPanel) {
            g.setColor(Color.GREEN);
            g.fillRect(secondRowX, stringY + fontHeight * (selectedLine + 3), 100, 100);
          }
          break;
        case MAP:
          break;
        default:
          break;
      }
    }
  }

  private void showInv(Set<Entry<String, Integer>> entrySet, String category, int categoryX,
      Graphics g, int panelWidth, int stringX, int stringY
  ) {
    FontMetrics fontMetrics = g.getFontMetrics();
    int fontHeight = fontMetrics.getHeight();
    int i = 0;
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
        String numberString = entry.getValue().toString();
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
        return String.valueOf(player.getSpeed());
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
  public void setText(String text, TextStates state, int startingLine) {
    setText(text);
    this.textState = state;
    this.startingLine = startingLine;
    selectedLine = startingLine;
  }

  /**
   * Moves selection.

   * @param dir 'u' == up, 'd' == down, 'l' == left, 'r' == right (char)
   */
  public void moveSelection(char dir) {
    switch (dir) {
      case 'u':
        if (displayState == DisplayStates.DEFAULT && selectedLine > startingLine) {
          selectedLine--;
        } else if (displayState == DisplayStates.INVENTORY && invSelectedIndex != 0) {
          moveInvItems(-1, 0);
        }
        break;
      case 'd':
        if (displayState == DisplayStates.DEFAULT && selectedLine <= getLineCount() - 2) {
          selectedLine++;
        } else if (displayState == DisplayStates.INVENTORY && invSelectedIndex != invItems) {
          moveInvItems(1, 8);
        }
        break;
      case 'l':
        moveInvCategory(-1, 1);
        break;
      case 'r':
        moveInvCategory(1, 3);
        break;
      default:
        break;
    }
    updateUI();
  }

  private void moveInvCategory(int dir, int check) {
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

  /**
   * Returns number based on selection. If no selection returns 0.

   * @return (int)
   */
  public int select() {
    if (displayState == DisplayStates.INVENTORY) {
      if (invMiniPanel) {
        invMiniPanel = false;
      } else {
        invMiniPanel = true;
      }
      updateUI();
    }
    if (textState == TextStates.CHOOSING) {
      return selectedLine - startingLine + 1;
    }
    return 0; 
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

  public int getInvCategory() {
    return invCategory;
  }
}