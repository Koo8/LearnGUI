import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * instead of using a formatted text field,
 * as shown in FormattedTextFieldDemo, this example
 * uses input verification to validate user input.
 *
 * Referred to Oracle InputVerificationDemo.java
 * I didn't use shouldYieldFocus(), as only verify() is a mandatory method
 * when extends InputVerifier class
 */

public class InputVerifier_TextField_Calculator extends JPanel {
    MyInputVerifier verifier = new MyInputVerifier();

    JTextField amountField;
    JTextField rateField;
    JTextField yearField;
    JTextField paymentField;
    final double LOANAMOUNT = 100000;
    final double RATE = 7.5;
    final int YEAR = 30;
    double amount = LOANAMOUNT, rate= RATE;
    int year = YEAR;

    NumberFormat amountFormat, rateFormat, yearFormat;
    DecimalFormat paymentFormat;

    // constructor
    InputVerifier_TextField_Calculator() {
        super(new BorderLayout());

        // 4 labels
        JLabel amountLabel = new JLabel("Loan Amount (10,000 - 10,000,000:");
        JLabel rateLabel = new JLabel("rate (>=0)%:");
        JLabel yearLabel = new JLabel("Years (1-40):");
        JLabel paymentLabel = new JLabel("Monthly Payment:");

        // add labels to leftPane
        JPanel leftPane = new JPanel(new GridLayout(0,1));
        leftPane.add(amountLabel);
        amountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        leftPane.add(rateLabel);
        rateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        leftPane.add(yearLabel);
        yearLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        leftPane.add(paymentLabel);
        paymentLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        leftPane.setBorder(BorderFactory.createEmptyBorder(0,0,0,20));
        // 4 JtextField
        amountField = new JTextField(10);
        amountFormat = NumberFormat.getNumberInstance();
        amountField.setText(amountFormat.format(amount));

        // **note**: another way of instantiate a textfield with number being formatted
        rateFormat = NumberFormat.getNumberInstance();
        rateField = new JTextField(rateFormat.format(rate),10);
        rateFormat.setMinimumFractionDigits(3);

        yearField = new JTextField(10);
        yearFormat = NumberFormat.getIntegerInstance();  // or use getNumberInstance and use setParseIntegerOnly();
        yearField.setText(yearFormat.format(year));

        // use decimalFormat for the prefix and suffix defination
        paymentField = new JTextField(10);
        paymentFormat = (DecimalFormat) NumberFormat.getNumberInstance();
        paymentFormat.setMaximumFractionDigits(2);
        paymentFormat.setNegativePrefix("(");
        paymentFormat.setNegativeSuffix(")");
        // todo: change 0 to calculated number
        paymentField.setText(paymentFormat.format(Utility.getPaymentAmount(LOANAMOUNT,RATE,YEAR)));
        paymentField.setForeground(Color.RED);
        
        // add fields to right pane
        JPanel rightPane = new JPanel(new GridLayout(0, 1));
        rightPane.add(amountField);
        rightPane.add(rateField);
        rightPane.add(yearField);
        rightPane.add(paymentField);

        // add inputVerifier to all textFields
        amountField.setInputVerifier(verifier);
        rateField.setInputVerifier(verifier);
        yearField.setInputVerifier(verifier);
        paymentField.setInputVerifier(verifier);

        // add actionlistener to 3 fields
        amountField.addActionListener(verifier);
        rateField.addActionListener(verifier);
        yearField.addActionListener(verifier);

        //add to the main panel
        add(leftPane, BorderLayout.LINE_START);
        add(rightPane, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        InputVerifier_TextField_Calculator pane = new InputVerifier_TextField_Calculator();
        frame.setContentPane(pane);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(InputVerifier_TextField_Calculator::createAndShowGUI);
    }

    private class MyInputVerifier extends InputVerifier implements ActionListener {
        @Override
        // only triggered when you click "enter"
        public void actionPerformed(ActionEvent e) {
            System.out.println("in actionPerformed");
            JTextField source = (JTextField) e.getSource();
            verify(source);
            source.selectAll();
        }

        @Override
        // only triggered when you try to shift cursor to another field
        public boolean verify(JComponent input) {
            System.out.println("in verify");
            calculatePayment();
            return checkAllFields(input,true);
        }

        private void calculatePayment() {
            System.out.println("calculating payment");
            double amountInput = LOANAMOUNT, rateInput = RATE, paymentInput;
            int yearInput= YEAR;

            try{
                amountInput = amountFormat.parse(amountField.getText()).doubleValue();  // turn Number to double
                rateInput = rateFormat.parse(rateField.getText()).doubleValue();
                yearInput = yearFormat.parse(yearField.getText()).intValue();

            }catch (ParseException e) {
                getToolkit().beep();
                e.printStackTrace();
            }

            paymentInput = Utility.getPaymentAmount(amountInput,rateInput,yearInput);
            paymentField.setText(paymentFormat.format(paymentInput));
        }

        private boolean checkAllFields(JComponent input, boolean needToMakeChange) {
            if(input == amountField) {
                return checkAmountField(needToMakeChange);
            } else if (input == rateField) {
                return checkRateField(needToMakeChange);
            } else if (input == yearField) {
                return checkYearField(needToMakeChange);
            } else {    // elsewhere, should allow cursor to refocus to other fields
                return true; // shouldn't happen
            }
        }

        private boolean checkAmountField(boolean needToMakeChange) {
            boolean isValid = true;
            // define input format
            try {
                amount = amountFormat.parse(amountField.getText()).doubleValue();
            } catch (ParseException e) {
                isValid = false;
                getToolkit().beep();
                e.printStackTrace();
            }
            // define input range
            if(amount <10000 || amount > 100000000) {
                isValid = false; // make it false, so can't move away from this amountField
                if (needToMakeChange) {  // if need to change the input to a better valid number, reassign amount value;
                    if(amount<10000) amount = 10000;
                    if(amount>100000000) amount = 100000000 ;
                }
            }

            // format the text of this field
            amountField.setText(amountFormat.format(amount));
            amountField.selectAll();// "enter" will make the whole field selected and cursor at the very end of the text.
            return isValid;
        }

        private boolean checkRateField(boolean needToMakeChange) {
            boolean isValid = true;
            // check if format is right
            try {
                rate = rateFormat.parse(rateField.getText()).doubleValue();
            } catch (ParseException e) {
                isValid = false;
                getToolkit().beep();
                e.printStackTrace();
            }

            // define the right range >=0
            if(rate <0) {
                isValid = false;// make cursor stay in the same test field
                if(needToMakeChange) rate = 0;
            }
            // format the field
            rateField.setText(rateFormat.format(rate));
            return isValid;
        }


        private boolean checkYearField(boolean needToMakeChange) {
            boolean isValid = true;
            // check format
            try {
                year = yearFormat.parse(yearField.getText()).intValue();
            } catch (ParseException e) {
                isValid = false; // so that cursor stay here for another input
                getToolkit().beep();
                e.printStackTrace();
            }

            // define the range
            if( year <1 || year >40 ) {
                isValid = false;
                if(needToMakeChange) {
                    if (year<1) year = 1;
                    if (year>40) year = 40;
                }
            }

            // format the field
            yearField.setText(yearFormat.format(year));

            return isValid;
        }


    }
}
