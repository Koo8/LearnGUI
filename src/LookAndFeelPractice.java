import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.plaf.metal.OceanTheme;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.lang.reflect.Method;

public class LookAndFeelPractice implements ActionListener {
    private int counter = 0;
    private JLabel label;
    private static final String LOOKANDFEEL = "Metal";
    private static final String THEME = "Other";


    public Component createComponent() {
        JButton button = new JButton("Click to Increment");
        button.setMnemonic(KeyEvent.VK_I);
        button.addActionListener(this);

        label = new JLabel("You clicked the button " + counter);
        //JPanel pane = new JPanel(); // default is horizontal display of the components
        JPanel pane = new JPanel(new GridLayout(0, 1)); // display vertically, if (1,0) display will change to horizontally
        pane.add(button);
        pane.add(label);
        // pane decoration
        pane.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));
        return pane;
    }

    private static void initLookAndFeel() {

        String lookandfeel = null;
        if (LOOKANDFEEL != null) {
            // define categories of lookandfeel
            if (LOOKANDFEEL.equals("Metal")) {
                lookandfeel = UIManager.getCrossPlatformLookAndFeelClassName();
                // lookAndFeel = "javax.swing.plaf.metal.MetalLookAndFeel"; // this line works as well

            } else if (LOOKANDFEEL.equals("System")) {
                lookandfeel = UIManager.getSystemLookAndFeelClassName();
            } else if (LOOKANDFEEL.equals("Motif")) {
                lookandfeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
            } else if (LOOKANDFEEL.equals("GTK")) {
                lookandfeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
            } else {
                System.err.println("Only choose value of LOOKANDFEEL from specified list- System, Metal, Motif and GTK");
                lookandfeel = UIManager.getSystemLookAndFeelClassName();
            }

            // set Lookandfeel
            try {
                UIManager.setLookAndFeel(lookandfeel);
                // "Metal" lookandfeel has default themes and one custom theme - TestTheme.java
                if(LOOKANDFEEL.equals("Metal")) {
                    if(THEME.equals("DefaultMetal")) {
                        MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());

                    } else if(THEME.equals("Ocean")) {
                        MetalLookAndFeel.setCurrentTheme(new OceanTheme());
                    } else {
                        MetalLookAndFeel.setCurrentTheme(new TestTheme());
                    }

                    // must define setLookandfeel to show the theme
                    UIManager.setLookAndFeel(new MetalLookAndFeel());

                }

            } catch (ClassNotFoundException e) {
                System.err.println("Couldn't find class for specified look and feel:"
                        + lookandfeel);
            } catch (InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }

        }
    }


    public static void createAndShowGui() {

        // first to set look and feel
        initLookAndFeel();
        // JFrames will have their Window decorations provided by the current LookAndFeel.
        JFrame.setDefaultLookAndFeelDecorated(true); // oo the DarkPink top border is created by this line.
        JFrame frame = new JFrame("Compare L&Fs");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(new LookAndFeelPractice().createComponent(), BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        counter++;
        label.setText("The Counter is  " + counter);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGui();
            }
        });
    }
}
