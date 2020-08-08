import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputVerifier_AcceptNumbers extends JPanel {

    JTextField name;
    JTextField age;
    JLabel result;

    // constructor
    InputVerifier_AcceptNumbers() {
        super(new GridLayout(0,1));
        name = new JTextField();
        age = new JTextField();
        result = new JLabel("show result");
        result.setForeground(Color.RED);

        add(name);
        add(age);
        add(result);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        age.setInputVerifier(new NumberVerifier());


    }

    private static void createAndDrawGUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        InputVerifier_AcceptNumbers panel = new InputVerifier_AcceptNumbers();
        frame.setContentPane(panel);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InputVerifier_AcceptNumbers::createAndDrawGUI);
    }

    private class NumberVerifier extends InputVerifier implements ActionListener {

        @Override
        public boolean shouldYieldFocus(JComponent source, JComponent target) {
            return verify(source) && verifyTarget(target);
        }

        @Override
        public boolean verify(JComponent input) {
            System.out.println("inside verify");
            JTextField ageField = (JTextField) input;
            try{
                System.out.println("inside try");
                int num = Integer.parseInt(ageField.getText());
                result.setText(num+ "");

            }catch(NumberFormatException e) {
                System.out.println("inside catch");
                result.setText("Invalid input");
                getToolkit().beep();
                System.out.println(e.toString());
                return false;
            }
            return true;

        }

        @Override
        public boolean verifyTarget(JComponent target) {
            return true;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField field = (JTextField) e.getSource();
            shouldYieldFocus(field,null);
            field.selectAll();
        }
    }
}
