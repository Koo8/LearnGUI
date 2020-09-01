import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * create a layeredPane to be used anywhere,
 * layeredpane hold several layers of Jlabel,
 * set a newly added Jlabel to any layers inside the Layeredpane
 * mouseMotionListener to detect mouse movement
 */

public class LayeredPane_ChooseWhichLayerToShow extends JPanel implements ActionListener, MouseMotionListener {
    String[] labelStrings = { "Yellow (0)", "Magenta (1)",
            "Cyan (2)",   "Red (3)",
            "Green (4)" };
    Color[] layerColors = { Color.yellow, Color.magenta,
            Color.cyan,   Color.red,
            Color.green };

    JLabel imageLabel;
    JCheckBox checkBox;
    JComboBox comboBox;
    JLayeredPane layeredPane;
    ImageIcon imageIcon = Utility.createImageIcon(this,"images/dukeWaveRed.gif");
   // constructor to create the Panel
    LayeredPane_ChooseWhichLayerToShow() {
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS)); // boxlayout use setLayout method

        //create topControlPane
        JPanel controlPane = new JPanel();// flowLayout
        controlPane.add(makeComboBox());
        controlPane.add(makeCheckBox());
        controlPane.setBorder(BorderFactory.createTitledBorder("Choose Image's Layer and Position"));


        ////***** create bottomLayeredPane,

        layeredPane = new JLayeredPane();
            //Add several overlapping, colored labels to the layered pane
            //using absolute positioning/sizing.
        addColoredLabelsToLayeredPane(layeredPane,labelStrings,layerColors);
        layeredPane.setPreferredSize(new Dimension(300,310));
        layeredPane.addMouseMotionListener(this);

            // create and add the image label to the layered pane
        addImageLabelToLayeredPane(layeredPane,imageIcon);
        // put together
        add(Box.createRigidArea(new Dimension(0,5)));
        add(controlPane);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(layeredPane);

    }

    private JComponent makeCheckBox() {
        checkBox = new JCheckBox("Top Position In Layer");
        checkBox.setSelected(true);
        checkBox.addActionListener(this);
        checkBox.setActionCommand("Check");
        return checkBox;
    }

    private JComponent makeComboBox() {
        comboBox = new JComboBox(labelStrings);
        comboBox.setSelectedItem(2);
        comboBox.addActionListener(this);
        comboBox.setActionCommand("Combo");
        return comboBox;
    }

    private void addImageLabelToLayeredPane(JLayeredPane layeredPane, ImageIcon imageIcon) {
        imageLabel = new JLabel(imageIcon);
        if (imageIcon != null) {
            /*By default a layered pane has no layout manager. This means
            that you typically have to write the code that positions and
            sizes the components you put in a layered pane.
            The example uses the setBounds method to set the size and position
            of each of the labels:*/
          imageLabel.setBounds(15,200,imageIcon.getIconWidth(),imageIcon.getIconHeight());
        } else {
            System.out.println("imageIcon file is not found, using a black square instead");
            imageLabel.setBounds(15,200,30,30);
            imageLabel.setOpaque(true);// set Opaque first, otherwise background color can't be drawn.
            imageLabel.setBackground(Color.BLACK);
        }
        layeredPane.add(imageLabel,2,0);
    }

    private void addColoredLabelsToLayeredPane(JLayeredPane layeredPane, String[] labelStrings, Color[] layerColors) {
        Point location = new Point(5, 5);
        for (int i = 0; i <labelStrings.length ; i++) {
            JLabel label = new JLabel(labelStrings[i]);
//            label.setAlignmentX(CENTER_ALIGNMENT);
//            label.setAlignmentY(TOP_ALIGNMENT);
            label.setVerticalAlignment(JLabel.TOP);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setOpaque(true);
            label.setBackground(layerColors[i]);
            label.setForeground(Color.black);
            label.setBounds(location.x,location.y, 120,120);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
            /*Problem: The components in my layered pane are not layered correctly.
            In fact, the layers seem to be inversed — the lower the depth the higher the component.
            This can happen if you use an int instead of an Integer when adding components
            to a layered pane.  should use layeredPane.add(label, new Integer(i))*/
            layeredPane.add(label,Integer.valueOf(i));   // highlight: see up note
            location.x += 35;
            location.y += 35;
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Check":
                boolean result = checkBox.isSelected();
                if(result){
                    layeredPane.moveToFront(imageLabel);
                } else {
                    layeredPane.moveToBack(imageLabel);
                }
                break;
            case "Combo":  // move the imageLayer to the defined index of the layeredPane
                int index = comboBox.getSelectedIndex();
                int position = checkBox.isSelected()?0:1;
                layeredPane.setLayer(imageLabel,index,position);
                break;
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //do nothing
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // change imageLabel position
        Point position = new Point(e.getX()-50, e.getY()-50);   // offset 50 to make cursor match the right toe of the image penguin
        imageLabel.setLocation(position);
    }

    public static void GUI() {
        JFrame frame = new JFrame("LayeredPane Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LayeredPane_ChooseWhichLayerToShow pane = new LayeredPane_ChooseWhichLayerToShow();
        frame.setContentPane(pane);
        pane.setOpaque(true);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LayeredPane_ChooseWhichLayerToShow::GUI);
    }
}
