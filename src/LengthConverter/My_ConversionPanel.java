package LengthConverter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

public class My_ConversionPanel extends JPanel
        implements ActionListener, ChangeListener, PropertyChangeListener {   // parameter variables
    My_ConverterRangeModel theModel;  // panel only need to get all values from ConverterRangeModel(the lead model)
    My_Unit[] units;
    MyConverter converter;
    String title;
    // layout variables
    JFormattedTextField textField;
    JComboBox comboBox;
    JSlider slider;
    NumberFormat format;
    NumberFormatter formatter;

    final static boolean MULTICOLORED = false;
    final static int MAX = 10000;

    // CONSTRUCTOR
    My_ConversionPanel(MyConverter converter, My_ConverterRangeModel model, My_Unit[] unitArray, String theTitle) {
        theModel = model;
        units = unitArray;
        this.converter = converter;
        title = theTitle;

        // set colored-background
        if (MULTICOLORED) {
            setOpaque(true);
            setBackground(new Color(100, 130, 255));
        }
        // create titled border:
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(title),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        //create JFormattedTextField
        format = NumberFormat.getNumberInstance();//use NumberFormat
        format.setMaximumFractionDigits(2); // allow maximum 2 decimal
        formatter = new NumberFormatter(format);
        formatter.setAllowsInvalid(true); //  temperarily allow invalid user input
        formatter.setCommitsOnValidEdit(true); // any valid input will immediate trigger result change
        // textField = new JFormattedTextField(format);  // not using this.
        // because format doesn't have the immediate change result feature like the formatter defined
        textField = new JFormattedTextField(formatter);
        textField.setColumns(10);
        textField.setValue(theModel.getDoubleValue()); // textfield get value from the lead slider
        textField.addPropertyChangeListener(this);

        // create comboBox
        comboBox = new JComboBox(units);    // can't do this, because units is not string array
        // the first item is selected by default // setSelectedIndex(0)
        comboBox.addActionListener(this);
        // need multiplier to calculate for followerSlider values
        theModel.setMultiplier(units[0].multiplier); // when starting, use the first item as the multiplier, as first item is selected by default.

        // create a slider
        slider = new JSlider(theModel);
        theModel.addChangeListener(this);

        // Group textField and slider inside a JPanel,
        // since two ConversionPanels will stacked on top of each other,
        // set this grouped Jpanel a fixed width size will help nicely
        //align the two conversionPanels
        JPanel leftPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                // fix its width
                return new Dimension(150, super.getPreferredSize().height);
            }

            @Override
            public Dimension getMaximumSize() {
                return getPreferredSize();
            }

            @Override
            public Dimension getMinimumSize() {
                return getPreferredSize();
            }
        };
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        leftPanel.add(textField);
        leftPanel.add(slider);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));

        // put comboBox inside a Jpanel, make the width sufficiently wide
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
        rightPanel.add(comboBox);
        rightPanel.add(Box.createHorizontalStrut(100));

        // put everything together
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        add(leftPanel);
        add(rightPanel);
        leftPanel.setAlignmentY(TOP_ALIGNMENT);
        rightPanel.setAlignmentY(TOP_ALIGNMENT);
    }

    // fetch the multiplier from converterRangeModel, and pass it on to MyConverter
    public double getMultiplier() {
        return theModel.getMultiplier();
    }

    @Override   // listen to combobox
    public void actionPerformed(ActionEvent e) {
        //which item selected, get its multiplier, pass it to slider to calculte new max
        int i = comboBox.getSelectedIndex();
       
        // this index is the units[] index
        double muliplier = units[i].multiplier;
        //set leadmodel new multiplier
        theModel.setMultiplier(muliplier);
        // to resetMaxValue of the theModel, this is doone after knowing the other model's multiplier,
        // therefore, this method is constructed in the MyConverter class
        converter.resetMaxValueThroughTwoMultipliers(false);
    }

    @Override   // for listen to textField
    public void propertyChange(PropertyChangeEvent evt) {
        // "value" is the propertyname of textField input
        if ("value".equals(evt.getPropertyName())) {
            Number value = (Number) evt.getNewValue();
            theModel.setDoubleValue(value.doubleValue());
        }
    }

    @Override  // slider
    public void stateChanged(ChangeEvent e) {
        // when slider changed, grab min, max and value of slider, pass them to textField
        int min = theModel.getMinimum();
        int max = theModel.getMaximum();
        double value = theModel.getDoubleValue();

        NumberFormatter formatter = (NumberFormatter) textField.getFormatter();
        formatter.setMinimum(min);
        formatter.setMaximum(max);
        textField.setValue(value);
    }
}
