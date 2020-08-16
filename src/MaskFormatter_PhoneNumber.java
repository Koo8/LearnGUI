import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

/**
 * This program use JFormattedTextField(JFormattedTextField.AbstractFormatter) constructor
 * to create a FTF instance
 */

public class MaskFormatter_PhoneNumber {
    public static void main(String args[]) throws ParseException {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container content = frame.getContentPane();
        content.setLayout(new BoxLayout(content,BoxLayout.PAGE_AXIS));
        // MaskFormatter to format textField input with String, not using any regex related such as Pattern or Matcher
        /**
         *     DIGIT_KEY = '#';
         *     LITERAL_KEY = '\'';
         *     UPPERCASE_KEY = 'U';
         *     LOWERCASE_KEY = 'L';
         *     ALPHA_NUMERIC_KEY = 'A';
         *     CHARACTER_KEY = '?';
         *     ANYTHING_KEY = '*';
         *     HEX_KEY = 'H';
         */
        MaskFormatter formatter1 = new MaskFormatter("###-###-####");
        formatter1.setPlaceholderCharacter('_');
        MaskFormatter formatter2 = new MaskFormatter("(###) ### ####");

        JFormattedTextField field1 = new JFormattedTextField(formatter1);
        JFormattedTextField field2 = new JFormattedTextField(formatter2);

        content.add(field1);
        content.add(field2);
        
        // oooo todo: Figure out how to use defaultFormatter to format 2 fields
        // DefaultFormatter and  matcher to 1.only show even length of string 2. only show non-vowels




        frame.setPreferredSize(new Dimension(500,500));
        frame.pack();
        frame.setVisible(true);
    }
}


