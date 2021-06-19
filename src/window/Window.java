package window;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * Abstract window class.
 */
public class Window extends JFrame {
  /**
   * Sets the initial values for every window.

   * @param title Title of window (String)
   * @param width Width of window (int)
   * @param height Height of window (int)
   */
  public Window(String title, int width, int height) {
    setMinimumSize(new Dimension(width, height));
    setSize(width, height);
    setTitle(title);
    setLocationRelativeTo(null);
    setVisible(true);
  }
}
