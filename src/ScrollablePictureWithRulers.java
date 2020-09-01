import javax.swing.*;
import java.awt.*;

/**
 * Copied from ScrollDemo.java
 */

public class ScrollablePictureWithRulers extends JPanel {
    //Fields
    ScrollablePic scrollablePic;   // from ScrollablePic.java
    static MyRuler myColumnRuler,myRowRuler;  // from MyRuler.java
    ButtonedCorner buttonedCorner;            // from ButtonedCorner.java
    ExtendedCorner extendedCorner1, extendedCorner2;    // from ExtendedCorner.java
    ImageIcon image;


    //Constructor
    ScrollablePictureWithRulers() {
        // set layout to boxlayout, otherwise default flowLayout won't open up the JScrollPane and always set the JSrollPane in the middle of the frame.
        setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS)); // or PAGE_AXIS
        setPreferredSize(new Dimension(320, 200));
        image = Utility.createImageIcon(this,"images/flyingBee.jpg");

        // this JPanel has a ScrollablePicture(a JPanel) within a JScrollPane with three corners
        scrollablePic = new ScrollablePic(image);
        myColumnRuler = new MyRuler(MyRuler.HORIZONTAL);
        myRowRuler = new MyRuler(MyRuler.VERTICAL);
        if(image != null) {
            myColumnRuler.setPreferredWidth(image.getIconWidth());
            myRowRuler.setPreferredHeight(image.getIconHeight());
        } else {
            myColumnRuler.setPreferredWidth(320);
            myRowRuler.setPreferredWidth(480);
        }
        buttonedCorner = new ButtonedCorner();
        extendedCorner1 = new ExtendedCorner();
        extendedCorner2 = new ExtendedCorner();

        JScrollPane scrollPane = new JScrollPane(scrollablePic);
        scrollPane.setColumnHeaderView(myColumnRuler);
        scrollPane.setRowHeaderView(myRowRuler);

        scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, buttonedCorner);
        scrollPane.setCorner(JScrollPane.UPPER_RIGHT_CORNER,extendedCorner1);
        scrollPane.setCorner(JScrollPane.LOWER_LEFT_CORNER,extendedCorner2);

        add(scrollPane);
        setPreferredSize(new Dimension(200,200));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
    public static void GUI() {
        JFrame frame = new JFrame("Scrollable Picture with Rulers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ScrollablePictureWithRulers pane = new ScrollablePictureWithRulers();
        frame.setContentPane(pane);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ScrollablePictureWithRulers::GUI);
    }

}
