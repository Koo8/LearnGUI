import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * use  CreateARectangleComponent.java as a helper class
 */

public class RectanglersInBoxLayout implements ItemListener {
    private final int numbersOfRec = 5;
    private boolean maxSizeRestricted = true;
    private boolean randomSide = true;
    private float[] alignX = {
            Component.LEFT_ALIGNMENT,
            Component.RIGHT_ALIGNMENT,
            Component.RIGHT_ALIGNMENT,
            Component.RIGHT_ALIGNMENT,
            Component.RIGHT_ALIGNMENT
    };
    private float[] hueArray = {0.3f, 0.1f, 0.75f, 0.22f, 0.45f};
    private int sideLength;  // if recSizeAreRandom is true, this value is randomly calculated, otherwise it is set to 30;
    private CreateARectangleComponent[] recs = new CreateARectangleComponent[numbersOfRec];

    private void inContentPane(Container theContainer) {
        JPanel panel = new JPanel();
        //boxlayout 1 -- use boxlayout for layout setting
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
        // create Rectangles
        for (int i = 0; i < numbersOfRec; i++) {
            recs[i] = new CreateARectangleComponent(alignX[i], hueArray[i], getSideLength(), randomSide, maxSizeRestricted);
            panel.add(recs[i]);
        }
        theContainer.add(panel,BorderLayout.CENTER);

        // create the instruction label and checkbox
        JLabel label = new JLabel("Click to change the Max resitriction");
        JCheckBox checkBox = new JCheckBox("Restrick Maximum Rectangle");
        checkBox.setSelected(maxSizeRestricted);
        checkBox.addItemListener(this);
        // boxlayout 2 -- use box to contain the two components
        Box box = Box.createVerticalBox();
        box.add(label);
        box.add(checkBox);

        theContainer.add(box,BorderLayout.PAGE_END);
    }

    private int getSideLength() {
        if (randomSide) {
            sideLength = (int) ((Math.random() * 30 )+ 30);
        } else {
            sideLength = 30;
        }
        return sideLength;
    }

    @Override   // for ItemListener
    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange()==ItemEvent.SELECTED) {
            maxSizeRestricted = true;
        }    else{
            maxSizeRestricted = false;
        }
        notifyCreateARecClass(maxSizeRestricted); // using set method;
    }
    private void notifyCreateARecClass(boolean restriction) {
        for (int i = 0; i <numbersOfRec ; i++) {
            recs[i].setRestrictMaxSize(restriction);
        }
    }

    private static void createAndShowGUI(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        RectanglersInBoxLayout box = new RectanglersInBoxLayout();
        box.inContentPane(frame.getContentPane());
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


}