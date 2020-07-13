import javax.swing.*;

/**
 * This program show a special font name with a button at bottom, which
 * click to open a JDialog that shows a name JList, choose a name will close
 * this JDialog and change the showed name to the selected name
 */

public class ChooseNameFromJListFromJDialog {
    // the first window is a frame, the second pop up window is a JDialog
    private static JFrame frame;
    // the JFrame has 3 components -- JLabel for title, JLable for name, and JButton to lead to the JDialog
    private JLabel theChoosenNameIs;
    private JLabel theSelectedName;
    private JButton button;
    private static JPanel panel;
    private static String[] names = {"Arlo", "Cosmo", "Elmo", "Hugo",
            "Jethro", "Laszlo", "Milo", "Nemo",
            "Otto", "Ringo", "Rocco", "Rollo"};

    // constructor
    public ChooseNameFromJListFromJDialog() {
        // instantiate all fields , for frame, instantiate it inside the createAndShowGUI
        theChoosenNameIs = new JLabel("You choose the name: ");
        theSelectedName = new JLabel(names[1]);
        button = new JButton("Pick a Name");
        panel = new JPanel();
        System.out.println(panel.toString());
        panel.add(theChoosenNameIs);
        panel.add(theSelectedName);
        panel.add(button);

        //  todo: learn boxLayout and adjust the panel components layout
    }

    public static void createAndShowGUI() {
        frame = new JFrame("Name Chooser");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ChooseNameFromJListFromJDialog c = new ChooseNameFromJListFromJDialog();
        frame.setContentPane(ChooseNameFromJListFromJDialog.panel);

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
