import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.nio.ByteOrder;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

public class InputMap_Put_Method {

    public static void main(String args[]) {
        String ACTION_KEY = "The Action";

        JFrame frame = new JFrame("KeyStroke Sample");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton buttonA = new JButton("FOCUSED (control alt 7)");
        JButton buttonB = new JButton("FOCUS/RELEASE (VK_ENTER)");
        JButton buttonC = new JButton("ANCESTOR  (VK_F4+SHIFT_MASK)");
        JButton buttonD = new JButton("WINDOW (' ')");

        Action actionListener = new AbstractAction() {
            public void actionPerformed(ActionEvent actionEvent) {
                JButton source = (JButton) actionEvent.getSource();
                System.out.println("Activated: " + source.getText());
            }
        };

        KeyStroke controlAlt7 = KeyStroke.getKeyStroke("control alt 7");  // parameter is a string in a strict format
        InputMap inputMap = buttonA.getInputMap();
        inputMap.put(controlAlt7, ACTION_KEY);
        ActionMap actionMap = buttonA.getActionMap();    // the actionMap is reused again and again
        actionMap.put(ACTION_KEY, actionListener);      // the binding is used again and again

        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true);
        inputMap = buttonB.getInputMap();
        inputMap.put(enter, ACTION_KEY);
        buttonB.setActionMap(actionMap);

        KeyStroke shiftF4 = KeyStroke.getKeyStroke(KeyEvent.VK_F4,
                InputEvent.SHIFT_DOWN_MASK);
        inputMap = buttonC
                .getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(shiftF4, ACTION_KEY);
        buttonC.setActionMap(actionMap);

        KeyStroke space = KeyStroke.getKeyStroke(' ');
        inputMap = buttonD.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(space, ACTION_KEY);
        buttonD.setActionMap(actionMap);

        frame.setLayout(new BorderLayout(2, 2));
        frame.add(buttonA, BorderLayout.PAGE_START);
        frame.add(buttonB, BorderLayout.LINE_START);
        frame.add(buttonC, BorderLayout.LINE_END);
        frame.add(buttonD, BorderLayout.PAGE_END);

        frame.setSize(400, 200);
        frame.setVisible(true);
    }
}
