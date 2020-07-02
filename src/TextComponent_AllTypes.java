import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;

public class TextComponent_AllTypes extends JPanel implements ActionListener {

    //Fields
    private static final String text = "text";
    private static final String passWord = "password";
    private static final String date = "date";
    private static final String buttonString = "button";
    private String newline = "\n";

    private JTextField textField;
    private JPasswordField passwordField;
    private JFormattedTextField dateField;
    private JLabel actionLabel;

    private int counter = 0;

    //constructor
    public TextComponent_AllTypes() {
        setLayout(new BorderLayout());
        // the outer layer JPanel has two Panels
        JPanel leftPanel = new JPanel(new BorderLayout()); // up-down layout defined by Page_start and Center
        JPanel rightPanel = new JPanel(new GridLayout(1,0));
        rightPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Styled Text"), BorderFactory.createEmptyBorder(5,5,5,5)));

        addToLeftPanel(leftPanel);
        addToRigthPanel(rightPanel);

        add(leftPanel, BorderLayout.LINE_START);
        add(rightPanel, BorderLayout.LINE_END);

    }

    private void addToLeftPanel(JPanel leftPanel) {

        // First Part -- PAGE_START part of BorderLayout
        JPanel textFieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        // note: textFieldsPanel has two parts, top is the 3 textfields, bottom is a label that can listen to action
              /// ------    Top 3 textfields
        c.anchor = GridBagConstraints.EAST;
                   ///// since all 3 textfields and 3 labels have the same constraints, so put them into []s respectively, then go through the array items to add each to the textFieldsPanel
        textField = new JTextField(10);
        textField.setActionCommand(text);
        textField.addActionListener(this);
       passwordField = new JPasswordField(10);
        passwordField.setActionCommand(passWord);
        passwordField.addActionListener(this);

        // todo: not sure why JformattedTextField listener is not working
       dateField = new JFormattedTextField(Calendar.getInstance().getTime()); // Today's date
        dateField.setActionCommand(date);
        dateField.addActionListener(this);

        // create 3 labels for 3 textFields
        JLabel textLabel = new JLabel(text + " :  ");
        textLabel.setLabelFor(textField);
        JLabel passwordLabel = new JLabel(passWord+ " :  ");
        passwordLabel.setLabelFor(passwordField);
        JLabel dateLabel = new JLabel(date+ " :  ");
        dateLabel.setLabelFor(dateField);

        // go through loop to add one label one field  each row
        JLabel[] labels= {textLabel,passwordLabel, dateLabel};
        JTextField[] fields = {textField,passwordField,dateField};
        for (int i = 0; i <labels.length ; i++) {
            // add label
            c.gridwidth = GridBagConstraints.RELATIVE;
            c.fill = GridBagConstraints.NONE;
            c.weightx = 0.0;
            textFieldsPanel.add(labels[i],c);

            c.gridwidth = GridBagConstraints.REMAINDER;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 1.0;
            textFieldsPanel.add(fields[i], c);
        }

                // actionLabel part
        actionLabel = new JLabel("Fill in then click enter...");
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 1.0;
        textFieldsPanel.add(actionLabel,c);

        // Second part -- the CENTER of Borderlayout component
        JTextArea plainTextArea = new JTextArea(
                "This is an editable JTextArea. " +
                        "A text area is a \"plain\" text component, " +
                        "which means that although it can display text " +
                        "in any font, all of the text is in the same font."
        );
        plainTextArea.setLineWrap(true);
        plainTextArea.setWrapStyleWord(true);
        plainTextArea.setFont(new Font("Serif",Font.ITALIC, 18));
        JScrollPane textAreaScrollPane = new JScrollPane(plainTextArea);
        textAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textAreaScrollPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Plain Text"), BorderFactory.createEmptyBorder(5,5,5,5)));
        textAreaScrollPane.setPreferredSize(new Dimension(250, 250));
        // add to leftPanel
        leftPanel.add(textFieldsPanel,BorderLayout.PAGE_START);
        leftPanel.add(textAreaScrollPane, BorderLayout.CENTER);

    }

