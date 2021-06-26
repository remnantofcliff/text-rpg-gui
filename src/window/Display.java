package window;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import main.App;

/**
 * Display for game.
 */
public class Display extends JPanel implements MouseWheelListener {
  private DisplayStates ds = DisplayStates.DEFAULT;
  private Font smallFont = new Font("Constantia", Font.BOLD, 15);
  private Font textFont = new Font("Constantia", Font.ITALIC, 34);
  private FontMetrics fm;
  private MainWindow mw = App.getMainWindow();
  private Rectangle[] buttons = new Rectangle[3];
  private StringBuilder text = new StringBuilder();
  private boolean setupDone;
  private int borderHeight;
  private int borderWidth;
  private int fontHeight;
  private int fontSize = 34;
  private int fontSizeSmall = 15;
  private int hgt;
  private int lineOffset = 1;
  private int maxDescent;
  private int maxTextWidth;
  private int mouseOverSelection = 3;
  private int selectedIndex;
  private int smallFontHeight;
  private int spLineY;
  private int spH;
  private int spHalfway;
  private int spMaxX;
  private int spTextX;
  private int spTextY;
  private int spW;
  private int spX;
  private int spY;
  private int uiStringY;
  private int wdtOffset1;
  private int wdtOffset2;
  private int[] uiStringX = new int[3];
  private static Display instance;
  private static final Color SELECTION_COLOR = new Color(200, 200, 200, 60);
  private static final Color PANEL_COLOR = new Color(100, 100, 255, 230);
  private static final Color TEXT_COLOR = App.getMainWindow().getUiColor();
  private static final List<Integer> SELECTABLE_LINES = new ArrayList<>();
  private static final List<Integer> CHARACTER_LINE_INTEGERS = List.of(0, 3, 7, 11);
  private static final String[] CHARACTER_STRINGS = {"||Character||", "Name:", "Difficulty:", "",
    "Health:", "Mana:", "Stamina:", "", "Dexterity:", "Magic:", "Strength:", "", "Speed:"};
  private static final String[] UI_STRINGS = {"Character", "Equipment", "Menu"};

  /**
   * Constructs a new display.
   */
  private Display() {
    addMouseWheelListener(this);
    addMouseListener();
    addMouseMotionListener();
  }

  private String[] getTextRows() {
    String[] arr = text.toString().split("\n");
    for (var i = 0; i < arr.length; i++) {
      var sb = new StringBuilder();
      while (fm.stringWidth(arr[i]) > maxTextWidth) {
        int lastSpace = arr[i].lastIndexOf(' ');
        sb.insert(0, arr[i].substring(lastSpace, arr[i].length()));
        arr[i] = arr[i].substring(0, lastSpace);
      }
      int j = i + 1;
      if (j == arr.length && !sb.isEmpty()) {
        var temp = new String[arr.length + 1];
        System.arraycopy(arr, 0, temp, 0, arr.length);
        arr = temp;
      }
      if (!sb.isEmpty()) {
        arr[j] = arr[j] == null ? sb.substring(1) : sb.append(" ").substring(1) + arr[j];
      }
    }
    return arr;
  }

