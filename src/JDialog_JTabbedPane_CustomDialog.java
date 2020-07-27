import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class JDialog_JTabbedPane_CustomDialog extends JPanel {
    private JLabel label;
    private JFrame frame;
    private ImageIcon icon;
    private ValidateDialog validateDialog;
    private NonModalDialog nonModalDialog;

    //Constructor
    JDialog_JTabbedPane_CustomDialog(JFrame frame) {
        super(new BorderLayout());
        this.frame = frame;
        icon = Utility.createImageIcon(this, "images/middle.gif");

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        JPanel firstPanel = createFirstPanel();
        JPanel secondPanel = createSecondPanel();
        JPanel thirdPanel = createThirdPanel();
        tabbedPane.addTab("Simple Dialogs", null, firstPanel, "Show some simple JDialogs");
        tabbedPane.addTab("Customized Dialogs", null, secondPanel, "Show some customized JDialogs");
        tabbedPane.addTab("Icon dialogs", null, thirdPanel, "Dialogs with icons");

        label = new JLabel("Click the button to see different dialogs", JLabel.CENTER);

        add(tabbedPane, BorderLayout.CENTER);
        add(label, BorderLayout.PAGE_END);
    }
    //// THIRD TAB
    private JPanel createThirdPanel()
    {
        JPanel parentPanel = new JPanel(new BorderLayout());
        JPanel btnPanel = new JPanel();
        String[] text = {
                "Plain - No Icon",
                "Message Information Icon",
                "Question Icon",
                "Error Icon",
                "Warning Icon",
                "Custom Icon"
        };
        ButtonGroup buttonGroup = new ButtonGroup();
        JButton button = new JButton("Show it");

        createPanel(parentPanel, btnPanel, "Different Icons in Dialogs", text, buttonGroup, button,2);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = buttonGroup.getSelection().getActionCommand();
                if (command.equals("0")) {
                    JOptionPane.showMessageDialog(frame,"This is a plain message dialog.","Plain Dialog",JOptionPane.PLAIN_MESSAGE);
                } else if (command.equals("1")) {
                    JOptionPane.showMessageDialog(frame,"This is a information message dialog.","Information Message Dialog",JOptionPane.INFORMATION_MESSAGE);
                } else if (command.equals("2")) {
                    JOptionPane.showConfirmDialog(frame,"Is this good for a question?","Question Dialog", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null );
                } else if (command.equals("3")) {
                    JOptionPane.showMessageDialog(frame, "This is an Error message", " Error Dialog", JOptionPane.ERROR_MESSAGE, null);
                } else if (command.equals("4")) {
                    JOptionPane.showMessageDialog(frame,"This is a Warning dialog" , "Warning Dialog", JOptionPane.WARNING_MESSAGE, null);
                } else if (command.equals("5")) {
                    JOptionPane.showMessageDialog(frame, "Custom Icon dialog", "Custom Icon dialog", JOptionPane.INFORMATION_MESSAGE, icon);
                }
            }
        });
        return parentPanel;
    }

    //*** SECOND *** Tab
    private JPanel createSecondPanel() {
        JPanel parentPanel = new JPanel(new BorderLayout());
        JPanel btnPanel = new JPanel();
        String[] text = {
                "Input Dialog - Select From Options",
                "Input Dialog - User Input",
                "JOptionPane not Closed Automatically",
                "Custom Dialog for validating input",
                "Non Modal Dialog - without JOptionPane"
        };
        ButtonGroup buttonGroup = new ButtonGroup();
        JButton button = new JButton("Show it");

        createPanel(parentPanel, btnPanel, "More complicated Dialogs", text, buttonGroup, button, 1);
        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = buttonGroup.getSelection().getActionCommand();
                if (command.equals("0")) {
                    String[] options = {"blue", "pink", "red", "green"};
                    Object choose = JOptionPane.showInputDialog(frame,
                            "Which color do you choose?",
                            "A pre_defined list to choose from",
                            JOptionPane.QUESTION_MESSAGE,
                            icon,
                            options,  //oo if this is null, check the next command.equals("1"), no predetermined list to display, only user can input whatever
                            options[2]);
                    // after JOptionPane pop up the dialog, it also detects which button being clicked or if the "x" of the dialog being clicked ( is this for windowlistener to detect?)
                    // When the selection is changed, setValue is invoked, which generates a PropertyChangeEvent.
                    if (choose != null) {
                        label.setText("I like color " + choose);
                    } else {   // cancel button return "null"
                        label.setText("You just cancel the OptionPane");
                    }
                }
                if (command.equals("1")) {
                    // compare with the above item
                    // highlight: note, I set the parentComponent to "null" instead of "frame", the dialog pop up in the center of the screen, and a default parentComponent is used
                    String s = (String) JOptionPane.showInputDialog(null, "You can input your own text",
                            "you type in the field",
                            JOptionPane.INFORMATION_MESSAGE,
                            null, // when null, an icon from L&F will show
                            null,
                            "type your words");
                    if (s != null) {
                        label.setText(s);
                    } else {
                        label.setText("you cancel the dialog");
                    }
                } else if (command.equals("2")) {
                    // create a JOptionPane that won't close by default button clicking, this needs to call your own propertyChangeListener
                    // 3 steps: create a JOptionPane, create a JDialog to setContentPane as JOptionPane, to call propertychangeListener on the pane
                    JOptionPane pane = new JOptionPane("The only way to close this dialog is by clicking the buttons below" +
                            "\nYou can't close this with the \"x\" sign", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION);

                    //You can't use pane.createDialog() because that
                    //method sets up the JDialog with a property change
                    //listener that automatically closes the window
                    //when a button is clicked.
                    JDialog dialog = new JDialog(frame, "Click a button to close the dialog", true);
                    dialog.setContentPane(pane);
                    dialog.setPreferredSize(new Dimension(400, 300));
                    // this makes the "x" not closable
                    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);


                    /**
                     *  NOW, the JOptionPane not able to auto close the dialog after any button clicked (not action triggered), create your own property change listener for any action command*/
                    pane.addPropertyChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            // the following two lines warrants that pane.getValue has to be within the condition of value.equals(value), to avoid "uninitializedValue" runtime error
                            // This is the same a evt.getNewValue.
//                            Object s = pane.getValue();  // uninitializedValue when just open up the dialog , 0,1,2 for 3 buttons, can't hear the "X" of the dialog
//                            System.out.println(s);
                            Object value = evt.getPropertyName();
                            if ("value".equals(value)) {      // all 3 buttons has propertyname as "value"
                                // evt.getNewValue will distinguish 3 buttons as 0,1,2, however, evt can hear the x sign of dialog, but pane.getValue can hear it, that will trigger some runtime error
                                int n = (int) evt.getNewValue();
                                // System.out.println(pane.getValue() + " pane ");  // or use Pane.getValue() for 3 buttons
                                if (n == 0) {
                                    label.setText("YES GREAT");
                                } else if (n == 1) {
                                    label.setText("NOOOOOOO");
                                } else if (n == 2) {
                                    label.setText("Cancelled");
                                    dialog.dispose();
                                }
                            }
                        }
                    });
                    // like JFrame, these two lines must be at the end to consume all information above
                    dialog.pack();
                    dialog.setVisible(true);
                }
                // use custom dialog
                else if (command.equals("3")) {
                    if (validateDialog == null) {
                        validateDialog = new ValidateDialog(frame, label);
                    }
                    validateDialog.setVisible(true);
                } else if (command.equals("4")) {
                    if (nonModalDialog == null) {
                        nonModalDialog = new NonModalDialog(frame);
                    }
                    nonModalDialog.setVisible(true);
                }
            }
        });
        return parentPanel;
    }

    //*** FIRST *** Tab
    private JPanel createFirstPanel() {

        // this panel has 4 radio buttons , 1 label within a boxLayout, then join a button within a borderLayout
        JPanel btnPanel = new JPanel();
        ButtonGroup group = new ButtonGroup();
        String[] text = {
                "Message Dialog",
                "Confirmed Dialog",
                "Option Dialog, No My Icons",
                "OK/No/Cancel From Developer"
        };
        JPanel thePanel = new JPanel(new BorderLayout());
        JButton button = new JButton("Show it!");
        createPanel(thePanel, btnPanel, "Simple Dialogs", text, group, button, 1);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = group.getSelection().getActionCommand();
                if (command.equals("0")) {
                    // showMessageDialog returns void, so not be able to assign to anything
                    JOptionPane.showMessageDialog(frame, "This is a message dialog \n" +
                            "it has an icon \n" +
                            "and an OK button");
                } else if (command.equals("1")) {
                    int n = JOptionPane.showConfirmDialog(frame, "Confirmed Dialog, what's your answer?", "Confirmed Dialog", JOptionPane.YES_NO_OPTION);
                    // there are 3 option types -- Yes_Option, no_option and closed_option
                    if (n == JOptionPane.YES_OPTION) {
                        label.setText("Confirmed Yes");
                    } else if (n == JOptionPane.NO_OPTION) {
                        label.setText("Confirmed No");
                    } else if (n == JOptionPane.CLOSED_OPTION) {
                        label.setText("You didn't choose anything for Confirmed dialog");
                    }
                } else if (command.equals("2")) {
                    String[] answers = {"Yes Please", "No Thanks"};
                    int n = JOptionPane.showOptionDialog(frame,
                            "Option Dialog with Customized Button",
                            "Option Dialog with My Buttons",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            answers,
                            answers[0]);
                    if (n == JOptionPane.YES_OPTION) {
                        label.setText("Yes Please");
                    } else if (n == JOptionPane.NO_OPTION) {
                        label.setText("No Thanks");
                    } else {
                        label.setText("You forget to choose your answer");
                    }
                } else if (command.equals("3")) {
                    String[] answers = {"Yes take this", "No forget it", "Cancel my application"};
                    int n = JOptionPane.showOptionDialog(frame, "Can I get your application form? ", "Option Dialog with 3 buttons", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, answers, answers[2]);
                    if (n == JOptionPane.CANCEL_OPTION) {
                        label.setText("You just cancel my question!!!");
                    }
                }
            }
        });
        return thePanel;
    }

    // help create 1st and 2nd Tabbed panes
    private void createPanel(JPanel parentPanel, JPanel btnpanel, String panelLabel, String[] text, ButtonGroup group, JButton button, int column) {
        btnpanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        btnpanel.setLayout(new BoxLayout(btnpanel, BoxLayout.PAGE_AXIS));
        btnpanel.add(new JLabel(panelLabel));

        if (column == 1) {
            for (int i = 0; i < text.length; i++) {
                JRadioButton b = new JRadioButton(text[i]);
                b.setActionCommand("" + i);   // 1, 2,3,4;
                //  System.out.println(b.getActionCommand() + " for " + i+1 + " button") ;
                group.add(b);
                btnpanel.add(b);
                if (i == 0) {
                    b.setSelected(true);
                }
            }
        }  else if (column ==2) {
            JPanel grid = new JPanel(new GridLayout(0,2));
            int numperColumn = text.length/2; // total number of String has to be a even number
            for (int i = 0; i < numperColumn ; i++) {
                int secondBtnIndex = i + numperColumn;
                JRadioButton b = new JRadioButton(text[i]);
                JRadioButton b1 = new JRadioButton(text[secondBtnIndex]);
                b.setActionCommand("" + i);
                b1.setActionCommand("" + secondBtnIndex);
                group.add(b);
                group.add(b1);
                grid.add(b);
                grid.add(b1);
                if (i==0) {
                    b.setSelected(true);
                }
            }
            btnpanel.add(grid);
            // must add this line to make the label align with the grid from the left of the panel
            grid.setAlignmentX(0.0f);
        }
        parentPanel.add(btnpanel, BorderLayout.PAGE_START);
        parentPanel.add(button, BorderLayout.PAGE_END);
    }

    //GUI
    private static void CASGUI() {
        JFrame frame = new JFrame(" Dialog practice");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 400));

        JDialog_JTabbedPane_CustomDialog pane = new JDialog_JTabbedPane_CustomDialog(frame);
        pane.setOpaque(true);
        frame.setContentPane(pane);

        frame.pack();
        frame.setVisible(true);
    }


    //Main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CASGUI();
            }
        });
    }

    //// INNER DIALOG ClASS
    private class ValidateDialog extends JDialog implements PropertyChangeListener {
        //Fields
        String[] options = {"Enter", "Cancel"};
        JTextField textField = new JTextField(10);
        Object[] message = {"What is the answer? ", "The answer is \"love\"", textField};
        final String MAGICWORD = "love";
        JOptionPane pane;
        JLabel label;
        private String typedText = null;

        //Constructor

        ValidateDialog(JFrame frame, JLabel label) {
            super(frame, "Quiz", true);
            this.label = label;
            // message as the first parameter is an array object, it dispalys in seperate lines
            pane = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, null, options, null /* or options[0] see note below */);
            /**
             * highlight: for the line above:
             * the last parameter is options[0], which is "Enter" button. why hit enter key in this dialog,
             * it will automatically create action listener for textfield - same as writing within in listener: pane.setValue("Enter").
             * IF this parameter is "null", the actionlistener has to be explicitly created for textField to
             * tread "enter" key action as "Enter" button pressed
             */

            setContentPane(pane);
            // handle window closing properly
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            // instead of closing the window, we change the pane's value property
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    // TODO; oooo WHY?
                    System.out.println("MY window is listening too...");
                    // this alone won't close the dialog, needs the property listener for specific instructions
                    pane.setValue(JOptionPane.CLOSED_OPTION);
                }
            });
            pane.addPropertyChangeListener(this);
            textField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pane.setValue("Enter");
                }
            });
            // I don't see the difference betwee with and without this.
            // if there are multiple focusable area, this line should be added to indicate which area is the first focus area
