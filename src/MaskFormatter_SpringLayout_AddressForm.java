import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;

/**
 * refer to TextInputDemo.java from oracle  - the difference is I didn't use the getTextFieldForSpinnerEditor() for the JSpinner.
 * the helper method is in Utility.java
 *
 * When a component's getMaximumSize and getPreferredSize
 *  methods return the same value, SpringLayout interprets
 * this as meaning that the component should not be stretched.
 * JLabel and JButton are examples of components implemented this way.
 * The getMaximumSize method of some components,
 * such as JTextField, returns the value Integer.MAX_VALUE for the width
 * and height of its MAX size, indicating that the component can grow to any size.
 * Therefore, when the frame size grow, it will get extra space allocate to it
 * SEE how this program can set the (in boxlayout) the JPanel to a fixed Max size
 *
 */
public class MaskFormatter_SpringLayout_AddressForm extends JPanel implements ActionListener, FocusListener {

    // Fields
    String[] labelArray = {"Street Address", "City", "State", "Zip Code"};
    JTextField streetField, cityField;
    JSpinner stateSpinner;
    JFormattedTextField zipField;
    boolean isCleared = true;
    JLabel displayLabel;
    private String[] stateList =  {
            "Alabama (AL)",
            "Alaska (AK)",
            "Arizona (AZ)",
            "Arkansas (AR)",
            "California (CA)",
            "Colorado (CO)",
            "Connecticut (CT)",
            "Delaware (DE)",
            "District of Columbia (DC)",
            "Florida (FL)",
            "Georgia (GA)",
            "Hawaii (HI)",
            "Idaho (ID)",
            "Illinois (IL)",
            "Indiana (IN)",
            "Iowa (IA)",
            "Kansas (KS)",
            "Kentucky (KY)",
            "Louisiana (LA)",
            "Maine (ME)",
            "Maryland (MD)",
            "Massachusetts (MA)",
            "Michigan (MI)",
            "Minnesota (MN)",
            "Mississippi (MS)",
            "Missouri (MO)",
            "Montana (MT)",
            "Nebraska (NE)",
            "Nevada (NV)",
            "New Hampshire (NH)",
            "New Jersey (NJ)",
            "New Mexico (NM)",
            "New York (NY)",
            "North Carolina (NC)",
            "North Dakota (ND)",
            "Ohio (OH)",
            "Oklahoma (OK)",
            "Oregon (OR)",
            "Pennsylvania (PA)",
            "Rhode Island (RI)",
            "South Carolina (SC)",
            "South Dakota (SD)",
            "Tennessee (TN)",
            "Texas (TX)",
            "Utah (UT)",
            "Vermont (VT)",
            "Virginia (VA)",
            "Washington (WA)",
            "West Virginia (WV)",
            "Wisconsin (WI)",
            "Wyoming (WY)"
    };

