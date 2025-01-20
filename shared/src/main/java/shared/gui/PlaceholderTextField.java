package shared.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JTextField;

/**
 * A JTextField with a placeholder text that is displayed when the field is
 * empty.
 */
public class PlaceholderTextField extends JTextField {
  private String placeholder;

  public PlaceholderTextField(String placeholder, int cols) {
    super(cols);
    this.placeholder = placeholder;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    // If the text is empty, paint the placeholder text
    if (getText().isEmpty()) {
      g.setColor(Color.GRAY);
      g.drawString(placeholder, getInsets().left,
          g.getFontMetrics().getAscent() + getInsets().top);
    }
  }

}