//            addComponentListener(new ComponentAdapter() {
//                @Override
//                // invoked when component is made visible
//                public void componentShown(ComponentEvent e) {
//                    // make sure textField to get the first window focus
//                    textField.requestFocusInWindow();
//                }
//            });

            pack();
            //setVisible(true);
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String prop = evt.getPropertyName();
            System.out.println(" outside property Name is " + prop);
//            if (isVisible()
//                    && evt.getSource() == pane
//                    && JOptionPane.VALUE_PROPERTY.equals(prop))
//            {
            Object value = pane.getValue();
            System.out.println("inside value " + value);
            // reset value to uninitializedValue, so that click the same button again or hit enter key again will trigger property change event
            if (value != JOptionPane.UNINITIALIZED_VALUE) {
                pane.setValue(JOptionPane.UNINITIALIZED_VALUE);
                if ("Enter".equals(value)) {
                    String text = textField.getText();
                    if (text.equalsIgnoreCase(MAGICWORD)) {
                        label.setText("Congraduation! You got the magic word.");
                        dispose();
                    } else {
                        textField.setText(null);
                        JOptionPane.showMessageDialog(this, "You typed " + text + ", this is not the magic word.", "Oops, Do again", JOptionPane.ERROR_MESSAGE, null);
                        textField.requestFocusInWindow();
                    }
                } else {  // for all other JOptionPane values (Closed, Cancel)
                    label.setText("It's OK. You can go to next option");
                    dispose();
                }
            } else return;
        }
    }

    // Inner Dialog Class
    //JOptionPane creates JDialogs that are modal. To create a non-modal Dialog,
    // you must use the JDialog class directly.
    private class NonModalDialog extends JDialog implements ActionListener {

        //Fields
        JLabel label;
        JPanel mainPanel;
        JPanel buttonPanel;
        JButton button;

        // Constructor
        NonModalDialog(Frame frame) {
            super(frame, "Non Modal Dialog"/*,false*/);
            // create ContentPane = (label + buttonPanel(button))
           label = /**new JLabel("This is a non modal dialog" +
                    "\nYou need to click the button " +
                    "\nto close this.");**/  // no line break can be created this way
                    new JLabel("<html><p align=center> This is a non modal dialog.<br> Please click the button<br>to close the dialog.");

            label.setHorizontalAlignment(JLabel.CENTER);

            buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
            buttonPanel.add(Box.createHorizontalGlue());
            button = new JButton("Close");
            button.addActionListener(this);
            buttonPanel.add(button);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(0,0,5,5));

            mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(label,BorderLayout.CENTER);
            mainPanel.add(buttonPanel, BorderLayout.PAGE_END);

            setContentPane(mainPanel);
            setSize(new Dimension(200,200));
            mainPanel.setBorder(BorderFactory.createMatteBorder(10,5,10,5,Color.ORANGE));
            setLocationRelativeTo(null);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
             dispose();
        }
    }
}


