import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.nio.ByteOrder;

/**
 * this has two panels: ComboBox panel and RadioButton panel
 * One actionlistener is listening to both panels
 */
public class ComboBox_RadioButton_SelectAnimalPic extends JPanel implements ActionListener {

    //Fields
    JComboBox comboBox;
    ButtonGroup buttonGroup;
    JRadioButton button;
    String[] petNames = {"bird", "cat", "dog", "rabbit", "Pig"};
    JLabel pictureCombox;

    //Constructor
    ComboBox_RadioButton_SelectAnimalPic() {
        super(new BorderLayout());
        JPanel topPanel = new JPanel(new BorderLayout());
        // combobox - non-editable
        // the following two lines both work
        // comboBox = new JComboBox<String >(petNames);
        comboBox = new JComboBox(petNames);
        comboBox.setActionCommand("combo");
        comboBox.addActionListener(this);

        // JLabel for pictures
        pictureCombox = new JLabel();
        updatePictureLabel(comboBox.getSelectedIndex());

        topPanel.add(comboBox, BorderLayout.PAGE_START);
        topPanel.add(pictureCombox, BorderLayout.PAGE_END);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // bottomPanel for radioButton

        JPanel bottomPanel = new JPanel();
        // radioButton Group
        JRadioButton b1 = new JRadioButton("dog");
        JRadioButton b2 = new JRadioButton("cat");
        JRadioButton b3 = new JRadioButton("rabbit");
        JRadioButton b4 = new JRadioButton("Pig");
        JRadioButton b5 = new JRadioButton("bird");
        buttonGroup = new ButtonGroup();
        b1.setActionCommand("dog");

        JRadioButton[] buttons = new JRadioButton[5];
        for (int i = 0; i <buttons.length ; i++) {
            JRadioButton b = new JRadioButton();
            b.setText(petNames[i]);
            b.setActionCommand(petNames[i]);
            b.addActionListener(this);
            buttonGroup.add(b);
            bottomPanel.add(b);
        }

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,topPanel, bottomPanel);
        splitPane.setOneTouchExpandable(true);

        add(splitPane, BorderLayout.CENTER);

    }

    private void updatePictureLabel(int index) {

        String name = petNames[index];
        ImageIcon icon = Utility.createImageIcon(this, "images/" + name + ".gif");
        if (icon != null) {
            pictureCombox.setIcon(icon);
        }else{
            pictureCombox.setFont(pictureCombox.getFont().deriveFont(Font.ITALIC));
            pictureCombox.setText("image is not available" + name + ".gif");
        }
        pictureCombox.setToolTipText("picture of " + name);
        pictureCombox.setHorizontalAlignment(SwingConstants.CENTER); // or JLabel.Center
        pictureCombox.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
    }

    //CASGUI
    private static void CASGUI() {
        JFrame frame = new JFrame("ComboBox and RadioButton");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ComboBox_RadioButton_SelectAnimalPic panel = new ComboBox_RadioButton_SelectAnimalPic();
        panel.setOpaque(true);
        frame.setContentPane(panel);

        frame.pack();
        frame.setVisible(true);
    }

    //Main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CASGUI();
            }
        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();
        if (command.equals("combo")) {
            System.out.println("ComboBox listening....");
            JComboBox source = (JComboBox) e.getSource();
            updatePictureLabel(source.getSelectedIndex());
        }
        else
        {
            System.out.println("Else ....");
            pictureCombox.setIcon(Utility.createImageIcon(
                    this,"images/" + command + ".gif"
            ));
        }
    }
}

