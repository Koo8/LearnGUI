import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * copy FrameDemo2.java from oracle - How to make Frames
 * -- use boolean to pass conditions (like switch)
 * -- frame.setIconImage for icon
 * -- get image from a file path
 * -- draw an image from scratch
 */

public class NewWindow_SelectLookAndFeel_SelectIcon implements ActionListener {

    // variables for connections between listener and TheFrame appearances
    // they are acting like switches, listener will move the switches so that
    // the new window can be created accordingly
    private static JButton defaultButton;
    private boolean noDecoration = false;
    private boolean needfileIcon = false;
    private boolean needPaintedIcon = false;

    private Point location;
    private static int maxX= 500;
    private static int maxY = 500;

    // action
    private static final String OPN_WNDW = "open_window";
    private static final String LF_DECO = "look and feel decorations";
    private static final String WS_DECO = "window system decorations";
    private static final String NO_DECO = "no decorations";
    private static final String DF_ICON = "default icon";
    private static final String FL_ICON = "icons from file";
    private static final String PT_ICON = "painted icon";

    private NewWindow_SelectLookAndFeel_SelectIcon() {
        // define maxX and maxY; compare with ScreenSize
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        maxX = screenSize.width - 50;
        maxY = screenSize.height -50;
    }


    // create GUI
    public static void GUI() {
        // choose default look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
           e.printStackTrace();
        }

        //Make sure we have nice window decorations.
        /* highlight:you must invoke the setDefaultLookAndFeelDecorated
         method BEFORE creating the frame whose decorations you wish to affect.*/
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        NewWindow_SelectLookAndFeel_SelectIcon w = new NewWindow_SelectLookAndFeel_SelectIcon();
        Container contentPane = frame.getContentPane(); // get rootPane

        // highlight: we can use BorderLayout constants without pre-define the layout to be BorderLayout
        contentPane.add(w.createOptionBox(), BorderLayout.CENTER);
        contentPane.add(w.createButtonPane(),BorderLayout.PAGE_END);

        //highlight: set DefaultButton in rootPane can activate the button using "enter"
        frame.getRootPane().setDefaultButton(defaultButton);

        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NewWindow_SelectLookAndFeel_SelectIcon::GUI);
    }

    private JComponent createButtonPane() {
        JButton button = new JButton("New Window");
        button.setActionCommand(OPN_WNDW);
        button.addActionListener(this);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        defaultButton = button; // so that rootpane can control with "enter" key

        JPanel buttonPane = new JPanel();  // flowLayout
        buttonPane.add(button);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        buttonPane.setBackground(Color.RED);
        return buttonPane;
    }

    private JComponent createOptionBox() {
        Box box = new Box(BoxLayout.PAGE_AXIS);
        // create 6 radioButtons, in two buttonGroups, two labels for each group
        JLabel label1 = new JLabel("Decoration options for Newly Created Windows");
        JLabel label2 = new JLabel("Icon Options");

        ButtonGroup group1 = new ButtonGroup();
        ButtonGroup group2 = new ButtonGroup();

        JRadioButton b1g1 = new JRadioButton("Look and Feel Decorations");
        b1g1.setActionCommand(LF_DECO);
        b1g1.addActionListener(this);
        b1g1.setSelected(true);
        group1.add(b1g1);

        JRadioButton b2g1 = new JRadioButton("Window System Decorations");
        b2g1.setActionCommand(WS_DECO);
        b2g1.addActionListener(this);
        group1.add(b2g1);

        JRadioButton b3g1 = new JRadioButton("No Decorations");
        b3g1.setActionCommand(NO_DECO);
        b3g1.addActionListener(this);
        group1.add(b3g1);

        JRadioButton b1g2 = new JRadioButton("default icon");
        b1g2.setActionCommand(DF_ICON);
        b1g2.addActionListener(this);
        b1g2.setSelected(true);
        group2.add(b1g2);

        JRadioButton b2g2 = new JRadioButton("icon from file");
        b2g2.setActionCommand(FL_ICON);
        b2g2.addActionListener(this);
        group2.add(b2g2);

        JRadioButton b3g2 = new JRadioButton("painted icon");
        b3g2.setActionCommand(PT_ICON);
        b3g2.addActionListener(this);
        group2.add(b3g2);

        box.add(label1) ;
        box.add(Box.createHorizontalStrut(5));
        box.add(b1g1);
        box.add(b2g1);
        box.add(b3g1);
        box.add(Box.createHorizontalStrut(15));
        box.add(label2);
        box.add(Box.createHorizontalStrut(5));
        box.add(b1g2);
        box.add(b2g2);
        box.add(b3g2);
        return box;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case OPN_WNDW:
                createNewWindow();
                break;
            case LF_DECO:
                noDecoration = false;
                //Ask for window decorations provided by the look and feel.
                JFrame.setDefaultLookAndFeelDecorated(true);
                break;
            case WS_DECO:
                noDecoration = false;
                // Ask for window decorations provided by Window System.
                JFrame.setDefaultLookAndFeelDecorated(false);
                break;
            case NO_DECO:
                noDecoration = true;
                JFrame.setDefaultLookAndFeelDecorated(false);
                break;
            case DF_ICON:
                needfileIcon = false;
                needPaintedIcon = false;
                break;
            case FL_ICON:
                needfileIcon = true;
                needPaintedIcon = false;
                break;
            case PT_ICON:
                needfileIcon = false;
                needPaintedIcon = true;
                break;
        }
    }

    private void createNewWindow() {
        JFrame frame = new TheFrame();

        if(noDecoration) {
            frame.setUndecorated(true);  // all the top bar part of the frame is wiped out
        }

        //Calling setIconImage sets the icon displayed when the window
        //is minimized.  Most window systems (or look and feels, if
        //decorations are provided by the look and feel) also use this
        //icon in the window decorations.

       if(needPaintedIcon) {
           frame.setIconImage(getPaintedIcon());
        } else if(needfileIcon ) {
            frame.setIconImage(getFileIcon());
        }

       // move the new window 40 down 40 right
        if (location != null) {
            location.translate(40,40);
            if(location.x >maxX || location.y > maxY) {
                location.setLocation(0,0);
            }
            frame.setLocation(location);
        } else {
            location = frame.getLocation();
        }

        //set visible
        frame.setVisible(true);
    }
    // create image from file
    private Image getFileIcon() {
        // get image url from file path
        URL url = getClass().getResource("images/FD.jpg");
        if(url != null) {
            return new ImageIcon(url).getImage();
        } else {
            return null;
        }
    }
    // create an image from scratch
    private BufferedImage getPaintedIcon() {
        BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(Color.BLACK);
        g.drawRect(0,0,15,15);
        g.setColor(Color.RED);
        g.drawOval(5,5,5,8);
        // manully clean the resources
        g.dispose();

        return image;
    }
}

class TheFrame extends JFrame implements ActionListener {

    TheFrame() {
        super("A new window");
        setSize(270,100);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.PAGE_AXIS));
        add(Box.createVerticalGlue());

        JButton button = new JButton("Close Window");
        button.addActionListener(this);
        add(button);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(5));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }
}
