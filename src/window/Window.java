package window;

import javax.swing.JFrame;

/**
 * Abstract window class.
 */
public class Window extends JFrame {
  
  Window(String title, int width, int height) {
    setResizable(false);
    setSize(width, height);
    setTitle(title);
    setLocationRelativeTo(null);
    setVisible(true);
  }
}
