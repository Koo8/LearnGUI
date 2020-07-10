import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.security.Key;

/**
 * 4 different ways to contruct keystroke.getkeystroke
 *
 */
public class InputMap_ActionMap_KeyBinding extends JFrame {
    private static final String KeyForAction = "action key";
    // constructor
    public InputMap_ActionMap_KeyBinding() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(400,200));
        setLayout(new GridLayout(2,2));

        createJButtons(this);

        pack();
        setVisible(true);
    }

    private void createJButtons(JFrame frame) {
        JButton b1 = new JButton("controlAlt7");
        JButton b2 = new JButton("space key");
        JButton b3 = new JButton("focus/release enter");
        JButton b4 = new JButton("ancestor F4/shiftMask");

        // all buttons have the same action
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton source = (JButton) e.getSource();
                System.out.println("Activated " + source.getText());
            }
        };

        InputMap myInputMap = b1.getInputMap();     // each button has its own inputMap, but can have the same actionMap
        ActionMap myActionMap = b1.getActionMap();  // actionMap is instantiated, and can be reused again and again
        KeyStroke mykeyStroke = KeyStroke.getKeyStroke("control alt 7");
        myInputMap.put(mykeyStroke,KeyForAction);
        myActionMap.put(KeyForAction, action);  // this binding is valid as far as the parameters are the same

        myInputMap = b2.getInputMap();
        mykeyStroke = KeyStroke.getKeyStroke(' ');  // char as parameter
        myInputMap.put(mykeyStroke,KeyForAction);
        b2.setActionMap(myActionMap);

        myInputMap = b3.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        mykeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0, true);
        myInputMap.put(mykeyStroke,KeyForAction);
        b3.setActionMap(myActionMap);

        myInputMap = b4.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        mykeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.SHIFT_DOWN_MASK);
        myInputMap.put(mykeyStroke, KeyForAction);
        b4.setActionMap(myActionMap);

        frame.add(b1);
        frame.add(b2);
        frame.add(b3);
        frame.add(b4);

    }

    public static void main(String[] args) {
         SwingUtilities.invokeLater(new Runnable() {
             @Override
             public void run() {
                 new InputMap_ActionMap_KeyBinding();
             }
         });
    }
}
