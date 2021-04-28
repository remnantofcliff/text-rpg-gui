package window;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import main.Game;

/**
 * The main-game window.
 */
public class MainWindow extends Window implements ActionListener, KeyListener {
  private Color color = new Color(255, 250, 236);
  private GameArea gameArea;
  private SettingsWindow settingsWindow;
  private int gameFontSize = 34;
  private static final String FONT_STRING = "Constantia";
  private static final String[] buttonNames = { "Start", "Load", "Settings", "Quit" };
  private static final String[] menuItemNames = 
    { "Character", "Equipment", "Inventory", "Map", "Menu" };
  private static final String[] menuItemNameKeys = { "C", "E", "I", "M", "ESCAPE" };
  private transient Game game;

  /**
   * Main window constructor, setups window.
   */
  public MainWindow() {
    super("Main Window", 1024, 768);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowActivated(WindowEvent e) {
        if (!isEnabled()) {
          settingsWindow.setState(Frame.NORMAL);
          settingsWindow.requestFocus();
        }
      }
    });
    addKeyListener(this);
    menuScreen();
  }

  private void gameScreen() {
    setFocusable(true);
    Font buttonFont = new Font(FONT_STRING, Font.ITALIC, 18);
    JMenuItem[] menuItems = new JMenuItem[menuItemNames.length];
    JMenuBar menuBar = new JMenuBar();
    for (int i = 0; i < menuItems.length; i++) {
      menuItems[i] = new JMenuItem(menuItemNames[i]);
      menuItems[i].setActionCommand(menuItemNames[i]);
      menuItems[i].addActionListener(this);
      menuItems[i].setBackground(color);
      menuItems[i].setFont(buttonFont);
      menuItems[i].setAccelerator(KeyStroke.getKeyStroke(menuItemNameKeys[i]));
      menuBar.add(menuItems[i]);
    }
    setJMenuBar(menuBar);
    menuBar.setFocusable(false);
    gameArea = new GameArea(new Font("Times New Roman", Font.ITALIC, gameFontSize), color);
    add(gameArea);
    requestFocus();
  }

  private void menuScreen() {
    setLayout(new GridLayout(0, 1));
    JLabel title = new JLabel("Game");
    Font titleFont = new Font(FONT_STRING, Font.ITALIC, 200);
    title.setFont(titleFont);
    title.setHorizontalAlignment(SwingConstants.CENTER);
    title.setBorder(new EmptyBorder(200, 0, 0, 0));
    JPanel buttonPanel = new JPanel(new GridLayout(0, 1));
    buttonPanel.setBorder(new EmptyBorder(60, 400, 100, 400));
    JButton[] buttons = new JButton[4];
    Font buttonFont = new Font(FONT_STRING, Font.ITALIC, 20);
    for (int i = 0; i < buttons.length; i++) {
      buttons[i] = new JButton(buttonNames[i]);
      buttons[i].setActionCommand(buttonNames[i]);
      buttons[i].addActionListener(this);
      buttons[i].setBackground(color);
      buttons[i].setFocusPainted(false);
      buttons[i].setFont(buttonFont);
      buttonPanel.add(buttons[i]);
    }
    add(title);
    add(buttonPanel);
  }

  private void reset() {
    getContentPane().removeAll();
    getContentPane().revalidate();
    getContentPane().repaint();
    setJMenuBar(null);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Start":
        reset();
        gameScreen();
        game = new Game(gameArea);
        game.start();
        break;
      case "Load":
        break;
      case "Settings":
        settingsWindow = new SettingsWindow();
        setEnabled(false);
        break;
      case "Quit":
        System.exit(1);
        break;
      case "Character":
        gameArea.changeDisplayState(DisplayStates.CHARACTER);
        break;
      case "Equipment":
        gameArea.changeDisplayState(DisplayStates.EQUIPMENT);
        break;
      case "Inventory":
        gameArea.changeDisplayState(DisplayStates.INVENTORY);
        break;
      case "Map":
        gameArea.changeDisplayState(DisplayStates.MAP);
        break;
      case "Menu":
        if (
            JOptionPane.showConfirmDialog(this, "Quit to main menu?", "", JOptionPane.YES_NO_OPTION)
            == 0) {
          reset();
          game.interrupt();
          menuScreen();
        }
        break;
      default:
        break;
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
    //
  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_W:
        gameArea.moveSelection('u');
        break;
      case KeyEvent.VK_UP:
        gameArea.moveSelection('u');
        break;
      case KeyEvent.VK_S:
        gameArea.moveSelection('d');
        break;
      case KeyEvent.VK_DOWN:
        gameArea.moveSelection('d');
        break;
      case KeyEvent.VK_A:
        gameArea.moveSelection('l');
        break;
      case KeyEvent.VK_LEFT:
        gameArea.moveSelection('l');
        break;
      case KeyEvent.VK_D:
        gameArea.moveSelection('r');
        break;
      case KeyEvent.VK_RIGHT:
        gameArea.moveSelection('r');
        break;
      case KeyEvent.VK_ENTER:
        if (gameArea.getDisplayState() == DisplayStates.INVENTORY) {
          gameArea.select();
        } else {
          game.setInput(gameArea.select());
        }
        break;
      default:
        break;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    //
  }

  public void setGameFontSize(int gameFontSize) {
    this.gameFontSize = gameFontSize;
  }

  public Game getGame() {
    return game;
  }
}
