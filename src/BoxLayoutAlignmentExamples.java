import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * This class show 3 tabs that hold different components inside boxLayout,
 * since some component have non-centered default alignment, some adjustment
 * needs to be done for the components to be aligned properly
 * --- JTabbedPane
 * --- label, button and panel have different alignment default
 * --- SwingConstants can be used, but may appear different from AbstractButton or TitledBorder constants
 */

public class BoxLayoutAlignmentExamples extends JPanel {

    //constructor
    BoxLayoutAlignmentExamples() {
        super(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();

        // add 3 tabs of panels
        JPanel tab1 = new JPanel();  // flowlayout
        tab1.add(createTwoButtonPanel(false));
        tab1.add(createTwoButtonPanel(true));
        tabbedPane.addTab("TWO BUTTONS",tab1);

        JPanel tab2 = new JPanel();
        tab2.add(createLabelAndComponentPanel(false));
        tab2.add(createLabelAndComponentPanel(true));
        tabbedPane.addTab("Lable & Component",tab2);

        JPanel tab3 = new JPanel();
        tab3.add(createTwoPanelsPane(false));
        tab3.add(createTwoPanelsPane(true));
        tabbedPane.addTab("Two Panels" ,tab3);

        add(tabbedPane, BorderLayout.CENTER);
    }

    private Component createTwoPanelsPane(boolean b) {
        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane,BoxLayout.LINE_AXIS));

        JPanel p1 = new JPanel();
        Dimension dimension = new Dimension(100, 50);
        // make sure the size won't change
        p1.setPreferredSize(dimension);
        p1.setMinimumSize(dimension);
        p1.setMaximumSize(dimension);
        TitledBorder border = new TitledBorder(new LineBorder(Color.red),
                "1st Panel",
                TitledBorder.CENTER,
                TitledBorder.ABOVE_BOTTOM);
        p1.setBorder(border);
        pane.add(p1);

        JPanel p2 = new JPanel();
        p2.setPreferredSize(dimension);
        p2.setMinimumSize(dimension);
        p2.setMaximumSize(dimension);
        border = new TitledBorder(new LineBorder(Color.CYAN),
                "2nd Panel",
                TitledBorder.TRAILING,
                TitledBorder.DEFAULT_POSITION);
        p2.setBorder(border);
        pane.add(p2);
        String title;

        if(b) {
             title = "Default matched";
        } else {
            title = "mismatched";
           // p1.setAlignmentY(SwingConstants.TOP);  // align to frame top
            p1.setAlignmentY(TOP_ALIGNMENT); // top aligns to other's alignment ( for this case center alignment)
        }
         pane.setBorder(BorderFactory.createTitledBorder(title));

        return pane;
    }

    private Component createLabelAndComponentPanel(boolean b) {
        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        JComponent p = new JPanel() {   // to make sure the size of this panel stay the same
            @Override
            public Dimension getPreferredSize() {            // can also use p.setPreferredSize(new dimension())
                return new Dimension(150, 100);
            }

            @Override
            public Dimension getMaximumSize() {
                return getPreferredSize();
            }

            @Override
            public Dimension getMinimumSize() {
                return getPreferredSize();
            }
        };
        p.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.RED),
                "A JPanel",
                TitledBorder.LEADING,
                TitledBorder.DEFAULT_POSITION,
                new Font("Newman", Font.ITALIC, 12),
                Color.PINK));
        JLabel label = new JLabel("A JLabel");
        pane.add(p);
        pane.add(label);

        String title;
        if (b) {
            title = "matched";
            label.setAlignmentX(CENTER_ALIGNMENT);  // center aligns with other components center
            // JPanel has a default alignmentX of Center
        } else {
            title = "mismatched";
        }

        pane.setBorder(BorderFactory.createTitledBorder(title));

        return pane;
    }

    private Component createTwoButtonPanel(boolean b) {
        ImageIcon icon1 = Utility.createImageIcon(this, "images/middle.gif");
        ImageIcon icon2 = Utility.createImageIcon(this, "images/geek/geek-cght.gif");
        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));
        JButton b1 = new JButton("A JButton", icon1);
        b1.setVerticalTextPosition(SwingConstants.TOP);  // AbstractButton.TOP works the same because TOP is still inherited from SwingConstant
        b1.setHorizontalTextPosition(SwingConstants.CENTER);   // otherwise, image and text in flowLayout
        pane.add(b1);

        JButton b2 = new JButton("Another JButton", icon2);
        b2.setVerticalTextPosition(SwingConstants.CENTER);
        pane.add(b2);

        String titleforBorder;

        if (b) { // default alignCenter, but two buttons are different size, layout is bad
            titleforBorder = "Desired";
            // set both buttons to the same allignMent - bottom
            b1.setAlignmentY(BOTTOM_ALIGNMENT);
            b2.setAlignmentY(BOTTOM_ALIGNMENT);
        } else {
            titleforBorder = "default";
        }
        pane.setBorder(BorderFactory.createTitledBorder(titleforBorder));

        return pane;
    }

    public static void GUI(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setContentPane(new BoxLayoutAlignmentExamples());
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BoxLayoutAlignmentExamples::GUI);
    }
}
