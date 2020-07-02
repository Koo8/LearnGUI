import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * This class describes a theme using "primary" colors.
 * You can change the colors to anything else you want.
 *
 * 1.9 07/26/04
 */
public class TestThemeForLookAndFeelPractice extends DefaultMetalTheme {

    //public String getName() { return "Toms"; }

    private final ColorUIResource primary1 = new ColorUIResource(255, 255, 0);
    private final ColorUIResource primary2 = new ColorUIResource(0, 255, 255);
    private final ColorUIResource primary3 = new ColorUIResource(100, 100, 255);
    // to override the default value of these methods.
    protected ColorUIResource getPrimary1() { return primary1; }
    protected ColorUIResource getPrimary2() { return primary2; }
    protected ColorUIResource getPrimary3() { return primary3; }
    protected ColorUIResource getSecondary1() { return new ColorUIResource(100,150,20);}
    public FontUIResource getWindowTitleFont(){return new FontUIResource("Serif", Font.ITALIC, 26);}

}