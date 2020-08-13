/**
 * Instances of DefaultFormatter can not be used in multiple instances of JFormattedTextField.
 * To obtain a copy of an already configured DefaultFormatter, use the clone method.
 */

import javax.swing.*;
import javax.swing.text.DefaultFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultFormatter_RegexPattern extends DefaultFormatter {

    private Matcher matcher;

    private DefaultFormatter_RegexPattern(Pattern regex) {
        setOverwriteMode(false);
        matcher = regex.matcher(""); // create a Matcher for the regular
        // expression
    }

    /**
     * "class must provide a constructor that takes a String argument.
     * If no single argument constructor that takes a String is found,
     * the returned value will be the String passed into stringToValue. " from oracle defaultFormatter
     * @param string
     * @return
     * @throws java.text.ParseException
     */
    public Object stringToValue(String string) throws java.text.ParseException {
        if (string == null)
            return null;
        matcher.reset(string); // set 'string' as the matcher's input

        if (!matcher.matches()) // Does 'string' match the regular expression?
            throw new java.text.ParseException("does not match regex", 0);

        // If we get this far, then it did match.
        return super.stringToValue(string); // will honor the 'valueClass'
        // property
    }

    public static void main(String argv[]) {
        // a demo main() to show how RegexPatternFormatter could be used

        JLabel lab1 = new JLabel("even length strings:");
        java.util.regex.Pattern evenLength = java.util.regex.Pattern
                .compile("(...)*");
        JFormattedTextField ftf1 = new JFormattedTextField(
                new DefaultFormatter_RegexPattern(evenLength));

        JLabel lab2 = new JLabel("no vowels:");
        java.util.regex.Pattern noVowels = java.util.regex.Pattern.compile(
                "[^aeiou]*", java.util.regex.Pattern.CASE_INSENSITIVE);
        DefaultFormatter_RegexPattern noVowelFormatter = new DefaultFormatter_RegexPattern(
                noVowels);
        noVowelFormatter.setAllowsInvalid(false); // don't allow user to type
        // vowels
        JFormattedTextField ftf2 = new JFormattedTextField(noVowelFormatter);

        JFrame f = new JFrame("RegexPatternFormatter Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel pan1 = new JPanel(new java.awt.BorderLayout());
        pan1.add(lab1, java.awt.BorderLayout.WEST);
        pan1.add(ftf1, java.awt.BorderLayout.CENTER);
        lab1.setLabelFor(ftf1);
        f.getContentPane().add(pan1, java.awt.BorderLayout.NORTH);
        JPanel pan2 = new JPanel(new java.awt.BorderLayout());
        pan2.add(lab2, java.awt.BorderLayout.WEST);
        pan2.add(ftf2, java.awt.BorderLayout.CENTER);
        lab2.setLabelFor(ftf2);
        f.getContentPane().add(pan2, java.awt.BorderLayout.SOUTH);
        f.setSize(300, 80);
        f.setVisible(true);
    }
}
