package main;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import window.MainWindow;

/**
 * Main method creates the window. Contains static methods used for logging.
 */
public class App {
  private static MainWindow mainWindow;
  private static final Logger LOGGER = Logger.getLogger("Logger");
  
  /**
   * Main method, sets the look and feel and opens the window for the game.

   * @param args arguments when program was ran. (String[])
   */
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
      LOGGER.log(Level.WARNING, "Failed to set look and feel.", e);
    }
    SwingUtilities.invokeLater(() -> mainWindow = new MainWindow());
  }

  public static MainWindow getMainWindow() {
    return mainWindow;
  }

  public static void logNoItemFound(String name) {
    LOGGER.log(Level.WARNING, "No item found in inventory: {0}", name);
  }

  public static void logNoSaveFound() {
    LOGGER.log(Level.WARNING, "No save-game found. Starting new game.");
  }
  
  public static void logThreadExit(String name) {
    LOGGER.log(Level.INFO, "Thread interrupted.\n      Closing thread: {0}", name);
  }

  public static void logWrongInput(int input) {
    LOGGER.log(Level.INFO, "Wrong input: {0}", input);
  }
}