package shared.gui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class RegisterFilter extends DocumentFilter{
    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
    if (text.isEmpty() || text.chars().allMatch(Character::isDigit)) {
        super.replace(fb, offset, length, text, attrs);
    }
}
}
