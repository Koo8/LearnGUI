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
   // String[] petNames = {"bird", "cat", "dog", "rabbit", "Pig"};
    Pet[] pets  = {
            new Pet("cat", 3, "like to eat fish"),
           new Pet("bird", 5, "fly high"),
           new Pet("dog", 7, "bark constantly"),
           new Pet("rabbit", 8, "hopping around"),
           new Pet("Pig", 1, "sleep and eat only")

   };
    JLabel pictureCombox;

    //Constructor
    ComboBox_RadioButton_SelectAnimalPic() {
        super(new BorderLayout());
        JPanel topPanel = new JPanel(new BorderLayout());
        // combobox - non-editable
        // the following two lines both work
        // comboBox = new JComboBox<String >(petNames);
       // comboBox = new JComboBox(petNames);

        // create a comboBox with renderer for a new Class Pet
        comboBox = new JComboBox(pets);
        comboBox.setRenderer(new BackgroundColorRenderer());
        comboBox.setMaximumRowCount(3);
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
            b.setText(pets[i].getPetName());
            b.setActionCommand(pets[i].getPetName());
            b.addActionListener(this);
            buttonGroup.add(b);
            bottomPanel.add(b);
        }

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,topPanel, bottomPanel);
        splitPane.setOneTouchExpandable(true);

        add(splitPane, BorderLayout.CENTER);

    }

    private void updatePictureLabel(int index) {

        String name = pets[index].getPetName();
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

    /**
     * ListCellRenderer can define a PreferredSize of the Cell
     * or Use DefaultListCellRender construct approach create a renderer
     * extends JLabel impliments ListCellRenderer ( see oracle ComboBoxDemo.java)
     */
    private class BackgroundColorRenderer implements ListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

              JLabel renderer = (JLabel) new DefaultListCellRenderer().getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
              Pet pet = (Pet) value;
              String name = pet.getPetName();
              String fav = pet.getFavActivity();
              int age = pet.getAge();
              renderer.setText(String.format("%s - %d - %s", name, age, fav));

              renderer.setBackground(index %2 == 0 ? Color.BLUE: (Color) UIManager.get("List.background"));
              renderer.setFont(new Font("serif", Font.ITALIC, 24));
              renderer.setPreferredSize(new Dimension(0, 30));
              renderer.setForeground(Color.getHSBColor(0.4f, 0.7f, 0.9f));
              return renderer;

        }
    }

    private class Pet {

        String petName;
        int age;
        String favActivity;
        public Pet(String petName, int age, String fav ) {
            this.petName = petName;
            this.age = age;
            this.favActivity = fav;
        }

        public String getPetName() {
            return petName;
        }

        public int getAge() {
            return age;
        }

        public String getFavActivity() {
            return favActivity;
        }
    }
}