    // constructor - set the layout of components in the panel
    MaskFormatter_SpringLayout_AddressForm() {
        // first layer is left-right layout
        // second layer is top-bottom layout

        // use BoxLayout can control the maximumSize of components  - other layoutManager can't do this
        setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
        //create a leftPanel with maxSize constraints
        JPanel leftPane = new JPanel() {
            @Override
            public Dimension getMaximumSize() {
                Dimension pref = getPreferredSize();
                return new Dimension(Integer.MAX_VALUE, pref.height);
            }
        };
        // set leftPane with boxLayout of top-bottom layout
        leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.PAGE_AXIS));
        // set top part of leftPane with SpringLayout with 4 labels and 4 textFields
        leftPane.add(createLabelsAndFields());
        leftPane.add(createButtons());
        leftPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,10));

        add(leftPane);
        add(createAddressDisplay());

    }

    /**
     * use SpringLayout
     * create 4 labels and 4 different textFields (one JSpinner for state)
     * @return
     */
    private Component createLabelsAndFields() {
        JPanel mainForm = new JPanel(new SpringLayout());
        // I can easily use for loop to add 4 labels, how to add 4 different textFields
        // I should predefine all 4 textfield first
        streetField = new JTextField(20);
        cityField = new JTextField(20);
        // create a JSpinner with ListModel(list or array), it automatically create a ListEditor(usually use JFormattedTextField)
        stateSpinner = new JSpinner(new SpinnerListModel(stateList));
        zipField = new JFormattedTextField(zipCodeFormatter("#####"));
        JComponent[] coms = {streetField, cityField,stateSpinner, zipField};
        for (int i = 0; i <labelArray.length ; i++) {
            JLabel label = new JLabel(labelArray[i], SwingConstants.TRAILING);
            mainForm.add(label);
            mainForm.add(coms[i]);
            coms[i].addFocusListener(this);
            label.setLabelFor(coms[i]);
        }
        // define Springlayout layout using SpringUtilities.java
        SpringUtilities.makeCompactGrid(mainForm,labelArray.length,2,5,5,5,5);
        return mainForm;

    }

    private MaskFormatter zipCodeFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter;
    }
    // todo: redo button layout
    private Component createButtons() {
        //
        JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        JButton setAddressBtn = new JButton("Set Address");
        JButton clearBtn = new JButton("Clear Address");
        buttonPane.add(setAddressBtn);
        buttonPane.add(clearBtn);
        //add listeners
        setAddressBtn.addActionListener(this);
        clearBtn.addActionListener(this);
        // to distinguish which btn is called
        // highlight: since setAddressBtn doesn't
        // highlight: have setactionCommand, its getActionCommand is its text "Set Address"
        clearBtn.setActionCommand("clear");
        setAddressBtn.setActionCommand("address");
        return buttonPane;
    }

    private Component createAddressDisplay() {
        JPanel displayPane = new JPanel(new BorderLayout());
        displayLabel = new JLabel("no address input");
        displayLabel.setFont(getFont().deriveFont(Font.ITALIC,16));
        displayLabel.setHorizontalAlignment(JLabel.CENTER);
        //displayLabel.setHorizontalAlignment(SwingConstants.CENTER);
        displayPane.add(displayLabel, BorderLayout.CENTER) ;
        displayPane.add(new JSeparator(JSeparator.VERTICAL), BorderLayout.LINE_START);
        displayPane.setPreferredSize(new Dimension(200, 150));
        return displayPane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String btn = e.getActionCommand();
        if (btn.equals("clear")) {
            isCleared = true;
            streetField.setText("");
            cityField.setText("");
            stateSpinner.setValue(stateList[0]);
            zipField.setValue(null);
            displayLabel.setText("no address input");
            displayLabel.setFont(getFont().deriveFont(Font.ITALIC,16));

        }
        else if (btn.equals("address")) {
            isCleared = false;
            updateDisplay();
        }


    }

    private void updateDisplay() {

            // format the display text
            String address = streetField.getText();
            String city = cityField.getText();
            String state = (String) stateSpinner.getValue();
            String zip = zipField.getText();

            if (address == null || address.equals("")){
                address = "<em>No address provided</em>";
            }
            if(city== null || city.equals("")) city="<em>No city provided</em";
            if(state == null || state.equals("")) {
                state = "<em>No state selected</em>";
            }  else {
                state = state.substring(state.indexOf('(')+1, state.indexOf('(') + 3) ;    // only the abbreviation part is used

            }
            if(zip == null || zip.equals("")) {
                zip = "<em>No zipcode provided</em>";
            }
            // use html to build the string
            StringBuilder sb = new StringBuilder();
            sb.append("<html><p align=center>");
            sb.append(address);
            sb.append("<br>");
            sb.append(city);
            sb.append(" ");
            sb.append(state);
            sb.append(" ");
            sb.append(zip);
            sb.append("</p></html>");

            displayLabel.setText(sb.toString());
            displayLabel.setFont(getFont().deriveFont(Font.PLAIN,16));

    }

    // create GUI JFrame
    private static void GUI() {
        JFrame frame = new JFrame();;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MaskFormatter_SpringLayout_AddressForm pane = new MaskFormatter_SpringLayout_AddressForm();
        frame.setContentPane(pane);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // turn off bold fonts of DefaultMetalTheme
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                GUI();
            }
        });
    }

    @Override
    public void focusGained(FocusEvent e) {
        System.out.println("in focus");
        Component c =  e.getComponent();
        if(c instanceof JTextField) {
            System.out.println("in focus in jTextField");
            ((JTextField)c).selectAll();
        }
        // for JformattedTextField, need call from event dispatch thread to workaround the bug
        if (c instanceof JFormattedTextField) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ((JFormattedTextField)c).selectAll();
                }
            });
        }

    }

    @Override
    public void focusLost(FocusEvent e) {

    }
}
