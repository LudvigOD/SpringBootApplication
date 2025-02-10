package register.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class RegisterFilter extends DocumentFilter{
    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException{

        if(!Character.isDigit(text.charAt(0))){
            return;
        }

        super.replace(fb, offset, length, text, attrs);
    }
}
