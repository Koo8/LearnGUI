import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;

/**
 *  1.createImageIcon
 *  2.getFileExtension
 *  3.getPaymentAmount(for calculator)
 *  4.getTextFieldForSpinnerEditor( for a JSpinner.DefaultEditor)
 *  5.getMaximumSize() - to keep the JComponent size to is preferred height.
 *
 */

public class Utility {
    /* create ImageIcon */
    public static ImageIcon createImageIcon(Object obj, String path) {
        URL imgURL = obj.getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    /* File Extention */
    public static String getFileExtension(File f) {
        String ext = null;
        String name = f.getName();
        int index = name.lastIndexOf('.');
        if(index > 0 && index <name.length()-1 /* not the last char */) {
            ext = name.substring(index+1).toLowerCase();
        }
        return ext;
    }


   // Mortgage loan calculator
    public static double getPaymentAmount(double loan, double rate, int year) {
        // M = P [ i(1 + i)^n ] / [ (1 + i)^n – 1] ---> i = rate /100/12;
        System.out.println("start calculating in getPaymentAmount...");
        System.out.println("rate is " + rate); // rate is 7.5 not 0.075
        double i = rate/100/12.0;
        double a =  Math.pow((1 + i), year * 12);//(1 + i)^n
        double upperPart = loan * (i* a) ;
        double lowerPart = a -1;
        double payment = -(upperPart/lowerPart); // output payment as a negative number

        return payment;
    }
    /**
     * Return the formatted text field used by the JSpinner's editor, or
     * null if the editor doesn't descend from JSpinner.DefaultEditor.
     */
    public static JFormattedTextField getTextFieldForSpinnerEditor(JSpinner spinner) {
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            return ((JSpinner.DefaultEditor)editor).getTextField();
        } else {
            System.err.println("Unexpected editor type: "
                    + spinner.getEditor().getClass()
                    + " isn't a descendant of DefaultEditor");
            return null;
        }
    }

    public static void turnOffTheBoldFont() {
        // turn off bold fonts
        UIManager.put("swing.boldMetal", Boolean.FALSE);
    }

    //Don't allow this panel to get taller than its preferred size.
    //BoxLayout pays attention to MAX size, though most layout
    //managers don't.
    public static Dimension getMaximumSize(JComponent component) {
        return new Dimension(Integer.MAX_VALUE,
                component.getPreferredSize().height);
    }
}
