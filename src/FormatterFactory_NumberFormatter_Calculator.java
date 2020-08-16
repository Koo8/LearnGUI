import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.text.ParseException;


/**
 *  This program use JFormattedTextField(AbstractFormatterFactory) constructor
 *  This is the most flexible approach.
 *  It is useful when you want to associate more than one formatter with a field
 *  or add a new kind of formatter to be used for multiple fields.
 *
 *  You can set a field's formatter factory either by
 *     1. creating the field using a constructor that takes a formatter factory argument,
 *     2. calling the setFormatterFactory method on the field.
 *  To create a formatter factory, you can often use an instance of oo DefaultFormatterFactory oo class.
 *  A DefaultFormatterFactory object enables you to specify four formatters returned
 *      1. when a value is being edited, (has focus)
 *      2. when a value is not being edited,   ( has no focus)
 *      3. when a value has a null value,
 *      4. default formatter.
 *
 *     "  public DefaultFormatterFactory(
 *                    JFormattedTextField.AbstractFormatter defaultFormat,
 *                    JFormattedTextField.AbstractFormatter displayFormat,
 *                    JFormattedTextField.AbstractFormatter editFormat,
 *                    JFormattedTextField.AbstractFormatter nullFormat) {
 *         this(defaultFormat, displayFormat, editFormat, null);
 *     } "
 */

public class FormatterFactory_NumberFormatter_Calculator extends JPanel implements PropertyChangeListener {

    // Fields
    NumberFormat amountFormat,rateFormat,yearFormat,paymentFormat,
            amountEditFormat,rateEditFormat;
    double amount = 100000,rate = 0.075;
    int year =30;
    JFormattedTextField amountField, rateField, yearField, paymentField;

    // Constructor
    FormatterFactory_NumberFormatter_Calculator() {
        super(new BorderLayout());
        setUpFormat();
        double payment = Utility.getPaymentAmount(100000,7.5,30);

        // two panes for left(label) and right(fields)
        JPanel leftPane = new JPanel(new GridLayout(0, 1));
        JPanel rightPane = new JPanel(new GridLayout(0, 1));

        // put 4 labels into leftPane
        JLabel amountLabel = new JLabel("Loan Amount  ");
        amountLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        JLabel rateLabel = new JLabel("APR %  ");
        JLabel yearLabel = new JLabel("Years  ");
        JLabel paymentLabel =new JLabel("Payment  ");

        // add 4 labels to leftPane
        leftPane.add(amountLabel);
        leftPane.add(rateLabel);
        leftPane.add(yearLabel);
        leftPane.add(paymentLabel);

        // put 4 JFormattedTestFields into right Pane
           /// create amountField textField with DefaultFormatterFactory constructor
        amountField = new JFormattedTextField(
                // use DefaultFormatterFactory as parameter
                new DefaultFormatterFactory(
                        new NumberFormatter(amountFormat),   // default format - for default and null format
                        new NumberFormatter(amountFormat),    // display format  - no focus
                        new NumberFormatter(amountEditFormat)   // edit format   - has focus
                )
        );
        amountField.setValue(amount);
        amountField.setColumns( 10);
        amountField.addPropertyChangeListener("value",this);

            /// create rateField with customized formatter for editing state
            /// create a sublass of NumberFormatter, override two methods : value to string (grab this string with listener) and string to value
            /// This subclass overrides the valueToString and stringToValue methods of NumberFormatter so that they convert the displayed number
            /// to the value actually used in calculations, and convert the value to a number.
            /// this subclass changed 0.075 number display to 7.5, which is needed for payment calculation.
        NumberFormatter rateEditFormatter = new NumberFormatter(rateEditFormat) {
            // when the rateField is in focus
            // this rateEditFormatter is called, at this stage, only the "valuetoString" is called
            // when any changes is made, the "string to value" is called.
            @Override
            public String valueToString(Object value) throws ParseException {
                // converting between Number and double class
                Number num = (Number) value;
                System.out.println("num before change in \"value to string\"" + num);
                if (num != null) {
                    num = num.doubleValue()*100; // Number can't use * operator
                    System.out.println("num after change in \"value to string\"" + num);
                }
                return super.valueToString(num);
            }

            @Override
            public Object stringToValue(String text) throws ParseException {
                Number num = (Number) super.stringToValue(text);
                System.out.println("num before convert in \"String to Value \"" + num);
                if(num != null) {
                    num = num.doubleValue()/100;
                    System.out.println("num after convert in \"String to Value \"" + num);
                }
                return num;
            }
        };

        rateField = new JFormattedTextField(
                new DefaultFormatterFactory(
                        new NumberFormatter(rateFormat),
                        new NumberFormatter(rateFormat),
                        rateEditFormatter
                )
        );
        rateField.setColumns(10);
        rateField.setValue(rate); // use 0.075 not 7.5 is for the testify the use of customized formatter "rateEditFormatter"
        rateField.addPropertyChangeListener("value",this);

        // create yearField with default interger formatter
        yearField = new JFormattedTextField();
        yearField.setColumns(10);
        yearField.setValue(year);
        yearField.addPropertyChangeListener("value",this);

        // create paymentField
        paymentField = new JFormattedTextField(paymentFormat);
        paymentField.setColumns(10);
        paymentField.setValue(payment);
        paymentField.setEditable(false);
        paymentField.setForeground(Color.red);

        // add 4 fields into rightpane
        rightPane.add(amountField);
        rightPane.add(rateField);
        rightPane.add(yearField);
        rightPane.add(paymentField);

        // assign labels to fields
        amountLabel.setLabelFor(amountField);
        rateLabel.setLabelFor(rateField);
        yearLabel.setLabelFor(yearField);
        paymentLabel.setLabelFor(paymentField);

        // add to the pane
        add(leftPane,BorderLayout.CENTER);
        add(rightPane, BorderLayout.LINE_END);
        setOpaque(true);
        setBorder(BorderFactory.createEmptyBorder(20,10,20,10));
    }

    private void setUpFormat() {
        // for amountField
        amountFormat = NumberFormat.getCurrencyInstance();
        amountFormat.setMinimumFractionDigits(0);
        amountEditFormat = NumberFormat.getNumberInstance();

        rateFormat = NumberFormat.getPercentInstance();
        rateFormat.setMinimumFractionDigits(2);
        rateEditFormat = NumberFormat.getNumberInstance();
        rateEditFormat.setMinimumFractionDigits(2);

        // not format set up for yearField, it use a defaultFormat detected by its setValue(Object).

        // paymentfield only have one formatter
        paymentFormat = NumberFormat.getCurrencyInstance();

    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object source = evt.getSource();
        if(source == amountField) {
            //highlight: If your Object represents a number, eg, such as an Integer, you can cast it to a Number then call the doubleValue() method.
            // highlight: you can't cast an Object to double.
            amount = ((Number)amountField.getValue()).doubleValue();
        } else if (source == rateField) {
            rate = ((Number)rateField.getValue()).doubleValue();
        } else if (source == yearField) {
            year = ((Number)yearField.getValue()).intValue();
        }

        paymentField.setValue(Utility.getPaymentAmount(amount,rate*100,year));

    }

    private static void GUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FormatterFactory_NumberFormatter_Calculator pane = new FormatterFactory_NumberFormatter_Calculator();
        frame.setContentPane(pane);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                GUI();
            }
        });
    }
}
