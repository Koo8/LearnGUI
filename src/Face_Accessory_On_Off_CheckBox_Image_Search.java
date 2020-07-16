import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.net.URL;

/**
 * 4 checkboxes for 4 face accessories,
 * setSelected selected or deselected state  --- change the imageIcon to match the checkbox
 * description.
 * oo very important: addItemListener as the end, otherwise setSelcted will be treated as a state change
 * oo learn how to arrange the image file  names and StringBuffer or StringBuilder
 * to select the correct image file
 */

public class Face_Accessory_On_Off_CheckBox_Image_Search extends JPanel implements ItemListener {
    private StringBuffer imageFileString;
    private JLabel picture;

    private JCheckBox chinBox;
    private JCheckBox glassBox;
    private JCheckBox hairBox;
    private JCheckBox teethBox;



    // constructor
    Face_Accessory_On_Off_CheckBox_Image_Search() {
        super(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20,10,20,10));
        // 4 checkbox buttons on one panel which is laid on the left of the parent panel
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1));
        chinBox = new JCheckBox("Chin");
        chinBox.setMnemonic(KeyEvent.VK_C);
        chinBox.setSelected(true);
        chinBox.addItemListener(this);
        glassBox = new JCheckBox("Glasses");
        glassBox.setMnemonic(KeyEvent.VK_G);
//        glassBox.addItemListener(this);
        glassBox.setSelected(true);
        hairBox = new JCheckBox("Hair");
        hairBox.setMnemonic(KeyEvent.VK_S);
//        hairBox.addItemListener(this);
        hairBox.setSelected(true);
        teethBox = new JCheckBox("Teeth");
        teethBox.setMnemonic(KeyEvent.VK_T);
        //teethBox.addItemListener(this);
        teethBox.setSelected(true);
        // add to the buttonPanel
        buttonPanel.add(chinBox);
        buttonPanel.add(glassBox);
        buttonPanel.add(hairBox);
        buttonPanel.add(teethBox);
        // add this buttonPanel to the main panel
        add(buttonPanel, BorderLayout.LINE_START);

        // oooo add itemlisteners only after setSelected has been defined, otherwise, listener can hear its state as a change
        chinBox.addItemListener(this);
        glassBox.addItemListener(this);
        hairBox.addItemListener(this);
        teethBox.addItemListener(this);



        // instantiate imageFileString
        imageFileString = new StringBuffer("cght");

        // the right part of the main panel is a lable with image icon
        picture = new JLabel();
        // use italic font when setText()
        picture.setFont(picture.getFont().deriveFont(Font.ITALIC));
        // setIcon will be called whenever there is an itemStateChanged call
        getIconForPicture();


        add(picture,BorderLayout.CENTER);


    }

    private void getIconForPicture() {
        // creat icon from image file
        ImageIcon icon = createIconFromFile("images/geek/geek-" + imageFileString.toString() + ".gif");
        picture.setIcon(icon);
        if(icon == null) {
            picture.setText("Image Missing");
        }  else {
            picture.setText(imageFileString.toString());
        }
    }

    private ImageIcon createIconFromFile(String filePath) {
        URL imageURL = getClass().getResource(filePath);
        if (imageURL != null) {
            return new ImageIcon(imageURL);
        }  else{
            System.err.println("Couldn't find the file " + filePath);
            return null;
        }

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        // when one checkbox is clicked, its stateChanged to Selected or DeSelected
        // the icon file path need to be updated to math the selection

        // assume it is selected state
        // change imageFileString to match the change
        System.out.println("item clicked");
        ItemSelectable box = e.getItemSelectable();
        char changedChar = '-';
        int index = 0; // the index Of ChangedChar;
        if(box.equals(chinBox)){
            changedChar = 'c';
            index = 0;
        }else if (box.equals(glassBox)) {
            changedChar = 'g';
            index = 1;
        } else if (box.equals(hairBox)) {
            changedChar = 'h';
            index = 2;
        }  else if (box.equals(teethBox)) {
            changedChar = 't';
            index = 3;
        }
        System.out.println("Index " + index);

        // however, if the button selected is in DeSelected state
        if(e.getStateChange() == ItemEvent.DESELECTED) {
            // one changedChar has been defined by the previous code
            // this needs to change again to match the deselected state
            changedChar = '-';
            // the index has been preserved
        }
        System.out.println("charChanged " + changedChar );

        imageFileString.setCharAt(index, changedChar);
        // after the file string being updated, we need to update the icon again
        getIconForPicture();
    }

    private static void createAndShowGUI(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Face_Accessory_On_Off_CheckBox_Image_Search pane = new Face_Accessory_On_Off_CheckBox_Image_Search();
        frame.setContentPane(pane);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
