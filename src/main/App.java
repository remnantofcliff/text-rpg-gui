package main;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import window.MainWindow;

/**
 * Main method creates the window. Contains static methods used for logging.
 */
public class App {
  private static MainWindow mainWindow;
  private static final Logger LOGGER = Logger.getLogger("Logger");
  
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> mainWindow = new MainWindow());
  }

  public static MainWindow getMainWindow() {
    return mainWindow;
  }

  public static void logNoItemFound(String name) {
    App.LOGGER.log(Level.WARNING, "No item found in inventory: {0}", name);
  }

  public static void logNoSaveFound() {
    App.LOGGER.log(Level.WARNING, "No save-game found. Starting new game.");
  }
  
  public static void logThreadExit(String name) {
    App.LOGGER.log(Level.INFO, "Thread interrupted.\n      Closing thread: {0}", name);
  }

  public static void logWrongInput(int input) {
    App.LOGGER.log(Level.INFO, "Wrong input: {0}", input);
  }
}