import javax.swing.*;
import java.awt.*;

/**
 * used by ScrollablePictureWithRulers.java
 * for setting JScrollPane column and row headers rulers,
 * Can be any JComponent
 * Needs PaintComponent of JComponent to paint the ruler.
 * The length interval calculation is based on Toolkit.getDefaultToolKit.getScreenResolution
 * which return a " dots per inch" int.
 */
public class MyRuler extends JComponent { // extends JComponent because needs to paitComponent().
    public static final int INCH = Toolkit.getDefaultToolkit().getScreenResolution(); // dots per inch
    int orientation;  // can be 0 or 1 for two directions
    // define horizontal and vertical of ruler with two ints.
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final int SIZE = 35;
    public int TICKLENGTH = 10;
    // ruler label
    public int increment, intervalDots;
    public boolean isMetric = true;


    // constructor
    MyRuler(int orientation) { // two orientations options
        this.orientation = orientation;
        // since increment and dots are different from metric and imperial, use a function
        setIncrementAndDotsInterval();
    }

    private void setIncrementAndDotsInterval() {
        if (isMetric) {
            // set intervalDots to dots per cm, increment same as intervalDots
            intervalDots = (int) (INCH / 2.54); // dots per cm;
            increment = intervalDots;
        } else {
            intervalDots = INCH;
            increment = intervalDots / 2; // so that half inch bar can be shown

        }
    }
     // both width and height are to be determined by the using class - ScrollablePictureWithRulers.java
    public void setPreferredWidth(int width) {
        setPreferredSize(new Dimension(width, 35));
    }

    public void setPreferredHeight(int h) {
        setPreferredSize(new Dimension(35, h));
    }

    @Override
    protected void paintComponent(Graphics g) {
        // ruler is a rectangle
        Rectangle drawHere = g.getClipBounds();// get a rectangle from the frame that embeded this ruler
        // fill clipping area with orange color  - clipping area is defined by the Frame size
        g.setColor(new Color(230, 164, 4));
        g.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height);
        // ruler size is determined by the class that use this helper class, setSize() can be used.
        // draw ruller label  - change color to black
        g.setColor(Color.BLACK);
        g.setFont(new Font("SanSerif", Font.PLAIN, 10));
        // ruller labels needs increment to define the space between two bars
        // These are decided by SetIncrementAndDotsInterval

        //draw lines and labels onto the ruler
        // first needs the start and end dots number
        // Use clipping bounds to calculate first and last tick locations.
        // drawHere.width and drawHere.height are determined synchronically by the frame visible size
        int start = 0;
        int end;
        String text;
        if (orientation == HORIZONTAL) {
            end = drawHere.x + drawHere.width;
        } else {
            end = drawHere.y + drawHere.height;
        }
        if (start == 0) { // draw a line and draw 0cm / 0in accordingly at the start point
            // draw "0cm or 0in" // this format saved a block of if/else for isMetric
            text = Integer.toString(0) + (isMetric?" cm" : " in");
            if (orientation == HORIZONTAL) {
                g.drawLine(0, SIZE - 1, 0, SIZE - TICKLENGTH - 1);
                g.drawString(text, 0, SIZE - TICKLENGTH - 4);
            } else {
                g.drawLine(SIZE-1, 0, SIZE-TICKLENGTH-1, 0);
                    g.drawString(text,15, 10);
            }
            // increment the start
            start = increment;
            text = null; // so that to be able to used later
        }

        for (int i = start; i <end; i+=increment) {
            // if i % intervalDots = 0, that's when increment == intervalDots, draw long line
            if(i%intervalDots != 0) {
                TICKLENGTH = 7;
                text = null;
            }else{
                TICKLENGTH = 10;
                text = Integer.toString(i/intervalDots);
            }
            if(orientation == HORIZONTAL) {
                g.drawLine(i, SIZE - 1, i, SIZE - TICKLENGTH - 1);
                if(text!=null) {
                    g.drawString(text,i-4, SIZE-TICKLENGTH-4);
                }
            }
            else {
                g.drawLine(SIZE-1, i, SIZE-TICKLENGTH-1, i);
                if(text !=null) {
                    g.drawString(text,SIZE-TICKLENGTH-6,i+4);
                }
            }
        }
    }

    public void setIsMetric(boolean b) {
        isMetric = b;
        //need to repaint the ruler
        setIncrementAndDotsInterval();
        repaint();
    }
}
