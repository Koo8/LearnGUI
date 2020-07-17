import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class ColorChooser extends JPanel implements ChangeListener {

    private JColorChooser cc;
    private JLabel banner;
    //constructor
    private ColorChooser() {
        super(new BorderLayout());
        // A JPanel holding the banner
        JPanel bannerPanel = new JPanel(new BorderLayout());
        bannerPanel.setBorder(BorderFactory.createTitledBorder("Banner"));
        // paint banner
        banner = new JLabel("Welcome to the Color Chooser", JLabel.CENTER);
        banner.setBackground(Color.blue);
        banner.setForeground(Color.getHSBColor(0.4f,0.8f, 0.8f));
        banner.setOpaque(true);
        //banner.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        //get the list of all fonts avaiable
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] allFonts = ge.getAvailableFontFamilyNames();
        for(String fontName:allFonts) {
            System.out.println(fontName);
        }
        banner.setFont(new Font(allFonts[18], Font.BOLD, 30));
        banner.setPreferredSize(new Dimension(200, 50));
        // add banner to bannerPanel
        bannerPanel.add(banner, BorderLayout.CENTER);

        //
        cc= new JColorChooser(banner.getForeground());
        cc.setBorder(BorderFactory.createTitledBorder("Pick a Color"));
        // add changeListener to cc
        cc.getSelectionModel().addChangeListener(this);

        // add two parts to the main panel
        add(bannerPanel, BorderLayout.CENTER);
        add(cc,BorderLayout.PAGE_END);

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        Color newColor = cc.getColor();
        banner.setForeground(newColor);

    }
    //CAS == create and Show
    private static void CASGUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // instantiate this class
        ColorChooser thePanel = new ColorChooser();
        frame.setContentPane(thePanel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CASGUI();
            }
        });
    }
}
