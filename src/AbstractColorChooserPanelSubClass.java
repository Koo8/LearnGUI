import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *  AbstractColorChooserPanel(JPanel)is an abstract superclass for color choosers.  If you want to add
 *  * a new color chooser panel into a JColorChooser, subclass
 *  * this class.  This is used by ColorChooser.java
 *  I used JRadioButton in the class, 4 JToggleButton works the same.
 */

public class AbstractColorChooserPanelSubClass extends AbstractColorChooserPanel implements ActionListener {
    // this custom choosePanel has 4 ToggleButtons with icons
    JRadioButton redCrayon;
    JRadioButton blueCrayon;
    JRadioButton yellowCrayon;
    JRadioButton greenCrayon;

    // create button prototype
    private JRadioButton createCrayon (String name) {
        JRadioButton crayon = new JRadioButton(); // button has not text
        // distinguish each button by actionCommand
        crayon.setActionCommand(name);
        // add listener for click
        crayon.addActionListener(this);
        // setIcon for button
        ImageIcon icon = Utility.createImageIcon(this, "images/" + name + ".gif");
        if (icon != null) {
            crayon.setIcon(icon);
            crayon.setToolTipText("This is " + name + "crayon");
        } else {
            crayon.setText("Image not found. This is the " + name +  "caryon");
            crayon.setFont(crayon.getFont().deriveFont(Font.ITALIC));
            crayon.setHorizontalAlignment(JButton.HORIZONTAL);
            crayon.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        }

        return crayon;
    }
    // get newColor from ColorSelectionModel, the newColor was set in the actionPerformed listener
    //
    @Override
    public void updateChooser() {
        Color newColor = getColorFromModel();
        if(Color.red.equals(newColor)) {
            redCrayon.setSelected(true);
        } else if (Color.green.equals(newColor) ) {
            greenCrayon.setSelected(true);
        } else if (Color.blue.equals(newColor)) {
            blueCrayon.setSelected(true);
        } else if (Color.black.equals(newColor)) {
            blueCrayon.setSelected(true);
        }
    }
    // this method works like a constructor
    @Override
    protected void buildChooser() {
        // the parent is JPanel, setLayout is from Container.java
        setLayout(new GridLayout(0, 1));
        // create 4 buttons and put them into a buttonGroup and the ChooserPanel
        ButtonGroup group = new ButtonGroup();
        redCrayon = createCrayon("red");
        greenCrayon = createCrayon("green");
        blueCrayon = createCrayon("blue");
        yellowCrayon = createCrayon("yellow");

        group.add(redCrayon);
        group.add(blueCrayon);
        group.add(greenCrayon);
        group.add(yellowCrayon);

        add(redCrayon);
        add(blueCrayon);
        add(greenCrayon);
        add(yellowCrayon);

    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public Icon getSmallDisplayIcon() {
        return null;
    }

    @Override
    public Icon getLargeDisplayIcon() {
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JRadioButton source = (JRadioButton) e.getSource();
        String command = source.getActionCommand();
        Color newColor = null;

        if("green".equals(command))   {
            newColor = Color.green;
        } else if ("red".equals(command)) {
            newColor = Color.red;
        } else if ("yellow".equals(command)) {
            newColor = Color.yellow;
        } else if ("blue".equals(command)) {
            newColor = Color.black;
        }
        getColorSelectionModel().setSelectedColor(newColor);
    }
}
