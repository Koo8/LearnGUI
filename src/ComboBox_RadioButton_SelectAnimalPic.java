import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * this has two panels: ComboBox panel and RadioButton panel
 * One actionlistener is listening to both panels
 */
public class ComboBox_RadioButton_SelectAnimalPic extends JPanel implements ActionListener {

    //Fields
    JComboBox comboBox;
    JComboBox comboBox2;
    ButtonGroup buttonGroup;
    JRadioButton button;
    JLabel dateDisplay;
    String[] dateFormats = {
            "dd MMMMM yyyy",
            "dd.MM.yy",
            "MM/dd/yy",
            "yyyy.MM.dd G 'at' hh:mm:ss z",
            "EEE, MMM d, ''yy",
            "h:mm a",
            "H:mm:ss:SSS",
            "K:mm a,z",
            "yyyy.MMMMM.dd GGG hh:mm aaa"
    };
    String currentDate;

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
        currentDate = dateFormats[0];
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel,BoxLayout.PAGE_AXIS));
        // combobox - non-editable
        // the following two lines both work
        // comboBox = new JComboBox<String >(petNames);
       // comboBox = new JComboBox(petNames);

        // create a comboBox with renderer for a new Class Pet
        comboBox = new JComboBox(pets);
        comboBox.setRenderer(new MyListCellRenderer());
        comboBox.setMaximumRowCount(3);
        comboBox.setActionCommand("combo");
        comboBox.setAlignmentX(LEFT_ALIGNMENT);
        comboBox.addActionListener(this);


        // JLabel for pictures - for comboBox
        pictureCombox = new JLabel();
        updatePictureLabel(comboBox.getSelectedIndex());


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
            topPanel.add(b);
        }


        topPanel.add(comboBox);
        topPanel.add(pictureCombox);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // bottomPanel for radioButton

        JPanel bottomPanel = new JPanel(new GridLayout(0, 1));

        // create comboBox2 for setEditable method
        comboBox2 = new JComboBox(dateFormats);
        comboBox2.setEditable(true);
        comboBox2.setActionCommand("c2");
        comboBox2.setSelectedIndex(2);
        comboBox2.addActionListener(this);

        // JLabel for Date display - for comboBox2
        dateDisplay = new JLabel(currentDate, JLabel.LEADING);
        //  dateDisplay.setAlignmentX(LEFT_ALIGNMENT);
        reformatDate(comboBox2.getSelectedItem().toString());

        bottomPanel.add(comboBox2);
        bottomPanel.add(dateDisplay);
        bottomPanel.setPreferredSize(new Dimension(400, 200));

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,topPanel, bottomPanel);
        splitPane.setOneTouchExpandable(true);

        add(splitPane);

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
        pictureCombox.setAlignmentX(LEFT_ALIGNMENT);
        pictureCombox.setBorder(BorderFactory.createLineBorder(Color.RED,5, true));

    }

    //CASGUI
    private static void CASGUI() {
        JFrame frame = new JFrame("ComboBox and RadioButton");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

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
            JComboBox source = (JComboBox) e.getSource();
            updatePictureLabel(source.getSelectedIndex());
        }  else if (command.equals("c2")){
            JComboBox mybox = (JComboBox) e.getSource();
            String text = mybox.getSelectedItem().toString();
            System.out.println("Combo 2");
            reformatDate(text);
        }
        else  // I NEED TO ADD INDIVIDUAL ACTIONcOMMAND FOR EACH RADIOBUTTON TO AVOID WHEN
        //I EDIT  COMBOBOX2 WITH MY OWN INPUT, THE PICTURE DISPLAY JPANEL IS MULFUNCTION
        // WHEN "EDIT" ACTIONCOMMAND "C2" IS NOT CALLED.
        // TODO: SO IF i CAN ADD THE NEWLY ADDED FORMAT TO THE COMBOBOX2 FORMAT ARRAY, I CAN USE "C2" ACTIONCOMMAND
        {
            System.out.println("Else ....");
            pictureCombox.setIcon(Utility.createImageIcon(
                    this,"images/" + command + ".gif"
            ));
        }
    }

    // REFORMATDATE
    private void reformatDate(String s) {
        Date  today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(s);
        String displayS = dateFormat.format(today);
        dateDisplay.setText(displayS);
        dateDisplay.setForeground(Color.BLUE);

    }


    /**
     * ListCellRenderer can define a PreferredSize of the Cell
     * or Use DefaultListCellRender construct approach create a renderer
     * extends JLabel impliments ListCellRenderer ( see oracle ComboBoxDemo.java)
     */
    private class MyListCellRenderer implements ListCellRenderer {
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

