import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;

/**
 * Make a MyGlassPane,  refer to GlassPaneDemo.java, which has deprecated method, but once updated,
 * bug of not responding to deSelected option from checkbox.
 * use checkbox to control to draw onto MyGlassPane over MenuBar and ContentPane area
 */

public class DrawOnGlassPane {
    static CusGlassPane myGlassPane;

    private static void GUI() {
        JFrame frame = new JFrame("GlassPane Drawing Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //create contenentPane, add one checkbox and two buttons
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new FlowLayout());
        JCheckBox checkBox = new JCheckBox("Glass pane \"visible\"", false);
        contentPane.add(checkBox);
        contentPane.add(new JButton("Button 1"));
        contentPane.add(new JButton("Button 2"));

        // create menubar
        JMenuBar menuBar = new JMenuBar();;
        JMenu menu = new JMenu("Menu");
        menu.add(new JMenuItem("Do nothing"));
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        // create glassPane which appears over both menu bar
        // and content pane and is an item listener on the checkbox
        myGlassPane  = new CusGlassPane(checkBox,menuBar,contentPane);
        // add this listener to checkbox
        checkBox.addItemListener(myGlassPane);
        frame.setGlassPane(myGlassPane);

        // put together and show
        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DrawOnGlassPane::GUI);
    }
}
/*We have to provide our own glass pane that extends JComponent
so that it can paint.
This glassPane have two functions 1. to draw a circle when checkbox is selected. 2
can listen to the mouseReleased action and start to draw*/
class CusGlassPane extends JComponent implements ItemListener {
    Point point;
    JMenuBar menuBar;

    //constructor  - parameters
    // 1. checkbox - the component that receive the dispatch event
    // 2. menubar - for its location
    // 3. contentPane - for its location

    CusGlassPane(JCheckBox checkBox,JMenuBar menuBar, Container contentPane) {
        this.menuBar = menuBar;
        // add listener
        MyMouseInputAdapter adapter = new MyMouseInputAdapter(checkBox,menuBar,contentPane, this);
        addMouseListener(adapter);
        addMouseMotionListener(adapter);

    }
    @Override // toggle between glassPane visible or not
    public void itemStateChanged(ItemEvent e) {
        System.out.println(e.getStateChange());
        setVisible(e.getStateChange() == ItemEvent.SELECTED);
        //setVisible(checkBox.isSelected());
    }

    // draw circle override paintComponent. repaint() will call this method

    @Override
    protected void paintComponent(Graphics g) {
        // paint at the point, point will be defined by listener
        if(point != null) {
            if(point.y <= menuBar.getHeight()){
                    g.setColor(Color.BLUE);
            } else {
                g.setColor(Color.RED);
            }
            g.fillOval(point.x-10,point.y-10,20, 20 );
        }
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}

// redispatch all events to checkbox
class MyMouseInputAdapter extends MouseInputAdapter {
    //Fields
   // Toolkit toolkit;
    Component checkBox;
    JMenuBar menuBar;
    Container contentPane;
    CusGlassPane glassPane;

    //constructor
    MyMouseInputAdapter(Component checkBox,JMenuBar menuBar, Container contentPane, CusGlassPane glassPane) {
       // toolkit = toolkit.getDefaultToolkit();
        this.checkBox = checkBox;
        this.menuBar = menuBar;
        this.contentPane = contentPane;
        this.glassPane = glassPane;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        reDespatchMouseEvent(e, false);
    }

    @Override
    public void mousePressed(MouseEvent e) {
       reDespatchMouseEvent(e,false);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        reDespatchMouseEvent(e,false);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        reDespatchMouseEvent(e,false);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        reDespatchMouseEvent(e,false);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        reDespatchMouseEvent(e,false);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       reDespatchMouseEvent(e, true);

    }
    /*Basic implimentation of redispatching events
    * 1. find mouse points in this pane  A
    * 2. convert to points in another pane  B
    * 3. find deepestComponent C on B
    * 4. then convert again from A to C
    * 5. c.dispatchevent*/
    private void reDespatchMouseEvent(MouseEvent e, boolean drawDot) {
        Point pointOnGlassPane = e.getPoint();
        // convert to point coordinate system on Contentpane -
        // since glasspane covers both menubar and contentPane,
        // do a test will find out: point.y negative value belongs to the
        // menubar height() and beyone, positive part will be into contentPane.
        Container container = contentPane;
        Point pointOnContenPane = SwingUtilities.convertPoint(glassPane,pointOnGlassPane,contentPane);
        if(pointOnContenPane.y > 0 ){   // inside the contentPane
            //Find out exactly which component it's over.
            Component component = SwingUtilities.getDeepestComponentAt(container,pointOnContenPane.x,pointOnContenPane.y);
            // if the component is the checkbox
            if((component != null) && (component.equals(checkBox))) {
                // convert the point again from glass pane to checkBox
                Point componentPoint = SwingUtilities.convertPoint(glassPane,pointOnGlassPane, component);
                // use this point to dispatch events to checkbox
                //parameters:
                // Component source, int id, long when, int modifiers,
                // int x, int y, int clickCount, boolean popupTrigger
                component.dispatchEvent(new MouseEvent(
                        component,e.getID(),
                        e.getWhen(),
                        e.getModifiersEx(),  // e.getModifier() is deprecated. but the getMofifierEx() can't work correctly - once checkbox selected, you can't trigger itemStateChange again with deSelected.
                        componentPoint.x,componentPoint.y,e.getClickCount(),e.isPopupTrigger()));
            }
        }
        if(drawDot) {
            // define point
           glassPane.setPoint(pointOnGlassPane);
           glassPane.repaint();
        }
    }
}
