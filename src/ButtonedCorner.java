import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Helper class for ScrollablePictureWithRulers.java
 */

public class ButtonedCorner extends JPanel implements ItemListener {

    JToggleButton button;
    // highlight: can't use the following instantiated instance, a stackoverFlowError will occur, 
    //ScrollablePictureWithRulers mainFunction = new ScrollablePictureWithRulers();
    // constructor
    ButtonedCorner () {
        button = new JToggleButton("cm", true);
        button.setFont(new Font("Sanserif", Font.PLAIN, 10));
        button.setMargin(new Insets(2,2,2,2));
        button.addItemListener(this);
        add(button);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        if (e.getStateChange()== ItemEvent.SELECTED) {
            button.setText("cm");
            ScrollablePictureWithRulers.myColumnRuler.setIsMetric(true);
            ScrollablePictureWithRulers.myRowRuler.setIsMetric(true);

        } else {
            button.setText("in");
            ScrollablePictureWithRulers.myRowRuler.setIsMetric(false);
            ScrollablePictureWithRulers.myColumnRuler.setIsMetric(false);
        }
    }
}
