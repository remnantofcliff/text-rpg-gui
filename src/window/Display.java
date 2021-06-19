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
 * GameArea.
 */
public class Display extends JPanel implements MouseWheelListener {
  private FontMetrics fm;
  private DisplayStates ds = DisplayStates.DEFAULT;
  private MainWindow mw = App.getMainWindow();
  private Rectangle[] buttons = new Rectangle[3];
  private StringBuilder text = new StringBuilder();
  private boolean setupDone;
  private int fontHeight;
  private int hgt;
  private int lineAmount;
  private int lineOffset = 1;
  private int maxDescent;
  private int maxLines;
  private int maxTextWidth;
  private int mouseOverSelection = 3;
  private int selectedIndex;
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
  private static final List<Integer> selectableLines = new ArrayList<>();
  private static final Color SELECTION_COLOR = new Color(200, 200, 200, 60);
  private static final Color PANEL_COLOR_A = new Color(100, 100, 255, 200);
  private static final Color TEXT_COLOR = App.getMainWindow().getUiColor();
  private static final Font SMALL_FONT = new Font("Constantia", Font.BOLD, 15);
  private static final Font TEXT_FONT = new Font("Constantia", Font.ITALIC, 34);
  private static final List<Integer> CHARACTER_LINE_INTEGERS = List.of(0, 2, 3, 4, 6, 7, 8, 10);
  private static final String[] UI_STRINGS = {"Character", "Equipment", "Menu"};
  private static final String[] CHARACTER_STRINGS = {"Name:", "", "Health:", "Mana:", "Stamina:",
      "", "Dexterity:", "Magic:", "Strength:", "", "Speed:"};

  /**
   * Constructs a new display.
   */
  public Display() {
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
      @Override
      public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
          int x = e.getX();
          if (e.getY() <= 20) {
            if (x <= buttons[0].getMaxX()) {
              changeDisplayState(DisplayStates.CHARACTER);
            } else if (x <= buttons[1].getMaxX()) {
              changeDisplayState(DisplayStates.EQUIPMENT);
            } else {
              if (JOptionPane.showConfirmDialog(
                  mw,
                  "Quit to main menu?",
                  "Quit?",
                  JOptionPane.YES_NO_OPTION
              ) == 0) {
                mw.reset();
                mw.menuScreen();
              }
            }
          } else if (20 < x && x < wdtOffset1) {
            mw.getGame().setInput(select());
          }
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
          if (y <= 20) {
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
            if (selectableLines.contains(select)) {
              selectedIndex = selectableLines.indexOf(select);
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

  public void addText(String text) {
    this.text.append(text);
    repaint();
  }

  public void clear() {
    text = new StringBuilder();
    repaint();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (setupDone) {
      Graphics2D g2D = (Graphics2D) g;
      g2D.setRenderingHint(
          RenderingHints.KEY_TEXT_ANTIALIASING,
          RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB
      );
      drawBackground(g);
      drawBorders(g);
      drawTopButtons(g, g2D);
      drawText(g);
      drawSelection(g);
      drawSecondaryPanel(g);
    } else {
      int wdt = getWidth() - 1;
      spX = wdt / 20;
      spTextX = spX + 20;
      spW = spX * 16;
      spMaxX = spX + spW;
      spHalfway = (spW) / 2 + 20;
      wdtOffset1 = wdt - 20;
      wdtOffset2 = wdtOffset1 - 20;
      maxTextWidth = wdtOffset2 - 5;
      fm = getFontMetrics(TEXT_FONT);
      fontHeight = fm.getHeight();
      maxDescent = fm.getMaxDescent();
      var fmSmall = getFontMetrics(SMALL_FONT);
      int wdtdiv6 = wdt / 6 + 1;
      uiStringX[0] = wdtdiv6 - fmSmall.stringWidth(UI_STRINGS[0]) / 2;
      uiStringX[1] = wdtdiv6 * 3 - fmSmall.stringWidth(UI_STRINGS[1]) / 2;
      uiStringX[2] = wdtdiv6 * 5 - fmSmall.stringWidth(UI_STRINGS[2]) / 2;
      uiStringY = fmSmall.getLeading() + 10;
      hgt = getHeight() - 1;
      spY = hgt / 20;
      spH = spY * 16;
      spTextY = spY + 20;
      spLineY = spTextY + maxDescent;
      maxLines = hgt / fontHeight;
      int wdtdiv3 = wdtdiv6 * 2;
      buttons[0] = new Rectangle(0, 0, wdtdiv3, 20);
      buttons[1] = new Rectangle(wdtdiv3, 0, wdtdiv3, 20);
      buttons[2] = new Rectangle(wdtdiv3 * 2, 0, wdtdiv3, 20);
      setupDone = true;
      repaint();
    }
  }

  private void drawSecondaryPanel(Graphics g) {
    if (ds != DisplayStates.DEFAULT) {
      g.setColor(PANEL_COLOR_A);
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
              switch (i) { case 0 -> player.getName();
              case 2 -> player.getHp() + " / " + player.getMaxHp();
              case 3 -> player.getMp() + " / " + player.getMaxMp();
              case 4 -> player.getSp() + " / " + player.getMaxSp();
              case 6 -> Integer.toString(player.getDexterity());
              case 7 -> Integer.toString(player.getMagic());
              case 8 -> Integer.toString(player.getStrength());
              case 10 -> Double.toString(player.getSpeed());
              default -> "";
            }, spHalfway, spTextY + fontHeight * j);
        }
      } else {

      }
    }
  }

