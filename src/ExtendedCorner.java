import javax.swing.*;
import java.awt.*;

/**
 * Helper class for ScrollablePictureWithRulers.java
 * Only needs to paint this corner area of JScrollPane
 */

public class ExtendedCorner extends JComponent {
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(230,55,14));
        // fill the whole component
        g.fillRect(0,0,getWidth(),getHeight());
    }
}
