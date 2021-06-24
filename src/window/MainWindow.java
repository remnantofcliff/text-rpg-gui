package window;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
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
public class MainWindow extends JFrame implements ActionListener {
  private Color uiColor = new Color(255, 250, 236);
  private Display display;
  private boolean inGame;
  private static final String FONT_STRING = "Constantia";
  private static final String[] buttonNames = { "Start", "Load", "Quit" };
  private transient Game game;

  /**
   * Main window constructor, setups window.
   */
  public MainWindow() {
    super("Deliquerat");
    setMinimumSize(new Dimension(1280, 960));
    setSize(1280, 960);
    setLocationRelativeTo(null);
    setVisible(true);
    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        super.componentResized(e);
        if (display != null) {
          display.resetFont();
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
          case KeyEvent.VK_ESCAPE: if (inGame) {
              display.exit();
            }
            break;
          default:break;
        }
      }
    });
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        if (game != null) {
          serialize();
        }
      }
    });
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    menuScreen();
  }

  /**
   * Saves the game.
   */
  public void serialize() {
    try (var out = new ObjectOutputStream(new FileOutputStream("tmp/save.ser"))) {
      out.writeObject(game.getPlayer());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void gameScreen() {
    inGame = true;
    setFocusable(true);
    display = Display.getInstance();
    add(display);
    requestFocus();
  }

  /**
   * Opens the initial Menu screen.
   */
  public void menuScreen() {
    inGame = false;
    getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
    var title = new JLabel("Deliquerat");
    title.setAlignmentX(Component.CENTER_ALIGNMENT);
    title.setBorder(new EmptyBorder(250, 0, 0, 0));
    var titleFont = new Font(FONT_STRING, Font.ITALIC, 200);
    title.setFont(titleFont);
    title.setHorizontalAlignment(SwingConstants.CENTER);
    var buttonPanel = new JPanel(new GridLayout(0, 1));
    buttonPanel.setMaximumSize(new Dimension(300, 300));
    var buttons = new JButton[3];
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
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Start":startGame(true);
        break;
      case "Load":startGame(false);
        break;
      case "Quit":System.exit(0);
        break;
      default:
        break;
    }
  }

  private void startGame(boolean newgame) {
    reset();
    gameScreen();
    game = new Game(newgame);
    game.start();
  }

  public Color getUiColor() {
    return uiColor;
  }

  public Game getGame() {
    return game;
  }
}
