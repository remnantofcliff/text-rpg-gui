package window;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import main.App;

/**
 * Window for main settings.
 */
public class SettingsWindow extends Window implements ActionListener {
  private JComboBox<String> fontBox = new JComboBox<>(FONT_SIZES);
  private JButton applyButton = new JButton(APPLY_STRING);
  private static final String APPLY_STRING = "Apply";
  private static final String[] FONT_SIZES = {"20", "22", "24", "26", "28", "30", "32", "34", "36",
    "38", "40", "42", "44", "46", "48", "50"};
  private static final EmptyBorder EMPTY_BORDER = new EmptyBorder(0, 0, 0, 0);
  
  /**
   * Creates new Settings-Window.
   */
  public SettingsWindow() {
    super("Settings", 250, 140);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setLayout(new GridLayout(0, 1));
    fontBox.setBorder(new TitledBorder(EMPTY_BORDER, "Font Size"));
    fontBox.setSelectedIndex(7);
    add(fontBox);
    applyButton.setBackground(App.getMainWindow().getUiColor());
    applyButton.addActionListener(this);
    applyButton.setActionCommand(APPLY_STRING);
    add(applyButton);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals(APPLY_STRING)) {
      App.getMainWindow().setGameFontSize(Integer.parseInt((String) fontBox.getSelectedItem()));
    }
  }
}