  private void drawSelection(Graphics g) {
    g.setColor(SELECTION_COLOR);
    if (selectedIndex >= selectableLines.size() && !selectableLines.isEmpty()) {
      selectedIndex = selectableLines.size() - 1;
    }
    if (!selectableLines.isEmpty()) {
      g.fillRect(
          20,
          25 + selectableLines.get(selectedIndex) * fontHeight + maxDescent,
          wdtOffset2,
          fontHeight
      );
    }
  }

  private void drawText(Graphics g) {
    g.setColor(TEXT_COLOR);
    g.setFont(TEXT_FONT);
    String[] arr = getTextRows();
    lineAmount = arr.length;
    selectableLines.clear();
    for (var i = 0; i < lineAmount; i++) {
      g.drawString(arr[i], 25, 25 + fontHeight * (i + lineOffset));
      if (arr[i].startsWith("-")) {
        selectableLines.add(i);
      }
    }
  }

  private void drawTopButtons(Graphics g, Graphics2D g2D) {
    g.setColor(Color.BLACK);
    g.setFont(SMALL_FONT);
    for (var i = 0; i < UI_STRINGS.length; i++) {
      g.drawString(UI_STRINGS[i], uiStringX[i], uiStringY);
    }
    for (var i = 0; i < buttons.length; i++) {
      g2D.draw(buttons[i]);
    }
    if (mouseOverSelection != 3) {
      g.setColor(SELECTION_COLOR);
      g2D.fill(buttons[mouseOverSelection]);
    }
  }

  private void drawBorders(Graphics g) {
    g.setColor(TEXT_COLOR);
    g.fillRect(0, 0, 20, hgt);
    g.fillRect(wdtOffset1, 0, 20, hgt);
    g.fillRect(20, 0, wdtOffset2, 20);
  }

  private void drawBackground(Graphics g) {
    g.setColor(Color.BLACK);
    g.fillRect(20, 0, wdtOffset2, hgt);
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

  /**
   * Moves current selection.

   * @param direction (Direction)
   */
  public void moveSelection(Directions direction) {
    switch (direction) {
      case DOWN:
        if (selectedIndex != selectableLines.size() - 1) {
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
        break;
    }
    repaint();
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

  public DisplayStates getDisplayState() {
    return DisplayStates.DEFAULT;
  }

  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {
    if (-lineOffset + maxLines + 1 < lineAmount && e.getWheelRotation() == 1) {
      lineOffset--;
    } else if (lineOffset != 1 && e.getWheelRotation() == -1) {
      lineOffset++;
    }
    repaint();
  }
}