    private void addToRigthPanel(JPanel panel) {
        // first - top editorPane
        JEditorPane editorPane = createEditorPane(); // see below details
        JScrollPane editorScrollPane = new JScrollPane(editorPane);
        editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        editorScrollPane.setPreferredSize(new Dimension(300, 160));
        editorScrollPane.setMinimumSize(new Dimension(10,10));
        // second bottom textPane
        JTextPane textPane = createTextpane(); // see below details
        JScrollPane textScrollPane = new JScrollPane(textPane);
        textScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textScrollPane.setPreferredSize(new Dimension(300,140));
        textScrollPane.setMinimumSize(new Dimension(10,10));

        // combine two panes within a SplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, editorScrollPane, textScrollPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setResizeWeight(0.5);

        // confine the splitpane within a JPanel with a new border
        panel.add(splitPane);
    }
    // for setPage to a URL content
    private JEditorPane createEditorPane() {
        JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false); // note: if this set to be false, the HTML EditorKit will generate hyperlink event
        URL url = this.getClass().getResource("TextSampleDemoHelp.html");
        // use setPage() to access the url content
        if (url != null) {
            try {
                editorPane.setPage(url);
            } catch (IOException e) {
                System.err.println("Attemp to read a bad URL " + url);
            }
        } else {
            System.err.println("Couldn't find the URL -- TextSampleDemoHelp.html" );
        }
        return editorPane;
    }
    // use StyledDocument.insertString() to add content to the textPane
    private JTextPane createTextpane() {
        String[] initString = // 10 strings in the array
                { "This is an editable JTextPane, ",            //regular
                        "another ",                                   //italic
                        "styled ",                                    //bold
                        "text ",                                      //small
                        "component, ",                                //large
                        "which supports embedded components..." + newline,//regular
                        " " ,                     //highlight: button needs to be alone in the array for style definition
                        "...and embedded icons... \"This is the button icon\"" + newline,         //regular
                        " ",                      //highLight: icon needs to be alone in the array for style definition
                        newline + "JTextPane is a subclass of JEditorPane that " +
                                "uses a StyledEditorKit and StyledDocument, and provides " +
                                "cover methods for interacting with those objects."
                };

        String[] initStyles = // 10 styles in the array -- total 7 types of styles
                { "regular", "italic", "bold", "small", "large",
                        "regular", "button", "regular", "icon",
                        "regular"
                };

        JTextPane textPane = new JTextPane();

        StyledDocument doc = textPane.getStyledDocument();
        // need to define all the styles in the style[]
        addStylesToDocument(doc);

        try {
            for (int i=0; i < initString.length; i++) {
                doc.insertString(doc.getLength(), initString[i], // (offset, string to be inserted, attribute set(style))
                        doc.getStyle(initStyles[i]));
            }
        } catch (BadLocationException ble) {
            System.err.println("Couldn't insert initial text into text pane.");
        }

        return textPane;
    }
    //  match the style name to its style definition
    protected void addStylesToDocument(StyledDocument doc) {
        //Initialize some styles.
        Style def = StyleContext.getDefaultStyleContext().
                getStyle(StyleContext.DEFAULT_STYLE);
       // 1. "regular"
        Style regular = doc.addStyle("regular", def); // the name "regular" style is  set to default style with font family of "Sanserif".
        StyleConstants.setFontFamily(def, "SansSerif");
        // 2. "italic"
        Style s = doc.addStyle("italic", regular); // add new style "italic" to styleddocument, it has "sanserif" and "italic" attributeset
        StyleConstants.setItalic(s, true);
        // 3. "bold"
        s = doc.addStyle("bold", regular);
        StyleConstants.setBold(s, true);
        // 4. fontsize - small
        s = doc.addStyle("small", regular);
        StyleConstants.setFontSize(s, 10);
        // 5. fontsize -  large
        s = doc.addStyle("large", regular);
        StyleConstants.setFontSize(s, 16);
        // 6. setIcon - icon
        s = doc.addStyle("icon", regular);
        StyleConstants.setAlignment(s, StyleConstants.ALIGN_RIGHT); // todo: alignment not working
        ImageIcon pigIcon = createImageIcon("images/Pig.gif",
                "a cute pig");
        if (pigIcon != null) {
            StyleConstants.setIcon(s, pigIcon);
        }

        s = doc.addStyle("button", regular);
        StyleConstants.setAlignment(s, StyleConstants.ALIGN_CENTER); //
        // turn the image file into ImageIcon
        ImageIcon soundIcon = createImageIcon("images/sound.gif",
                "sound icon");
        JButton button = new JButton();
        if (soundIcon != null) {
            button.setIcon(soundIcon);
        } else {
            button.setText("BEEP");
        }
        button.setCursor(Cursor.getDefaultCursor());
        button.setMargin(new Insets(0,0,0,0));
        button.setActionCommand(buttonString);
        button.addActionListener(this);
        StyleConstants.setComponent(s, button); // set the component attribute
    }

    protected  ImageIcon createImageIcon(String path,
                                               String description) {
        URL imgURL = this.getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }



    public static void createAndShowGUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TextComponent_AllTypes pane = new TextComponent_AllTypes();
        frame.getContentPane().add(pane);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        counter++;
        System.out.println(counter + " " + e.getSource());
        String command = e.getActionCommand();

        if(text.equals(command)) {
            JTextField source = (JTextField) e.getSource();
            actionLabel.setText(source.getText());
        }else if (passWord.equals(command)) {
            JPasswordField source = (JPasswordField)e.getSource();
            actionLabel.setText(String.valueOf(source.getPassword())); // or use  new String()
        }else if (buttonString.equals(command)) {
            Toolkit.getDefaultToolkit().beep();// make beep sound.
        }
    }
}
