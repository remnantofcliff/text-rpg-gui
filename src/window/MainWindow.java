package window;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import main.Game;
import window.Display.Directions;
import window.Display.DisplayStates;

/**
 * The main-game window.
 */
public class MainWindow extends Window implements ActionListener {
  private Color uiColor = new Color(255, 250, 236);
  private Display display;
  private static final String FONT_STRING = "Constantia";
  private static final String[] buttonNames = { "Start", "Load", "Settings", "Quit" };
  private transient Game game;

  /**
   * Main window constructor, setups window.
   */
  public MainWindow() {
    super("Main Window", 1280, 960);
    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        super.componentResized(e);
        if (display != null) {
          display.setup();
        }
      }
    });
    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
          case KeyEvent.VK_W:display.moveSelection(Directions.UP);
            break;
          case KeyEvent.VK_UP:display.moveSelection(Directions.UP);
            break;
          case KeyEvent.VK_S:display.moveSelection(Directions.DOWN);
            break;
          case KeyEvent.VK_DOWN:display.moveSelection(Directions.DOWN);
            break;
          case KeyEvent.VK_A:display.moveSelection(Directions.LEFT);
            break;
          case KeyEvent.VK_LEFT:display.moveSelection(Directions.LEFT);
            break;
          case KeyEvent.VK_D:display.moveSelection(Directions.RIGHT);
            break;
          case KeyEvent.VK_RIGHT:display.moveSelection(Directions.RIGHT);
            break;
          case KeyEvent.VK_ENTER:game.setInput(display.select());
            break;
          case KeyEvent.VK_E:display.changeDisplayState(DisplayStates.EQUIPMENT);
            break;
          case KeyEvent.VK_C:display.changeDisplayState(DisplayStates.CHARACTER);
            break;
          default:break;
        }
      }
    });
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    menuScreen();
  }

  private void gameScreen() {
    setFocusable(true);
    display = new Display();
    add(display);
    requestFocus();
  }

  /**
   * Opens the initial Menu screen.
   */
  public void menuScreen() {
    setLayout(new GridLayout(0, 1));
    var title = new JLabel("Game");
    var titleFont = new Font(FONT_STRING, Font.ITALIC, 200);
    title.setFont(titleFont);
    title.setHorizontalAlignment(SwingConstants.CENTER);
    title.setBorder(new EmptyBorder(200, 0, 0, 0));
    var buttonPanel = new JPanel(new GridLayout(0, 1));
    buttonPanel.setBorder(new EmptyBorder(60, 400, 100, 400));
    var buttons = new JButton[4];
    var buttonFont = new Font(FONT_STRING, Font.ITALIC, 20);
    for (var i = 0; i < buttons.length; i++) {
      buttons[i] = new JButton(buttonNames[i]);
      buttons[i].addActionListener(this);
      buttons[i].setActionCommand(buttonNames[i]);
      buttons[i].setBackground(uiColor);
      buttons[i].setFocusPainted(false);
      buttons[i].setFont(buttonFont);
      buttonPanel.add(buttons[i]);
    }
    add(title);
    add(buttonPanel);
  }

  /**
   * Empties the frame.
   */
  public void reset() {
    getContentPane().removeAll();
    getContentPane().revalidate();
    getContentPane().repaint();
    setJMenuBar(null);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Start":startGame(true);
        break;
      case "Load":startGame(false);
        break;
      case "Settings":new SettingsWindow();
        break;
      case "Quit":System.exit(1);
        break;
      default:
        break;
    }
  }

  private void startGame(boolean newgame) {
    reset();
    gameScreen();
    game = new Game(display, newgame);
    game.start();
  }
  /**
   * Sets the font size for the game.

   * @param size font size (int)
   */
  public void setGameFontSize(int size) {
    if (display != null) {
      display.setFont(new Font("Times New Roman", Font.ITALIC, size));
    }
  }

  public Color getUiColor() {
    return uiColor;
  }

  public Game getGame() {
    return game;
  }
}
