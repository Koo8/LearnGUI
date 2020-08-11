import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Pattern Syntax DateFormat
 * You can use the following symbols in your formatting pattern:
 *
 * G	Era designator (before christ, after christ)
 * y	Year (e.g. 12 or 2012). Use either yy or yyyy.
 * M	Month in year. Number of M's determine length of format (e.g. MM, MMM or MMMMM)
 * d	Day in month. Number of d's determine length of format (e.g. d or dd)
 * h	Hour of day, 1-12 (AM / PM) (normally hh)
 * H	Hour of day, 0-23 (normally HH)
 * m	Minute in hour, 0-59 (normally mm)
 * s	Second in minute, 0-59 (normally ss)
 * S	Millisecond in second, 0-999 (normally SSS)
 * E	Day in week (e.g Monday, Tuesday etc.)
 * D	Day in year (1-366)
 * F	Day of week in month (e.g. 1st Thursday of December)
 * w	Week in year (1-53)
 * W	Week in month (0-5)
 * a	AM / PM marker
 * k	Hour in day (1-24, unlike HH's 0-23)
 * K	Hour in day, AM / PM (0-11)
 * z	Time Zone
 * '	Escape for text delimiter
 * '	Single quote
 */

public class JFormattedTextField_SubClass_InputVerifier {

    // constructor
    JFormattedTextField_SubClass_InputVerifier() {
        // create the GUI
        JFrame frame = new JFrame();
        frame.setPreferredSize( new Dimension(300, 300));

        Box form = Box.createVerticalBox();
        //  label - field - label - field  structure
        form.add(new JLabel("Date & Time"));
        CustomDateTimeField dtField = new CustomDateTimeField(new Date());
        form.add(dtField);
        form.add(new JLabel("Amount in Money Form"));
        JFormattedTextField moneyField = new JFormattedTextField(NumberFormat.getCurrencyInstance());
        moneyField.setValue(100000);
        form.add(moneyField);

        frame.add(form);
        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JFormattedTextField_SubClass_InputVerifier::new);
    }


}
class CustomDateTimeField extends JFormattedTextField {
    //Constructor
    CustomDateTimeField(Date date) {
        // define format when just start the program
        // any referrence within the super() should be from static fields or static block
        // highlight: the Static members can be used inside the super() as parameter,
        // because static members exist before constructor

        // get it formatted from the start
        super(MyCustomVerifier.getDefaultFormat());
        this.setInputVerifier(new MyCustomVerifier(this ));
        this.setValue(date);

    }
}

class MyCustomVerifier extends InputVerifier{
    JFormattedTextField field;
    Date date;
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd hh'h':mm'm'");

    // when there is a set of format to use, see below, use static block to instantiate the variables
//    private static List<SimpleDateFormat> validForms =
//            new ArrayList<SimpleDateFormat>();
//    // static block comes first than all constructors including
//    // DateTimeField so that getDefaultFormat won't result in outOfBound
//    static {
//        System.out.println("in static");
//        validForms.add(new SimpleDateFormat("dd-MMM-yyyy HH'h':mm'm'"));
//        validForms.add(new SimpleDateFormat("dd-MMM-yyyy HH:mm"));
//    }
    // then parse each of the format using a for loop

    public MyCustomVerifier(JFormattedTextField field) {
        // use constructor to connect with the textfield to get its value or text
        this.field = field;
    }

    @Override
    public boolean verify(JComponent input) {
        boolean result = false;

        if(input == field) {
            try {
                date = format.parse(field.getText());
                result = true;
            } catch (ParseException e) {
                System.out.println("wrong input of date");
                e.printStackTrace();
            }
        }
        if (result) field.setValue(date);      // note: use setValue oo
        else field.setText("use yyyy-MM-dd HH'h':mm'm' pattern please");   // use setText for a string descripline.

        return result;
    }

    public static SimpleDateFormat getDefaultFormat() {
        return format;
    }
}
