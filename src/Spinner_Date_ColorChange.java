import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;


/**
 * this program use SpringLayout
 * use CyclingSpinnerListModel.java as a helper class for cycle through NumberModel
 * 3 Models : SpinnerListModel, SpinnerDateModel, SpinnerNumberModel, coresponding to 3 spinnerEditors
 * ListEditor, NumberEditor, DateEditor
 * Eidtor use JFromattedTextField and JLabel
 * ChangeListener added to Spinner
 * JFormattedtextField and Spinner can both be setBorder etc.
 * use Calendar to get current date or any other date using add(),
 *
 */
public class Spinner_Date_ColorChange extends JPanel implements ChangeListener {
    private JSpinner spinner, spinnerColor;
    // get  current calender instance, will be used for 2nd and 3rd spinners
    private Calendar calendar = Calendar.getInstance();
    // for link year model with month model
    private boolean cycleModel = false;
    CyclingSpinnerListModel myModel;


    // constructor
    private Spinner_Date_ColorChange() {
        super(new SpringLayout());

        // make 3 pairs of JLabel and JSpinner pairs and add them to the Jpanel
        ///// FIRST *****
        // Fields
        // label array
        String[] labels = {"Months ", "Years ", "Color Change", "Another Date "};
        spinner = createLabeledSpinner(this, labels[0], createMonthModel(true));

        if (spinner.getModel() instanceof CyclingSpinnerListModel) {
            cycleModel = true;
            myModel = (CyclingSpinnerListModel) spinner.getModel();
        }

        // after spinner is create, I can define the JFormattedTestField that comes with the Editor
        // the formatted text field used by the JSpinner's editor.
        JFormattedTextField field = Utility.getTextFieldForSpinnerEditor(spinner);
        //field.addPropertyChangeListener();  // you can add listeners
        field.setColumns(10);
        field.setHorizontalAlignment(SwingConstants.TRAILING);

        ///// SECOND *****
        spinner = createLabeledSpinner(this, labels[1], createYearModel());
        // make the year display formatted without a thousand seperator
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner,"####");
        spinner.setEditor(editor);

        ///// Third *****
        spinner =createLabeledSpinner(this, labels[2],new GrayNumberModel(40));
        spinner.setEditor(new GrayEditor(spinner));

        ///// Fourth *****
        spinner = createLabeledSpinner(this, labels[3], createDateModel());
        JSpinner.DateEditor editor1 = new JSpinner.DateEditor(spinner,"MM/yyyy"); // note: yyyy is right, can't use YYYY (week year).
        spinner.setEditor(editor1);
        field = Utility.getTextFieldForSpinnerEditor(spinner);
        if(field != null) {
            field.setHorizontalAlignment(SwingConstants.TRAILING);
            field.setBorder(BorderFactory.createEmptyBorder(10,1,1,1));
        }

        spinner.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        spinner.addChangeListener(this); // note: spinner can't add propertyChangeListener.


        // initially make the color show season color
        changeColorForSeasons(createDateModel().getDate());

