import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * helper class for RectanglesInBoxLayout.java
 * this class create a rectangle with size color and alignmentX properties.
 */

public class CreateARectangleComponent extends JComponent
implements MouseListener
{
    // Fields - Color,Size
    private Color color;
    private Dimension dimension;
    private boolean restrictMaxSize;
    private boolean sizeRandom;

    // costructor
    CreateARectangleComponent(float alignment,
                              float hue,
                              int sideLength,
                              boolean sizeNotChangeable,
                              boolean masSizeRes){
        setAlignmentX(alignment);
       // System.out.println("alignment x in constructor "+ getAlignmentX());
        color = Color.getHSBColor(hue, 0.5f, 0.75f);
        dimension = new Dimension(sideLength*2,sideLength );  // this create a rectangle shape
        setBorder(BorderFactory.createLineBorder(Color.PINK, 5));
        this.sizeRandom= sizeNotChangeable;
        this.restrictMaxSize = masSizeRes;
        addMouseListener(this);
       // setPreferredSize(dimension); // this works the same as the getPreferredSize method below.
    }
    // OO the following 3 methods are important to draw the rectangle and define the width change between max and min.
    @Override
    public Dimension getPreferredSize() {
        return dimension;
    }

    @Override    // without this, the rectangle can be shrink to very small
    public Dimension getMinimumSize() {
        return dimension;
    }
    @Override
    public Dimension getMaximumSize() {
        // the super.getMaximumSize() can turn the rectangle into the full width of its container
        if(restrictMaxSize) {
            return dimension;
        }
        else {
            return super.getMaximumSize();
        }
        //return dimension;
    }


    /**
     *  Subclasses of JComponent that guarantee to always completely paint their contents
     *   should override this method and return true.
     *   This lets the painting
     *      * system know that it doesn't need to paint any covered
     *      * part of the components underneath this component.  The
     *      * end result is possibly improved painting performance.
     *   isOpaque(), not setOpaque();
     * @return
     */
    @Override
    public boolean isOpaque() {
        return true;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
    // change the rectangle position -  by change its alignmentX value
    @Override
    public void mousePressed(MouseEvent e) {
        // define the new alignment float (0.0 to 1.0) for setAlignmentX for JComponent
        // calculate alignment
        int width =getWidth();
        System.out.println("width " + width);
        float currentX = (float)e.getX();
       // System.out.println("current x " + currentX);
        float newAlignmentX = currentX/width;
        //System.out.println("new Alignment " + newAlignmentX);
        // round to one decimal float
        int roundTo = Math.round(newAlignmentX * 10.0f);
        newAlignmentX = roundTo / 10.0f;
        // setAlignmentX
        setAlignmentX(newAlignmentX);


        // need to revalidate and repaint
        revalidate();
        repaint();
        System.out.println("new Width in mousePressedListener " + getWidth());
    }


    @Override
    public void paint(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        g.setColor(color);
        g.fillRect(0,0,getWidth(),getHeight());

        // draw vertical line @ the alignment point
        // get color of the line
        g.setColor(Color.WHITE);
        // find the new alignmentX value
        float alignmentXValue = getAlignmentX();
        int xPosition = (int) ((alignmentXValue * width)-1);
        g.drawLine(xPosition, 0,xPosition, height-1);

        // draw alignment value onto the component
        g.setColor(Color.WHITE);
        g.drawString(Float.toString(alignmentXValue), 5, height-5);

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void setRestrictMaxSize(boolean restriction) {
        restrictMaxSize = restriction;
    }

//    public static void createAndShowGUI(){
//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        JPanel panel = new JPanel();
//        //panel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
//        panel.setOpaque(true);
//        panel.setPreferredSize(new Dimension(200, 500)) ;
//
//        CreateARectangleComponent rec = new CreateARectangleComponent(Component.LEFT_ALIGNMENT,0.75f, 50);
//       // CreateARectangleComponent rec1 = new CreateARectangleComponent(Component.LEFT_ALIGNMENT,0.33f, 30);
//       // CreateARectangleComponent rec2 = new CreateARectangleComponent(Component.LEFT_ALIGNMENT,0.93f, 50);
//       // System.out.println("preferred Size in createAndShowGUI " + rec.getWidth() + "," + rec1.getWidth() + "," + rec2.getWidth());
//        panel.add(rec);
//      // panel.add(rec1);
//      //  panel.add(rec2);
//
//        frame.setContentPane(panel);
//
//        frame.pack();
//        frame.setVisible(true);
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                createAndShowGUI();
//            }
//        });
//    }
}
