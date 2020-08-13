import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Replace the "he" in the text with "she"
  *
 * String regex = "\\b\\d+\\b";   // find all numbers. \b \b is word boundry, + is for 1 or more occurance, d is for digit
 */

public class Regex_ReplaceHeWithShe extends JPanel implements KeyListener {
    JTextArea inputArea, outputText;
    // constructor
    Regex_ReplaceHeWithShe() {
        super(new GridLayout(0,1));
        // create GUI
        inputArea = new JTextArea("He said he can go to school. \r\nHe also said he can't do her job. Is he serious about her situation?");
        outputText = new JTextArea("show result");
        inputArea.addKeyListener(this);
        add(inputArea);
        add(outputText);
        setPreferredSize(new Dimension(600,200));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        changeText(inputArea,outputText);
    }

    private void changeText(JTextArea inputArea,  JTextArea outputText) {
        String text = inputArea.getText();
        // create Pattern and matcher
        String regex = "\\b(he)\\b";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(text);
        StringBuilder sb = new StringBuilder();
        String t= "";
        String replaceString = "";

        while(m.find()) {
            t = m.group();
            if(t.equals("He")){
                replaceString = "She";
            }
            if (t.equals("he")) {
                replaceString = "she";
            }
            m.appendReplacement(sb, replaceString);

        }
        m.appendTail(sb);
        outputText.setText(sb.toString());
    }

    private static void GUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Regex_ReplaceHeWithShe pane = new Regex_ReplaceHeWithShe();
        frame.setContentPane(pane);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Regex_ReplaceHeWithShe::GUI);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("keyTyped is listening...");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("KeyPressed is listening");
        changeText(inputArea,outputText);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("KeyReleased is listening");
    }
}