  private void addMouseListener() {
    addMouseListener(new MouseAdapter() {
      private void button1(MouseEvent e) {
        int x = e.getX();
        if (e.getY() <= borderHeight) {
          if (x <= buttons[0].getMaxX()) {
            changeDisplayState(DisplayStates.CHARACTER);
          } else if (x <= buttons[1].getMaxX()) {
            changeDisplayState(DisplayStates.EQUIPMENT);
          } else {
            exit();
          }
        } else if (borderWidth < x && x < wdtOffset1) {
          mw.getGame().setInput(select());
        }
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
          button1(e);
        }
      }
    });
  }

  private void addMouseMotionListener() {
    addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseMoved(MouseEvent e) {
          var repaint = false;
          int temp = mouseOverSelection;
          int y = e.getY();
          if (y <= borderHeight) {
            int x = e.getX();
            if (x <= buttons[0].getMaxX()) {
              mouseOverSelection = 0;
            } else if (x <= buttons[1].getMaxX()) {
              mouseOverSelection = 1;
            } else {
              mouseOverSelection = 2;
            }
          } else {
            mouseOverSelection = 3;
            int select = y / fontHeight - 1;
            if (SELECTABLE_LINES.contains(select)) {
              selectedIndex = SELECTABLE_LINES.indexOf(select);
              repaint = true;
            }
          }
          if (temp != mouseOverSelection) {
            repaint = true;
          }
          if (repaint) {
            repaint();
          }
        }
    });
  }

  private void drawBackground(Graphics g) {
    g.setColor(Color.BLACK);
    g.fillRect(borderWidth, 0, wdtOffset2, hgt);
  }

  private void drawBorders(Graphics g) {
    g.setColor(TEXT_COLOR);
    g.fillRect(0, 0, borderWidth, hgt);
    g.fillRect(wdtOffset1, 0, borderWidth, hgt);
    g.fillRect(borderWidth, 0, wdtOffset2, borderHeight);
  }

  private void drawSecondaryPanel(Graphics g) {
    if (ds != DisplayStates.DEFAULT) {
      g.setColor(PANEL_COLOR);
      g.fillRect(spX, spY, spW, spH);
      g.setColor(Color.WHITE);
      var player = mw.getGame().getPlayer();
      if (ds == DisplayStates.CHARACTER) {
        for (var i = 0; i < CHARACTER_STRINGS.length; i++) {
          int j = i + 1;
          if (CHARACTER_LINE_INTEGERS.contains(i)) {
            g.drawLine(spX, spLineY + fontHeight * j, spMaxX, spLineY + fontHeight * j);
          }
          g.drawString(CHARACTER_STRINGS[i], spTextX, spTextY + fontHeight * j);
          g.drawString(
              switch (i) { case 1 -> player.getName();
              case 2 -> player.getDifficulty().toString();
              case 4 -> player.getHp() + " / " + player.getMaxHp();
              case 5 -> player.getMp() + " / " + player.getMaxMp();
              case 6 -> player.getSp() + " / " + player.getMaxSp();
              case 8 -> Integer.toString(player.getDexterity());
              case 9 -> Integer.toString(player.getMagic());
              case 10 -> Integer.toString(player.getStrength());
              case 12 -> Double.toString(player.getSpeed());
              default -> "";
            }, spHalfway, spTextY + fontHeight * j);
        }
      } else {
        for (var i = 1; i <= 3; i++) {
          g.drawString(
              switch (i) { case 1 -> "||Equipment||";
              case 2 -> "Armor: " + player.getArmor().getName();
              case 3 -> "Weapon: " + player.getWeapon().getName();
              default -> "";
            }, spTextX, spTextY + fontHeight * i);
        }
        g.drawLine(spX, spLineY + fontHeight, spMaxX, spLineY + fontHeight);
      }
    }
  }

  private void drawSelection(Graphics g) {
    g.setColor(SELECTION_COLOR);
    if (selectedIndex >= SELECTABLE_LINES.size() && !SELECTABLE_LINES.isEmpty()) {
      selectedIndex = SELECTABLE_LINES.size() - 1;
    }
    if (!SELECTABLE_LINES.isEmpty()) {
      g.fillRect(
          borderWidth,
          borderHeight + 5 + SELECTABLE_LINES.get(selectedIndex) * fontHeight + maxDescent,
          wdtOffset2,
          fontHeight
      );
    }
  }
 
  private void drawText(Graphics g) {
    g.setColor(TEXT_COLOR);
    g.setFont(textFont);
    String[] arr = getTextRows();
    SELECTABLE_LINES.clear();
    for (var i = 0; i < arr.length; i++) {
      g.drawString(arr[i], borderWidth + 5, borderHeight + 5 + fontHeight * (i + lineOffset));
      if (arr[i].startsWith("-")) {
        SELECTABLE_LINES.add(i);
      }
    }
  }

  private void drawTopButtons(Graphics g) {
    g.setColor(Color.BLACK);
    g.setFont(smallFont);
    for (var i = 0; i < UI_STRINGS.length; i++) {
      g.drawString(UI_STRINGS[i], uiStringX[i], uiStringY);
    }
    Graphics2D g2D = (Graphics2D) g;
    g2D.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB
    );
    for (var i = 0; i < buttons.length; i++) {
      g2D.draw(buttons[i]);
    }
    if (mouseOverSelection != 3) {
      g.setColor(SELECTION_COLOR);
      g2D.fill(buttons[mouseOverSelection]);
    }
  }

  public void addText(String text) {
    this.text.append(text);
    repaint();
  }

  /**
   * Changes the current display state. If current displaystate is the same as the one it's being
   * changed to, it gets changed back to DEFAULT.

   * @param ds (DisplayStates)
   */
  public void changeDisplayState(DisplayStates ds) {
    if (this.ds == ds) {
      this.ds = DisplayStates.DEFAULT;
    } else {
      this.ds = ds;
    }
    repaint();
  }

  public void clear() {
    text = new StringBuilder();
    repaint();
  }

  /**
   * Close the game and reset MainWindow to main menu.
   */
  public void exit() {
    if (JOptionPane.showConfirmDialog(mw, "Quit to main menu?", "Quit?",
        JOptionPane.YES_NO_OPTION
    ) == 0) {
      clear();
      mw.serialize();
      mw.getGame().interrupt();
      mw.reset();
      mw.menuScreen();
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    if (setupDone) {
      super.paintComponent(g);
      drawBackground(g);
      drawBorders(g);
      drawTopButtons(g);
      drawText(g);
      drawSelection(g);
      drawSecondaryPanel(g);
    } else {
      int wdt = getWidth();
      borderWidth = (wdt - 4) / 63;
      hgt = getHeight();
      borderHeight = (hgt - 1) / 46;
      spX = wdt / 20;
      spTextX = spX + 20;
      spW = spX * 16;
      spMaxX = spX + spW;
      spHalfway = (spW) / 2 + 20;
      wdtOffset1 = wdt - borderWidth;
      wdtOffset2 = wdtOffset1 - borderWidth;
      maxTextWidth = wdtOffset2 - 5;
      fm = getFontMetrics(textFont);
      fontHeight = fm.getHeight();
      maxDescent = fm.getMaxDescent();
      var fmSmall = getFontMetrics(smallFont);
      smallFontHeight = fmSmall.getHeight();
      int wdtdiv6 = wdt / 6 + 1;
      uiStringX[0] = wdtdiv6 - fmSmall.stringWidth(UI_STRINGS[0]) / 2;
      uiStringX[1] = wdtdiv6 * 3 - fmSmall.stringWidth(UI_STRINGS[1]) / 2;
      uiStringX[2] = wdtdiv6 * 5 - fmSmall.stringWidth(UI_STRINGS[2]) / 2;
      uiStringY = borderHeight / 2 + 1 + fmSmall.getDescent();
      spY = hgt / 20;
      spH = spY * 16;
      spTextY = spY + 20;
      spLineY = spTextY + maxDescent;
      int wdtdiv3 = wdtdiv6 * 2;
      buttons[0] = new Rectangle(0, 0, wdtdiv3, borderHeight);
      buttons[1] = new Rectangle(wdtdiv3, 0, wdtdiv3, borderHeight);
      buttons[2] = new Rectangle(wdtdiv3 * 2, 0, wdtdiv3, borderHeight);
      setupDone = true;
      repaint();
    }
  }

  /**
   * Moves current selection.

   * @param direction (Direction)
   */
  public void moveSelection(Directions direction) {
    switch (direction) {
      case DOWN:
        if (selectedIndex != SELECTABLE_LINES.size() - 1) {
          selectedIndex++;
        }
        break;
      case LEFT:
        break;
      case RIGHT:
        break;
      case UP:
        if (selectedIndex != 0) {
          selectedIndex--;
        }
        break;
      default:
      
    }
    repaint();
  }

  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {
    if (e.getWheelRotation() == -1 && smallFontHeight < borderHeight + 1) {
      fontSize++;
      fontSizeSmall++;
    } else if (e.getWheelRotation() == 1 && 18 < smallFontHeight) {
      fontSize--;
      fontSizeSmall--;
    }
    smallFont = smallFont.deriveFont((float) fontSizeSmall);
    textFont = textFont.deriveFont((float) fontSize);
    setup();
    repaint();
  }

  public void resetFont() {
    smallFont = smallFont.deriveFont((float) 15);
    textFont = textFont.deriveFont((float) 34);
  }

  /**
   * Returns selection number. Starts from 0.
   */
  public int select() {
    int temp = selectedIndex;
    selectedIndex = 0;
    return temp;
  }

  public void setup() {
    setupDone = false;
  }

  /**
   * Enum for directions of selection.
   */
  public enum Directions {
    UP, DOWN, LEFT, RIGHT
  }

  /**
   * Display-states.
   */
  public enum DisplayStates {
    CHARACTER, EQUIPMENT, DEFAULT
  }

  /**
   * Returns the single instance of the Display-class.

   * @return (Display)
   */
  public static Display getInstance() {
    if (instance == null) {
      instance = new Display();
    }
    return instance;
  }
}