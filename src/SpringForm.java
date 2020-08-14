/*
 * A 1.4 application that uses SpringLayout to create a forms-type layout.
 * Other files required: SpringUtilities.java.
 */

import javax.swing.*;
import java.awt.*;

public class SpringForm extends JPanel{

    public SpringForm() {
        // The BoxLayout manager generally uses a component's preferred size,
        // and is one of the few layout managers that respects the component's oomaximumoo size.
        // if change layout to other layout manager, the following getMaximumSize code won't have
        // any effects.
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        System.out.println("layout is " + getLayout());
        // highlight: for resizing a component, read
        // https://docs.oracle.com/javase/tutorial/uiswing/layout/problems.html
        // todo: oooo I still don't know if use setMaximumSize combined with revalidate and repaint, how to make the image show.
       JPanel p = new JPanel()
       {
           @Override
           public Dimension getMaximumSize() {
               Dimension pref = getMinimumSize();
//               System.out.println("I am in getMaximumSize");
//               System.out.println("pref is "  + pref );
               return new Dimension(Integer.MAX_VALUE,pref.height );
           }
       };
       // set SpringLayout with its components
       p.setLayout(new SpringLayout());
        String[] labels = {"Name: ", "Fax: ", "Email: ", "Address: "};
        int formLength = labels.length;

        for (int i = 0; i < formLength; i++) {
            // create corresponding JLabel and JTextFeild and pair them, then add both to the pane
           JLabel l = new JLabel(labels[i], JLabel.TRAILING);
           p.add(l);
           JTextField textField = new JTextField();
           if(i== 2) {
               textField.setText("I am the standard for the width of the column");
           }
           l.setLabelFor(textField);
           p.add(textField);
       }

       //oooooo Lay out the panel. - this is the most important part of this program.
       SpringUtilities.makeCompactGrid(p,
               formLength, 2, //rows, cols
               6, 6,        //initX, initY
               6, 6);       //xPad, yPad
       // SpringUtilities.makeGridAllEqual(p,formLength,2,10,10,10,10);

        p.setOpaque(true);  //content panes must be opaque

        add(p);
        JFrame frame = new JFrame("SpringForm");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        frame.setContentPane(this);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
   }


    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(SpringForm::new);
    }
}
