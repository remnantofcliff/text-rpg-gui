package window;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.stream.Stream;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import main.App;

/**
 * Window for main settings.
 */
public class SettingsWindow extends Window {
  private JCheckBox fullScreenBox = new JCheckBox("Fullscreen");
  private JComboBox<String> fontBox = new JComboBox<>(FONT_SIZES);
  private JComboBox<String> resolutionBox = new JComboBox<>(RESOLUTIONS);
  private static final String[] FONT_SIZES = { "20", "22", "24", "26", "28", "30", "32", "34", "36",
    "38", "40", "42", "44", "46", "48", "50" };
  private static final String[] RESOLUTIONS = { "1024x768", "1280x720", "1280x800", "1280x1024",
    "1360x768", "1366x768", "1440x900", "1536x864", "1600x900", "1680x1050", "1920x1080",
    "1920x1200", "2048x1152", "2560x1080", "2560x1440", "3440x1440", "3840x2160" };
  private static final EmptyBorder EMPTY_BORDER = new EmptyBorder(0, 0, 0, 0);
  
  SettingsWindow() {
    super("Settings", 250, 600);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setLayout(new GridLayout(0, 1));
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosed(WindowEvent e) {
        MainWindow mainFrame = App.getMainWindow();
        if (fullScreenBox.isSelected()) {
          mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        } else {
          int[] res = Stream.of(resolutionBox.getSelectedItem().toString().split("x"))
          .mapToInt(Integer::parseInt).toArray();
          mainFrame.setSize(res[0], res[1]);
        }
        mainFrame.setGameFontSize(Integer.parseInt((String) fontBox.getSelectedItem()));
        mainFrame.setEnabled(true);
        mainFrame.requestFocus();
        mainFrame.setLocationRelativeTo(null);
      }
    });
    resolutionBox.setBorder(new TitledBorder(EMPTY_BORDER, "Resolution"));
    add(resolutionBox);
    fontBox.setBorder(new TitledBorder(EMPTY_BORDER, "Font Size"));
    fontBox.setSelectedIndex(7);
    add(fontBox);
    add(fullScreenBox);
  }
}