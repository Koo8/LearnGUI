import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This program show a name with special font with a button at bottom, which
 * click to open a JDialog that shows a name JList, choose a name will close
 * this JDialog and change the showed name to the selected name
 */

public class ChooseName_FromListDialog {
    // the first window is a frame, the second pop up window (ListOFNameDialog.java) is a JDialog
    private static JFrame frame;     // this frame is needed for instantiate the new ListOfNameDialog.java
    // the JFrame has 3 components -- JLabel for title, JLable for name, and JButton to lead to the JDialog
    private JLabel theChoosenNameIs;
    private JLabel initialName;
    private JButton button;
    private static JPanel panel;
    private static String[] names = {"Arlo", "Cosmo", "Elmo", "Hugo",
            "Jethro", "Laszlo", "Milo", "Nemo",
            "Otto", "Ringo", "Rocco", "Rollo"};

    // constructor
    public ChooseName_FromListDialog() {
        // instantiate all fields , for frame, instantiate it inside the createAndShowGUI
        theChoosenNameIs = new JLabel("You choose the name: ");
        initialName = new JLabel(names[1]);
        theChoosenNameIs.setLabelFor(initialName);
        initialName.setFont(getWackyFont());
        button = new JButton("Pick a Name");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get the selected name
                String selectedName = ListOfNameJDialog.createDialogReturnSelectedName(frame,"Name Picker",names,"longString","Choose a name",initialName.getText(),button);
                initialName.setText(selectedName);
            }
        });
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.add(theChoosenNameIs);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(initialName);
        panel.add(Box.createRigidArea(new Dimension(350, 20)));// the width garantees the minimum width of the panel
        panel.add(button);
        theChoosenNameIs.setAlignmentX(Component.CENTER_ALIGNMENT);
        initialName.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

    }

    public static void createAndShowGUI() {
        frame = new JFrame("Name Chooser");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ChooseName_FromListDialog c = new ChooseName_FromListDialog();
        frame.setContentPane(ChooseName_FromListDialog.panel);

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
    /**
     * Finds a cursive font to use, or falls back to using
     * an italic serif font.
     */

    public Font getWackyFont() {
        //initial strings of desired fonts
        String[] desiredFonts =
                {"French Script", "FrenchScript", "Script"};

        String[] existingFamilyNames = null; //installed fonts
        String fontName = null;        //font we'll use

        //Search for all installed font families.  The first
        //call may take a while on some systems with hundreds of
        //installed fonts, so if possible execute it in idle time,
        //and certainly not in a place that delays painting of
        //the UI (for example, when bringing up a menu).
        //
        //In systems with malformed fonts, this code might cause
        //serious problems; use the latest JRE in this case. (You'll
        //see the same problems if you use Swing's HTML support or
        //anything else that searches for all fonts.)  If this call
        //causes problems for you under the latest JRE, please let
        //us know.
        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        if (ge != null) {
            existingFamilyNames = ge.getAvailableFontFamilyNames();
        }

        //See if there's one we like.
        if ((existingFamilyNames != null) && (desiredFonts != null)) {
            int i = 0;
            while ((fontName == null) && (i < desiredFonts.length)) {

                //Look for a font whose name starts with desiredFonts[i].
                int j = 0;
                while ((fontName == null) && (j < existingFamilyNames.length)) {
                    if (existingFamilyNames[j].startsWith(desiredFonts[i])) {

                        //We've found a match.  Test whether it can display
                        //the Latin character 'A'.  (You might test for
                        //a different character if you're using a different
                        //language.)
                        Font f = new Font(existingFamilyNames[j],
                                Font.PLAIN, 1);
                        if (f.canDisplay('A')) {
                            fontName = existingFamilyNames[j];
                            System.out.println("Using font: " + fontName);
                        }
                    }

                    j++; //Look at next existing font name.
                }
                i++;     //Look for next desired font.
            }
        }

        //Return a valid Font.
        if (fontName != null) {
            return new Font(fontName, Font.PLAIN, 36);
        } else {
            return new Font("Serif", Font.ITALIC, 36);
        }
    }


}
