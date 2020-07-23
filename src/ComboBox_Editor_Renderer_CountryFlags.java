import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComboBox_Editor_Renderer_CountryFlags extends JFrame implements ActionListener {

    // Fields
    private JComboBox comboBox;
    private DefaultComboBoxModel model;
    private String[][] countryList = {{"USA", "cat.gif"},
            {"India", "dog.gif"},
            {"Vietnam", "bird.gif"},
            {"Germany", "cat.gif"},
            {"Canada", "Pig.gif"},
            {"Japan", "rabbit.gif"}};

    // Constructor
    ComboBox_Editor_Renderer_CountryFlags() {
       super("ComboBox with Renderer, Editor and Custom ComboBoxModel");
       setLayout(new FlowLayout());

       model = new DefaultComboBoxModel(countryList);
       comboBox = new JComboBox(model);
       // The renderer is used if the JComboBox is not editable.
        // If it is editable, the editor is used to render and
        // edit the selected item.

        // highlight: comment off/on the setEditable() to see which (editor/renderer) style is used.
        // when it is editable, you need an editor to show the style for selected Item.
       //comboBox.setEditable(true);
       comboBox.setMaximumRowCount(2);
        // render paint the dropdown comboBox JPanel
       comboBox.setRenderer(new MyCellRenderer());
       // editor edit the selected area appearance
       comboBox.setEditor(new MyEditor());
       comboBox.setPreferredSize(new Dimension(280, 130));

       add(comboBox);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setSize(400, 300);
       setLocationRelativeTo(null);


    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
    private class MyCellRenderer extends JPanel implements ListCellRenderer {
        // the parameter "value" is each listItem
        private JLabel labelItem = new JLabel();
        
        MyCellRenderer() {
            // define the JPanel
            super(new GridBagLayout());
            setBackground(Color.WHITE);

            // define JLabel with constraints

            GridBagConstraints constraints = new GridBagConstraints();
           constraints.fill = GridBagConstraints.HORIZONTAL; // fill the whole row
            constraints.weightx = 1.0;
            constraints.insets = new Insets(5, 5, 2, 2);  //margins
            labelItem.setOpaque(true);
            // these following two lines won't do any difference, the layout is already left-aligned todo?
            //labelItem.setHorizontalAlignment(SwingConstants.LEFT);
           // labelItem.setAlignmentX(LEFT_ALIGNMENT);

            // add to JPanel
            add(labelItem,constraints);
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            String[] eachRow = (String[]) value;
            String country =  eachRow[0];
            String iconString = eachRow[1];
            ImageIcon icon = Utility.createImageIcon(this,"images/"+iconString);
            labelItem.setText(country);
            labelItem.setIcon(icon);

            // change color when selected / deSelected
            if(isSelected) {
                labelItem.setBackground(Color.BLUE);
                labelItem.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.RED));
                labelItem.setForeground(Color.YELLOW);
            }  else {
                labelItem.setBackground(Color.WHITE);
                labelItem.setBorder(null);
                labelItem.setForeground(Color.BLACK);

            }

            return this;
        }
    }


    private class MyEditor extends BasicComboBoxEditor {
        // use textField as BasicComboBoxEditor did or use JLabel as the canvas to draw

        private JLabel label;  // use JLabel because it has setIcon() method.
        private String countryString;

        private MyEditor() {
            label = new JLabel();
        }

        @Override
        public Component getEditorComponent() {
            return label;
        }
        // get the string[] of country and icon
        @Override
        public Object getItem() {
            return super.getItem();
        }
        // set the country string and icon 
        @Override
        public void setItem(Object anObject) {
            String[] rowItem = (String[]) anObject;
            countryString = rowItem[0];
            label.setText(countryString);
            label.setIcon(Utility.createImageIcon(this,"images/" + rowItem[1]));
            label.setBorder(BorderFactory.createDashedBorder(Color.red, 10, 13));
        }
    }

    // Main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ComboBox_Editor_Renderer_CountryFlags().setVisible(true);
            }
        });
    }


}
