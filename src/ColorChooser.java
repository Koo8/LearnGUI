import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Customize colorPanel of colorChooser with 4 crayons
 */

public class ColorChooser extends JPanel implements ChangeListener, ActionListener {

    private JColorChooser cc;
    private JLabel banner;
    //constructor
    private ColorChooser() {
        super(new BorderLayout());
        // part-1 A JPanel holding the banner
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

        // Part -2 create buttonPanel
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Click to change background color"));
        JButton button = new JButton("Show Color Chooser...");
        button.setBackground(new Color(100,200,200));   // todo: how to remove the lined border around the JButton
        button.setForeground(Color.WHITE);
        button.setFont(new Font(allFonts[30],Font.BOLD, 25));
        button.addActionListener(this);
        buttonPanel.add(button,BorderLayout.CENTER);

        // Part - 3 add colorChooser with its default chooserPanel  and  preview Panel
        cc= new JColorChooser(banner.getForeground());   // no parameter make the initial color to be white
        cc.setBorder(BorderFactory.createTitledBorder("Choose a Front Color"));
        // add changeListener to cc
        cc.getSelectionModel().addChangeListener(this);

        // remove the colorchooser's preview panel
        cc.setPreviewPanel(new JPanel());
        // override chooserPane with your own
        // for adding a new chooserPanel to a JColorChooser, subclass the AbstractColorChooserPanel
        AbstractColorChooserPanel myChooserPanel[] = {new AbstractColorChooserPanelSubClass()}; // oooo needs to create a new class for ChooserPanel
        cc.setChooserPanels(myChooserPanel);
        cc.setColor(banner.getForeground());

        // add two parts to the main panel
        add(bannerPanel, BorderLayout.PAGE_START);
        add(buttonPanel, BorderLayout.CENTER);
        add(cc,BorderLayout.PAGE_END);
        setPreferredSize(new Dimension(600, 400));

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        Color newColor = cc.getColor();
        banner.setForeground(newColor);

    }
    //CAS == create and Show
    private static void CASGUI() {
        JFrame frame = new JFrame("Color Chooser Customed Panel");
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

    // when button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        Color color = JColorChooser.showDialog(this,"Pick a Color", banner.getBackground());
        if (color != null){
            banner.setBackground(color);
        }
    }
}
