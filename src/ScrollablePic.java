import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Helper class for ScrollablePictureWithRulers.java
 * This class is a JLabel(ImageIcon) that's scrollable,
 * highlight: Components that support autoscrolling(all JComponent) must
 *
 *  handle mouseDragged(events) by calling scrollRectToVisible with a
 *  rectangle that contains the mouse event's location.
 */


public class ScrollablePic extends JLabel implements Scrollable, MouseMotionListener {

    //constructor
    ScrollablePic(ImageIcon m){
        super(m); // instantiate the JLabel with the ImageIcon
        // imageIcon maybe null, set text to show
        if(m == null) {
            System.out.println("in ScrollablePic.java, image is null");
            setText("No picture found");
            setHorizontalAlignment(CENTER);
            setOpaque(true);
            setBackground(Color.WHITE);
        }
        // deal with scrollable of this JComponent
        setAutoscrolls(true);  // enable synthetic drag event.
        // to handle scrolling event, use mouseDragged() from MouseMotionListener
        addMouseMotionListener(this);
    }



    @Override
    public void mouseDragged(MouseEvent e) {
        /*Components(this JLabel) that support autoscrolling must handle mouseDragged events by
        calling scrollRectToVisible with a rectangle that contains the mouse
        event's location. */
        Rectangle rec = new Rectangle(e.getX(), e.getY(), 1, 1);
        scrollRectToVisible(rec);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //do nothing
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return null; // or return super.getPreferredSize() both won't affect the size of the Frame, which is defined by the JScrolPane
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {// direction -- Less than zero to scroll up/left, greater than zero for down/right.
        return 0;  // check oracle scrollablePicture.java from ScrollDemo.java
        // todo: i don't understand the code inside this method.
        // this method doesn't get called anywhere else
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 0;
    }

    @Override  //returning true (Width, Height below) for a Scrollable
    //whose ancestor is a JScrollPane effectively disables horizontal
    //scrolling.     this program is the case.
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
}