        // layout the components
        SpringUtilities.makeCompactGrid(this, labels.length, 2, 5, 5, 5, 5);
        setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
    }

    private void changeColorForSeasons(Date date) {
        // change the font color of JFormattedTextField of Another Day
        JFormattedTextField field = Utility.getTextFieldForSpinnerEditor(spinner); // the last spinner definition
        // find month value --- set the calendar to "date", - get the corresponding month value
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        switch(month) {
            case 2:
            case 3:
            case 4:
                field.setForeground(Color.RED);
                break;
            case 5:
            case 6:
            case 7:
                field.setForeground(Color.CYAN);
                break;
            case 8:
            case 9:
            case 10:
                field.setForeground(Color.BLUE);
                break;
            default: //for 1,12,11
                field.setForeground(Color.ORANGE);
        }
    }


    private JSpinner createLabeledSpinner(Container parent, String labelName, SpinnerModel spinnerModel) {
        JLabel label = new JLabel(labelName);
        JSpinner spinner = new JSpinner(spinnerModel);
        label.setLabelFor(spinner);
        parent.add(label);
        parent.add(spinner);
        return spinner;
    }

    private SpinnerDateModel createDateModel() {
        // get currentDate, earliestDate, lastestDate
        Date currentDate = calendar.getTime();

        calendar.add(Calendar.YEAR,-1);  // move calendar one year back
        Date earliestDate = calendar.getTime();

        calendar.add(Calendar.YEAR,+2); // move calendar two more years advanced - that is one year more than currentYear
        Date lastestDate = calendar.getTime();

        SpinnerDateModel model = new SpinnerDateModel(currentDate,earliestDate,lastestDate,Calendar.YEAR);
        return model;
    }

    private SpinnerNumberModel createYearModel() {
        int currentYear = calendar.get(Calendar.YEAR);
        System.out.println("currentYear is " + currentYear);
        SpinnerNumberModel model = new SpinnerNumberModel(currentYear,currentYear-100,currentYear+100,1);
        // link the year model with month model, so that when month start a new cycle, the year can change accordingly
        if(cycleModel) {
            myModel.setLinkedModel(model);
        }
        return model;

    }

    private SpinnerListModel createMonthModel( boolean cycleMonth) {
        //This method returns 13 elements since
        //java.util.Calendar#UNDECIMBER Calendar.UNDECIMBER is supported.
        // the last string is "";
        SpinnerListModel model = null;
        String[] monthArray = new DateFormatSymbols().getMonths();
        String[] newMonthArray = null;
        // remove the last string
        int lastIndex = monthArray.length-1;

        if (monthArray[lastIndex] == null || monthArray[lastIndex].length()<= 0) {
            // make a new string[] and copy part of the monthArray
            newMonthArray = new String[lastIndex];
        }
        System.arraycopy(monthArray,0,newMonthArray,0,lastIndex);

        // choose cycleMonth or not
        if(cycleMonth) {
            model = new CyclingSpinnerListModel(newMonthArray);
        } else {
            // default spinnerListModel don't cycle continually through the list.
            model = new SpinnerListModel(newMonthArray);
        }

        return model;
    }

    private static void GUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Spinner_Date_ColorChange pane = new Spinner_Date_ColorChange();
        pane.setOpaque(true);
        frame.setContentPane(pane);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Utility.turnOffTheBoldFont();
                GUI();
            }
        });
    }

    @Override
    public void stateChanged(ChangeEvent e) {
//        Object source = e.getSource();
//        System.out.println("stateChanged source is " + source);
        // get updated date value through getModel(), then use changeColorForSeasons(date) to make color changes
        SpinnerDateModel model = (SpinnerDateModel) spinner.getModel();
        Date date = model.getDate(); // only SpinnerDateModel has this method
        //Date date = createDateModel().getDate();  // this code only get the real time date
        //System.out.println(" state changed, date " + date);
        changeColorForSeasons(date);
    }

    private class GrayNumberModel extends SpinnerNumberModel {
        // I need to get the Color value from this model, pass it to the GrayEditor
        GrayNumberModel(int defaultColor) {
            super(defaultColor,0, 255,5);
        }
        public Color getColor() {
            Number value = (Number) getValue();
            int num = value.intValue();
            Color color = new Color(num,num ,num );
            return color;
        }
    }
    // if use JFormattedTextField, the color number will show, use JLabel, only background change color, the color int won't display
     // to use JLabel for editor, a subclass of JLabel is needed, use spinner parameter for all needed info
    private class GrayEditor extends JLabel implements ChangeListener{
        // constructor
        GrayEditor(JSpinner spinner) {
            setOpaque(true);

            // get color from GrayNumberModel
            GrayNumberModel model = (GrayNumberModel) spinner.getModel();
            Color color = model.getColor();
            setBackground(color);
            spinner.addChangeListener(this);
            updateToolTips(spinner);
        }
        public void updateToolTips(JSpinner spinner) {
            String tips = spinner.getToolTipText();
            if (tips == null) {
                // get the color int value, set it as tool tips
                GrayNumberModel model = (GrayNumberModel) spinner.getModel();
                Number value = (Number) model.getValue();
                int num = value.intValue();
                setToolTipText("(" + num + " " + num + " " + num +")");
            }
            else {
                setToolTipText(tips);
            }
            
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            JSpinner spinner = (JSpinner) e.getSource();
            GrayNumberModel model = (GrayNumberModel) spinner.getModel();
            Color color = model.getColor();
            setBackground(color);  // JComponent method
            updateToolTips(spinner);

        }
    }
}
