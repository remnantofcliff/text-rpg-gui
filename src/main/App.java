package main;

import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import window.MainWindow;

/**
 * Main method creates the window.
 */
public class App {
  private static MainWindow mainWindow;
  public static final Logger LOGGER = Logger.getLogger("Logger");
  
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> mainWindow = new MainWindow());
  }

  public static synchronized MainWindow getMainWindow() {
    return mainWindow;
  }
}