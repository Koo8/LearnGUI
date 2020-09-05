import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JList_FireHirePeople extends JPanel implements ListSelectionListener {
    private JList<java.io.Serializable> list;
  //  private DefaultListModel<java.io.Serializable> model;
    private SortedListModel sortedListModel;
    private JScrollPane scrollPane;

    private JPanel bottomPane;
    private static JButton hireButton, fireButton;
    private JTextField textField;

    private HireListener hireListener;



    //constructor
    JList_FireHirePeople() {
         super(new BorderLayout());

         // create top scrollpane
        // create model - list
//        model = new DefaultListModel<>();
//        model.addElement("John Smith");
//        model.addElement("Amy Cooper");
//        model.addElement("Lee Black");

        sortedListModel = new SortedListModel();
        sortedListModel.add("John Smith");
        sortedListModel.add("Amy Cooper");
        sortedListModel.add("Gorge Brown");

        list = new JList<java.io.Serializable>(sortedListModel);
       // list.setSelectedIndex(0);// removed, so that fireButton can be set to enabled when an item is selected
        list.setVisibleRowCount(5);
       // list.setSelectionModel();   // remember, not Model, but Mode,
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Model is for class, Mode is for selection int
        list.addListSelectionListener(this);
        // add list to a scrollpane
        scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(200, 100));

        //creat a bottom pane with button, textField and button layout
        bottomPane = new JPanel();
        bottomPane.setLayout(new BoxLayout(bottomPane,BoxLayout.LINE_AXIS));
        fireButton = new JButton("Fire");
        hireButton = new JButton("Hire");

        hireListener = new HireListener(hireButton);
        textField = new JTextField(10);
        textField.setActionCommand("TEXT");
        textField.addActionListener(hireListener);
        hireButton.setEnabled(false);  // set initial false,
        fireButton.setEnabled(false);
        fireButton.addActionListener(new FireListener());
        hireButton.setActionCommand("BUTTON");
        hireButton.addActionListener(hireListener);
        // add to box
        bottomPane.add(fireButton);
        bottomPane.add(Box.createHorizontalStrut(5));
        bottomPane.add(new JSeparator(SwingConstants.VERTICAL));
        bottomPane.add(Box.createHorizontalStrut(5));
        bottomPane.add(textField);
        bottomPane.add(Box.createHorizontalStrut(3));
        bottomPane.add(hireButton);
        bottomPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        // add to main panel
        add(scrollPane,BorderLayout.CENTER);
        add(bottomPane,BorderLayout.PAGE_END);
        setOpaque(true);

    }


    @Override  // when list valuechanged, set FireButton enabled
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {
           // System.out.println("index is " + list.getSelectedIndex());
            // check if list has any selection
            if ((list.getSelectedIndex() == -1)) {
                fireButton.setEnabled(false);
            } else {
                fireButton.setEnabled(true);
            }
        }
    }

    private static void GUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setContentPane(new JList_FireHirePeople());
        frame.getRootPane().setDefaultButton(hireButton);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Utility.turnOffTheBoldFont();
        SwingUtilities.invokeLater(JList_FireHirePeople::GUI);
    }
    // inner class - so that to access the variables and implements the listener
    class FireListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // remove the item from list - this method only be called when there is a valid selection
            int index = list.getSelectedIndex();
            // todo: need to improve the sortedlistmodel.java for a right function.
            sortedListModel.removeElement(index);   // highlight: not list.remove(index), this remove is from container superclass, it remove component, the list only have one component;
            // in order to remove more item, check size == 0?
            int size = sortedListModel.getSize(); // list size
            if(size == 0) {
                fireButton.setEnabled(false);
            } else {
                // if index is the last item, setSelectedIndex(index) won't work, because that index is
                // not existed anymore, so we need to more one index up - (index-1)
                // otherwise index can stay the same
                if (index == size) {
                    index--;
                }
                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index); // in case a lot of items, window focus to this item
            }

        }
    }
    class HireListener implements ActionListener {
        private JButton button;

        public HireListener(JButton button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("in hireListener");
            String com = e.getActionCommand();
            System.out.println("actionCommand in Hirelistener is " + com);
            if(com.equals("TEXT")) {
                String text = textField.getText();
//                System.out.println("Text is " + text);
//                String removeALpha = text.replace("[A-Za-z]", "");
                if (text.equals("") || sortedListModel.contains(text) /*|| removeALpha.length()>0*/) {
                    //System.out.println("DisableFireButton inside HireActionListener");
                   Toolkit.getDefaultToolkit().beep();
                    hireButton.setEnabled(false);
                    textField.requestFocusInWindow();
                    textField.selectAll();
                } else {
                   // System.out.println("EnableFireButton inside hireactionlistener");
                    hireButton.setEnabled(true);
                }
            } else if (com.equals("BUTTON")) {
                int index = list.getSelectedIndex();
                if(index <0 ) {
                    index = 0;
                }  else {
                    index++;
                }
                sortedListModel.add( textField.getText());
                textField.requestFocusInWindow();
                textField.setText("");
                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }
}




