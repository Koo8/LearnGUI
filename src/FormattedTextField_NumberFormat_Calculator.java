import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

/**
 * mortgage calculating formula:
 * M = P [ i(1 + i)^n ] / [ (1 + i)^n – 1]
 */

public class FormattedTextField_NumberFormat_Calculator
extends JPanel
implements PropertyChangeListener {
    JFormattedTextField f1,f2,f3, f4;

    double amount = 100000, rate = 7.5;
    int amortization = 30;


    FormattedTextField_NumberFormat_Calculator() {
        super(new BorderLayout());
       // double payment = ;

        // for formattedTextField calculator
        // two JPanels left and right to hold JLabels and JFormatted Text Fields
        JPanel leftLabels = new JPanel(new GridLayout(0,1));
        leftLabels.setAlignmentX(RIGHT_ALIGNMENT);
        JPanel rightFields = new JPanel(new GridLayout(0, 1));
        rightFields.setAlignmentX(LEFT_ALIGNMENT);
        // add four labels into the left JPanel
        JLabel label1 = new JLabel("Loan Amount");
        JLabel label2 = new JLabel("APR rate (%)");
        JLabel label3 = new JLabel("Amortization  ");
        JLabel label4 = new JLabel("Payment");
        leftLabels.add(label1);
        leftLabels.add(label2);
        leftLabels.add(label3);
        leftLabels.add(label4);

        // add four formattedTextFields to the right JPanel

        NumberFormat format1 = NumberFormat.getNumberInstance();
         f1 = new JFormattedTextField(format1);
        f1.setValue(amount);
        rightFields.add(f1);
        label1.setLabelFor(f1);

        NumberFormat format2 = NumberFormat.getNumberInstance();
        format2.setMinimumFractionDigits(3);
        f2 = new JFormattedTextField(format2);
        f2.setValue(rate);
        f2.setColumns(10);
        rightFields.add(f2);
        label2.setLabelFor(f2);

        f3 = new JFormattedTextField(); // although this has no specific formatter, it is treated as int, even put in decimal will trim the decimal part.
        f3.setValue(amortization);
        f3.setColumns(10);
        rightFields.add(f3);
        label3.setLabelFor(f3);

        NumberFormat format4 = NumberFormat.getCurrencyInstance();
        f4 = new JFormattedTextField(format4);
        f4.setValue(Utility.getPaymentAmount(amount, rate, amortization));
        f4.setColumns(10);
        f4.setEditable(false);
        f4.setForeground(Color.red);
        rightFields.add(f4);
        label4.setLabelFor(f4);
        // highlight: I don't understand why has to use "value" exact wording, need to find its origin
        f1.addPropertyChangeListener("value",this);    // oo add"value" or without the propertyname both works.
        f2.addPropertyChangeListener("value",this);    // however, with propertyName "value" not anything else,won't trigger listener when just starting,
        f3.addPropertyChangeListener("value",this);    // without any property name will trigger listener from starting the program

        // add to the top "FormattedTextField" panel
        JPanel topPael = new JPanel(new BorderLayout());
        topPael.add(leftLabels, BorderLayout.CENTER);
        topPael.add(rightFields, BorderLayout.LINE_END);
        topPael.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        add(topPael,BorderLayout.CENTER);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("listen....");
        System.out.println(evt.getNewValue());
        Object source = evt.getSource();
        if (source  == f1 ) {
            //amount = (double) f1.getValue(); // oo can't just cast to double, if put 5, a exception will throw.
            amount = ((Number)f1.getValue()).doubleValue(); // cast to Number, then change to double  //Returns the value of the specified number as a {@code double}
        } else if (source == f2) {
            rate = ((Number) f2.getValue()).doubleValue();
        } else if (source == f3) {
            amortization = ((Number) f3.getValue()).intValue();
        }
        f4.setValue(Utility.getPaymentAmount(amount, rate, amortization));
    }

    // CASGUI
    private static void CASGUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new FormattedTextField_NumberFormat_Calculator());

        frame.pack();
        frame.setVisible(true);
    }

    // Main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(FormattedTextField_NumberFormat_Calculator::CASGUI);
    }

    private class MyInputVerifier extends InputVerifier{
        @Override
        public boolean verify(JComponent input) {
            return false;
        }
    }
}